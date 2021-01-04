package xyz.ufactions.customstaff.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.ufactions.customstaff.CustomStaff;

import java.util.List;

public class StaffCommand extends CommandBase {

    public StaffCommand(CustomStaff plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}