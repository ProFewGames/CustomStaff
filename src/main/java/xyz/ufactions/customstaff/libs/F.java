package xyz.ufactions.customstaff.libs;

import org.bukkit.ChatColor;

import java.util.List;

public class F {

    public static String format(String text) {
        return color("&8[&4&lCustomStaff&8]&r " + text);
    }

    public static String element(String text) {
        return color("&c" + text + "&7");
    }

    public static String help(String help, String desc) {
        return color("&c" + help + " &7" + desc);
    }

    public static String error(String text) {
        return format("&c" + text);
    }

    public static String list(String string) {
        return color(" &c➥ &7" + string);
    }

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }


    public static String oo(boolean var) {
        if (var)
            return color("&aon&7");
        return color("&coff&7");
    }

    public static String concatenate(String splitter, List<String> list) {
        StringBuilder builder = new StringBuilder();

        for (String string : list) {
            if (builder.length() == 0) {
                builder.append(string);
            } else {
                builder.append(splitter).append(string);
            }
        }
        return builder.toString();
    }

    public static String concatenate(String splitter, int index, String[] args) {
        StringBuilder builder = new StringBuilder();

        for (int i = index; i < args.length; i++) {
            if (builder.length() == 0)
                builder.append(args[i]);
            else
                builder.append(splitter).append(args[i]);
        }

        return builder.toString();
    }

    public static String capitalizeFirstLetter(String string) {
        if (string.contains(" ")) {
            StringBuilder toReturn = new StringBuilder();
            String[] array = string.split(" ");

            for (String s : array) {
                toReturn.append(capitalizeFirstLetter(s));
            }
            return toReturn.toString();
        }
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}