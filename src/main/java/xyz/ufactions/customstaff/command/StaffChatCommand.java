package xyz.ufactions.customstaff.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ufactions.customstaff.CustomStaff;
import xyz.ufactions.customstaff.file.LanguageFile;
import xyz.ufactions.customstaff.libs.F;

import java.util.Collections;
import java.util.List;

public class StaffChatCommand extends CommandBase {

    public StaffChatCommand(CustomStaff plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!checkPermission(sender, "customstaff.command.sc")) return true;
        if (!isPlayer(sender)) return true;
        Player player = (Player) sender;

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("toggle")) {
                if (plugin.isInStaffChat(player.getUniqueId())) {
                    plugin.disableStaffChat(player.getUniqueId());
                    player.sendMessage(F.color(plugin.getLanguageFile().get(LanguageFile.LanguagePath.STAFFCHAT_DISABLED)));
                } else {
                    plugin.enableStaffChat(player.getUniqueId());
                    player.sendMessage(F.color(plugin.getLanguageFile().get(LanguageFile.LanguagePath.STAFFCHAT_ENABLED)));
                }
                return true;
            }
            plugin.sendToStaffChat(player, F.concatenate(" ", 0, args));
            return true;
        }
        player.sendMessage(F.format("Commands:"));
        player.sendMessage(F.help("/" + label + " toggle", "Toggle staff chat on/off"));
        player.sendMessage(F.help("/" + label + " <message>", "Send a message to staff chat"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (checkPermission(sender, "customstaff.command.sc", false)) {
            if (args.length == 1)
                return getMatches(args[0], Collections.singletonList("toggle"));
        }
        return null;
    }
}