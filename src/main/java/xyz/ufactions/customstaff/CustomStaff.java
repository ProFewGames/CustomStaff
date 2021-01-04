package xyz.ufactions.customstaff;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ufactions.customstaff.command.StaffChatCommand;
import xyz.ufactions.customstaff.file.ConfigurationFile;
import xyz.ufactions.customstaff.listener.PlayerListener;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CustomStaff extends JavaPlugin {

    // Data
    private final Set<UUID> staffchat = new HashSet<>(); // TODO : Data Sorting? - Might get messy
    private String chatFormat;

    // Configuration Files
    private ConfigurationFile configurationFile;

    @Override
    public void onEnable() {
        this.configurationFile = new ConfigurationFile(this);

        StaffChatCommand command = new StaffChatCommand(this);
        command.register("staffchat");

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

    public void setChatFormat(String chatFormat) {
        this.chatFormat = chatFormat;
    }

    // Fetch

    public ConfigurationFile getConfigurationFile() {
        return configurationFile;
    }
}