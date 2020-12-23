package xyz.ufactions.customstaff;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.ufactions.customstaff.command.StaffChatCommand;
import xyz.ufactions.customstaff.listener.PlayerListener;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CustomStaff extends JavaPlugin {

    private final Set<UUID> staffchat = new HashSet<>(); // TODO : Data Sorting? - Might get messy

    @Override
    public void onEnable() {
        StaffChatCommand command = new StaffChatCommand(this);
        command.register("staffchat");

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    public boolean isInStaffChat(UUID uuid) {
        return staffchat.contains(uuid);
    }

    public void enableStaffChat(UUID uuid) {
        staffchat.add(uuid);
    }

    public void disableStaffChat(UUID uuid) {
        staffchat.remove(uuid);
    }
}