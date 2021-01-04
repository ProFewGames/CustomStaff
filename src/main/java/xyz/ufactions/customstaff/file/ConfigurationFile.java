package xyz.ufactions.customstaff.file;

import org.bukkit.plugin.java.JavaPlugin;

public class ConfigurationFile extends FileHandler {

    public ConfigurationFile(JavaPlugin plugin) {
        super(plugin, "config.yml");
    }

    public String getStaffChatFormat() {
        return getString("staff-chat-format");
    }
}