package xyz.ufactions.customstaff.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.ufactions.customstaff.CustomStaff;

import java.util.regex.Matcher;

public class PlayerListener implements Listener {

    private final CustomStaff plugin;

    public PlayerListener(CustomStaff plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (plugin.isInStaffChat(e.getPlayer().getUniqueId())) {
            e.getRecipients().clear();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("customstaff.sc.notify")) {
                    e.getRecipients().add(player);
                }
            }

            String format = plugin.getConfigurationFile().getString("staff-chat-format");
            format = ChatColor.translateAlternateColorCodes('&', format);
            format = format.replaceAll("%chat_format%", Matcher.quoteReplacement(e.getFormat()));
            e.setFormat(format);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        plugin.getHiddenStaff().remove(e.getPlayer().getUniqueId());
    }
}