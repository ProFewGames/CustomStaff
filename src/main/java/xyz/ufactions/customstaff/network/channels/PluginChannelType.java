package xyz.ufactions.customstaff.network.channels;

import xyz.ufactions.customstaff.network.PluginChannel;

public enum PluginChannelType {
    REDIS(RedisPluginChannel.class),
    MYSQL(MySQLPluginChannel.class),
    BUNGEE(BungeePluginChannel.class),
    EMPTY(EmptyPluginChannel.class);

    private final Class<? extends PluginChannel> pluginChannelClass;

    PluginChannelType(Class<? extends PluginChannel> pluginChannelClass) {
        this.pluginChannelClass = pluginChannelClass;
    }

    public Class<? extends PluginChannel> getPluginChannelClass() {
        return pluginChannelClass;
    }
}