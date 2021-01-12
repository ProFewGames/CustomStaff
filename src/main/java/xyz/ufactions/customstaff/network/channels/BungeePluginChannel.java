package xyz.ufactions.customstaff.network.channels;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import xyz.ufactions.customstaff.CustomStaff;
import xyz.ufactions.customstaff.libs.UtilMath;
import xyz.ufactions.customstaff.network.PluginChannel;
import xyz.ufactions.customstaff.network.PluginChannelData;
import xyz.ufactions.customstaff.network.handlers.OnlineStaffHandler;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class BungeePluginChannel extends PluginChannel implements PluginMessageListener {

    public BungeePluginChannel(CustomStaff plugin) {
        super(plugin);
    }

    @Override
    public void register() {
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);

        registerData(OnlineStaffHandler.OnlineStaffData.class);
    }

    @Override
    public void sendData(PluginChannelData data, UUID address) {
        plugin.debug("Sending to : " + (address != null ? address.toString() : "n/a") + " | data : " + data);
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward");
        out.writeUTF("ALL");
        out.writeUTF("CustomStaff");

        data.from = ServerUUID;
        data.address = address;

        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);
        try {
            String content = cookData(data);
            msgout.writeUTF(content);
            plugin.getLogger().info("Written Contents = \"" + content + "\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.writeShort(msgbytes.toByteArray().length);
        out.write(msgbytes.toByteArray());

        Player player = new ArrayList<Player>(Bukkit.getOnlinePlayers()).get(UtilMath.r(Bukkit.getOnlinePlayers().size()));
        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());

        plugin.debug("Data sent.");
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equalsIgnoreCase("BungeeCord")) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (!subchannel.equalsIgnoreCase("CustomStaff")) return;
        plugin.debug("Incoming Data");

        String data = in.readUTF();
        receivedData(data.substring(2)); // Substring 2? It adds characters when receiving messages idk why
    }
}