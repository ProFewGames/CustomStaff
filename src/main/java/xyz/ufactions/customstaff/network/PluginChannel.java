package xyz.ufactions.customstaff.network;

import xyz.ufactions.customstaff.CustomStaff;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class PluginChannel {

    private final List<PluginChannelListener> listeners = new ArrayList<>();
    public final UUID ServerUUID = UUID.randomUUID();

    protected final CustomStaff plugin;

    public PluginChannel(CustomStaff plugin) {
        this.plugin = plugin;
    }

    public abstract void register();

    public final void sendData(PluginChannelData data) {
        sendData(data, null);
    }

    public abstract void sendData(PluginChannelData data, UUID address);

    protected final void receivedData(String data) {
        plugin.debug("Decoding data : " + data);
        PluginChannelData pluginChannelData = Utility.deserialize(data, PluginChannelData.class);
        if (pluginChannelData.address != null) {
            if (pluginChannelData.address != ServerUUID) {
                plugin.debug("Data was not meant for this address.");
                return;
            }
        }

        plugin.debug("Calling data : " + pluginChannelData);
        for (PluginChannelListener listener : listeners) {
            listener.onDataReceived(pluginChannelData);
        }
        plugin.debug("Data Called.");
    }

    public final void registerListener(PluginChannelListener listener) {
        plugin.debug("Registering listener : " + listener);
        listeners.add(listener);
    }
}