package xyz.ufactions.customstaff.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ufactions.customstaff.CustomStaff;
import xyz.ufactions.customstaff.libs.F;

import java.util.ArrayList;
import java.util.List;

public class StaffCommand extends CommandBase {

    public StaffCommand(CustomStaff plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!checkPermission(sender, "customstaff.command.staff")) return true;

        if (args.length == 1) {
            if (isPlayer(sender, false)) {
                Player player = (Player) sender;
                if (args[0].equalsIgnoreCase("hide")) {
                    if (player.hasPermission("customstaff.command.staff.hide")) {
                        plugin.setHiddenStaff(player.getUniqueId(), true);
                        player.sendMessage(F.error("You have been hidden from the staff list."));
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("show")) {
                    if (player.hasPermission("customstaff.command.staff.show")) {
                        plugin.setHiddenStaff(player.getUniqueId(), false);
                        player.sendMessage(F.format("You are now visible on the staff list."));
                        return true;
                    }
                }
            }
        }

        List<Player> staff = plugin.getOnlineStaff(isPlayer(sender) ? (Player) sender : null);
        if (staff.isEmpty()) {
            sender.sendMessage(F.error("There are no staff online."));
        } else {
            sender.sendMessage(F.format("Online Staff:"));
            for (Player player : staff) {
                sender.sendMessage(F.list(player.getName()));
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (checkPermission(sender, "customstaff.command.staff", false)) {
            List<String> list = new ArrayList<>();
            if (checkPermission(sender, "customstaff.command.staff.show", false)) list.add("show");
            if (checkPermission(sender, "customstaff.command.staff.hide", false)) list.add("hide");
            return getMatches(args[0], list);
        }
        return null;
    }
}