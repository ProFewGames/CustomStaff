package xyz.ufactions.customstaff.network;

import xyz.ufactions.customstaff.CustomStaff;

import java.util.UUID;

public abstract class PluginChannelData {

    protected final CustomStaff plugin;
    protected final PluginChannel pluginChannel;

    public UUID from;
    public UUID address;

    public PluginChannelData(CustomStaff plugin) {
        this.plugin = plugin;
        this.pluginChannel = plugin.getPluginChannel();
    }

    protected final void sendData(PluginChannelData data) {
        plugin.getPluginChannel().sendData(data);
    }
}