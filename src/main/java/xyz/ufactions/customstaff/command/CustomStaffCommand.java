package xyz.ufactions.customstaff.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ufactions.customstaff.CustomStaff;
import xyz.ufactions.customstaff.libs.TitleAPI;

import java.util.List;

public class CustomStaffCommand extends CommandBase {

    public CustomStaffCommand(CustomStaff plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        TitleAPI.sendTitle("Hello", "Test", 5, 2, 5, Bukkit.getOnlinePlayers().toArray(new Player[0]));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return null;
    }
}