package xyz.ufactions.customstaff.network.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.ufactions.customstaff.CustomStaff;
import xyz.ufactions.customstaff.network.PluginChannelData;
import xyz.ufactions.customstaff.network.PluginChannelListener;

import java.util.HashSet;
import java.util.Set;

public class OnlineStaffHandler extends PluginChannelListener {

    public static class OnlineStaffData extends PluginChannelData {
        public final Set<String> staff = new HashSet<>();
    }

    public OnlineStaffHandler(CustomStaff plugin) {
        super(plugin);

        plugin.getPluginChannel().registerListener(this);
    }

    @Override
    public void onDataReceived(PluginChannelData data) {
        if (!(data instanceof OnlineStaffData)) return;

        OnlineStaffData osd = (OnlineStaffData) data;
        if (data.address != null) {
            System.out.println("Fetched : " + osd.staff);
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.isOp()) {
                osd.staff.add(player.getName());
            }
        }
        sendData(osd, osd.from);
    }
}