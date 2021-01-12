package xyz.ufactions.customstaff.network;

import xyz.ufactions.customstaff.CustomStaff;

import java.util.UUID;

public abstract class PluginChannelListener {

    protected final CustomStaff plugin;

    public PluginChannelListener(CustomStaff plugin) {
        this.plugin = plugin;
    }

    public abstract void onDataReceived(PluginChannelData data);

    protected final void sendData(PluginChannelData data) {
        plugin.getPluginChannel().sendData(data);
    }

    protected final void sendData(PluginChannelData data, UUID address) {
        plugin.getPluginChannel().sendData(data, address);
    }
}