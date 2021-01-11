package xyz.ufactions.customstaff.network.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.ufactions.customstaff.CustomStaff;
import xyz.ufactions.customstaff.network.PluginChannelData;
import xyz.ufactions.customstaff.network.PluginChannelListener;

import java.util.ArrayList;
import java.util.List;

public class OnlineStaffData extends PluginChannelData implements PluginChannelListener {

    public final List<Player> staff = new ArrayList<>();

    public OnlineStaffData(CustomStaff plugin) {
        super(plugin);

        plugin.getPluginChannel().registerListener(this);
    }

    @Override
    public void onDataReceived(PluginChannelData data) {
        if (!(data instanceof OnlineStaffData)) return;

        OnlineStaffData onlineStaffData = (OnlineStaffData) data;
        onlineStaffData.staff.addAll(Bukkit.getOnlinePlayers());
        plugin.debug("Finished counting staff. Returning information to called server.");
        plugin.getPluginChannel().sendData(onlineStaffData, onlineStaffData.from);
    }
}