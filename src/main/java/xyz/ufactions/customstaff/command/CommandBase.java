package xyz.ufactions.customstaff.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import xyz.ufactions.customstaff.CustomStaff;
import xyz.ufactions.customstaff.libs.F;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandBase implements CommandExecutor, TabCompleter {

    protected final CustomStaff plugin;

    public CommandBase(CustomStaff plugin) {
        this.plugin = plugin;
    }

    protected final List<String> getMatches(String start, List<String> possibleMatches) {
        if (start.isEmpty()) return possibleMatches;

        List<String> matches = new ArrayList<>();
        for (String possibleMatch : possibleMatches) {
            if (possibleMatch.toLowerCase().startsWith(start.toLowerCase())) {
                matches.add(possibleMatch);
            }
        }
        return matches;
    }

    protected final boolean checkPermission(CommandSender sender, String permission) {
        return checkPermission(sender, permission, true);
    }

    protected final boolean checkPermission(CommandSender sender, String permission, boolean notify) {
        if (!sender.hasPermission(permission)) {
            if (notify) {
                sender.sendMessage(F.error("No Permission"));
                return false;
            }
        }
        return true;
    }

    protected final boolean isPlayer(CommandSender sender) {
        return isPlayer(sender, true);
    }

    protected final boolean isPlayer(CommandSender sender, boolean notify) {
        if (!(sender instanceof Player)) {
            if (notify)
                sender.sendMessage(F.error("No Player"));
            return false;
        }
        return true;
    }

    public final void register(String name) {
        plugin.getCommand(name).setExecutor(this);
        plugin.getCommand(name).setTabCompleter(this);
    }
}