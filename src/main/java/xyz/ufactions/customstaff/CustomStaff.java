package xyz.ufactions.customstaff;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ufactions.customstaff.command.StaffChatCommand;
import xyz.ufactions.customstaff.command.StaffCommand;
import xyz.ufactions.customstaff.file.ConfigurationFile;
import xyz.ufactions.customstaff.listener.PlayerListener;

import java.util.*;

public class CustomStaff extends JavaPlugin {

    // Data || TODO : Data Sorting? - Might get messy
    private final Set<UUID> staffchat = new HashSet<>();
    private final Set<UUID> hiddenstaff = new HashSet<>();

    // Configuration Files
    private ConfigurationFile configurationFile;

    @Override
    public void onEnable() {
        this.configurationFile = new ConfigurationFile(this);

        new StaffChatCommand(this).register("staffchat");
        new StaffCommand(this).register("staff");

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    public void reload() {
        this.configurationFile.reload();
    }

    public void sendToStaffChat(Player player, String message) {
        boolean added = staffchat.add(player.getUniqueId());
        player.chat(message);
        if (added) staffchat.remove(player.getUniqueId());
    }

    // Data Handling

    public boolean isInStaffChat(UUID uuid) {
        return staffchat.contains(uuid);
    }

    public void enableStaffChat(UUID uuid) {
        staffchat.add(uuid);
    }

    public void disableStaffChat(UUID uuid) {
        staffchat.remove(uuid);
    }

    public void setHiddenStaff(UUID uuid, boolean hidden) {
        if (Bukkit.getPlayer(uuid) == null) return;

        if (hidden)
            hiddenstaff.add(uuid);
        else
            hiddenstaff.remove(uuid);
    }

    // Fetch

    public ConfigurationFile getConfigurationFile() {
        return configurationFile;
    }

    public Set<UUID> getHiddenStaff() {
        return hiddenstaff;
    }

    public List<Player> getOnlineStaff(Player player) {
        List<Player> list = new ArrayList<>();
        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("customstaff.visible.staff") && !hiddenstaff.contains(staff.getUniqueId())
                    && (player != null && player.canSee(staff))) {
                list.add(staff);
            }
        }
        return list;
    }
}