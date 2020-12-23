package xyz.ufactions.customstaff.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.ufactions.customstaff.CustomStaff;

import java.util.Collections;
import java.util.List;

public class StaffChatCommand extends ICommand {

    public StaffChatCommand(CustomStaff plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!checkPermission(sender, "customstaff.command.sc")) return true;
        if(!isPlayer(sender)) return true;
        // TODO
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            if (checkPermission(sender, "customstaff.command.sc", false)) {
                return getMatches(args[0], Collections.singletonList("toggle"));
            }
        }
        return null;
    }
}