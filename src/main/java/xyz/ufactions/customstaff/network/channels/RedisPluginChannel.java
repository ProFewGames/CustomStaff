package xyz.ufactions.customstaff.network.channels;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import xyz.ufactions.customstaff.CustomStaff;
import xyz.ufactions.customstaff.network.PluginChannel;
import xyz.ufactions.customstaff.network.PluginChannelData;

import java.util.UUID;

public class RedisPluginChannel extends PluginChannel {

    private final String CHANNEL = "CustomStaff";

    private JedisPool pool;
    private PubSub pubsub;

    public RedisPluginChannel(CustomStaff plugin) {
        super(plugin);
    }

    @Override
    public void register() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        String host = plugin.getConfigurationFile().getString("Redis.host");
        String password = plugin.getConfigurationFile().getString("Redis.password");
        int port = plugin.getConfigurationFile().getInt("Redis.port");
        this.pool = new JedisPool(jedisPoolConfig, host, port, 2000, password);
        plugin.debug("Jedis pool created.");

        Jedis jedis = this.pool.getResource(); // Note: Too many connections might be this | Not returning to pool.

        new Thread(() -> {
            try {
                this.plugin.debug("Subscribing via Jedis to channel \"" + CHANNEL + "\".");
                jedis.subscribe(pubsub = new PubSub(), CHANNEL);
                this.plugin.debug("Subscription via Jedis has ended.");
            } catch (Exception e) {
                this.plugin.debug("Subscription via Jedis failed.");
                e.printStackTrace();
            }
        }, "Jedis").start();
    }

    @Override
    public void unregister() {
        pubsub.unsubscribe();
    }

    @Override
    public void sendData(PluginChannelData data, UUID address) {
        Jedis jedis = this.pool.getResource();
        plugin.debug("Resource taken from pool.");

        String cookedData = cookData(data);
        plugin.debug("Transmitting cooked data. " + cookedData);
        try {
            jedis.publish(CHANNEL, cookedData);
        } catch (Exception e) {
            this.pool.returnBrokenResource(jedis);
            jedis = null;
            plugin.debug("Returned broken resource.");
        } finally {
            if (jedis != null) {
                this.pool.returnResource(jedis);
                plugin.debug("Returned resource to pool.");
            }
        }
    }

    private class PubSub extends JedisPubSub {

        @Override
        public void onMessage(String channel, String message) {
            plugin.debug("Message Received. Channel: " + channel + ". Msg: " + message);
        }
    }
}