package xyz.ufactions.customstaff.network.channels;

import xyz.ufactions.customstaff.CustomStaff;
import xyz.ufactions.customstaff.network.PluginChannel;
import xyz.ufactions.customstaff.network.PluginChannelData;

import java.util.UUID;

public class EmptyPluginChannel extends PluginChannel {

    public EmptyPluginChannel(CustomStaff plugin) {
        super(plugin);
    }

    @Override
    public void register() {
    }

    @Override
    public void sendData(PluginChannelData data, UUID address) {
    }
}