package xyz.ufactions.customstaff.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.ufactions.customstaff.CustomStaff;

public class PlayerListener implements Listener {

    private final CustomStaff plugin;

    public PlayerListener(CustomStaff plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        // TODO
    }
}