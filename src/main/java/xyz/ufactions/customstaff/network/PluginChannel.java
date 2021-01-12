package xyz.ufactions.customstaff.network;

import xyz.ufactions.customstaff.CustomStaff;
import xyz.ufactions.customstaff.libs.F;

import java.util.*;

public abstract class PluginChannel {

    private final Map<String, Class<? extends PluginChannelData>> dataMap = new HashMap<>();
    private final List<PluginChannelListener> listeners = new ArrayList<>();
    public final UUID ServerUUID = UUID.randomUUID();

    protected final CustomStaff plugin;

    public PluginChannel(CustomStaff plugin) {
        this.plugin = plugin;
    }

    public abstract void register();

    protected final void registerData(Class<? extends PluginChannelData> pluginChannelDataClazz) {
        dataMap.put(pluginChannelDataClazz.getSimpleName(), pluginChannelDataClazz);
    }

    public final void sendData(PluginChannelData data) {
        sendData(data, null);
    }

    public abstract void sendData(PluginChannelData data, UUID address);

    protected final String cookData(PluginChannelData data) { // Global Serialize
        return data.getClass().getSimpleName() + ":" + Utility.serialize(data);
    }

    protected final void receivedData(String data) {
        plugin.debug("Decoding data : \"" + data + "\"");
        // Uncook the data... name convention :) "uncook" - Start JynxDEV
        String[] array = data.split(":");
        Class<? extends PluginChannelData> clazz = dataMap.get(array[0]);
        PluginChannelData pluginChannelData = Utility.deserialize(F.concatenate(":", 1, array), clazz);
        // End
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