package xyz.ufactions.customstaff;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ufactions.customstaff.command.CustomStaffCommand;
import xyz.ufactions.customstaff.command.StaffChatCommand;
import xyz.ufactions.customstaff.command.StaffCommand;
import xyz.ufactions.customstaff.file.ConfigurationFile;
import xyz.ufactions.customstaff.file.LanguageFile;
import xyz.ufactions.customstaff.listener.PlayerListener;
import xyz.ufactions.customstaff.network.PluginChannel;
import xyz.ufactions.customstaff.network.channels.PluginChannelType;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class CustomStaff extends JavaPlugin {

    // Data || TODO : Data Sorting? - Might get messy
    private final Set<UUID> staffchat = new HashSet<>();
    private final Set<UUID> hiddenstaff = new HashSet<>();

    private PluginChannel pluginChannel;
    private boolean debugging;

    // Configuration Files
    private ConfigurationFile configurationFile;
    private LanguageFile languageFile;

    @Override
    public void onEnable() {
        this.languageFile = new LanguageFile(this, LanguageFile.Language.ENGLISH);
        this.configurationFile = new ConfigurationFile(this);

        this.debugging = this.configurationFile.getBoolean("debugging");

        PluginChannelType pluginChannelType;
        try {
            pluginChannelType = PluginChannelType.valueOf(this.configurationFile.getString("messaging-channel"));
        } catch (EnumConstantNotPresentException e) {
            getLogger().warning("Failed to fetch messaging-channel provided in the config.");
            pluginChannelType = PluginChannelType.EMPTY;
        }

        try {
            this.pluginChannel = pluginChannelType.getPluginChannelClass()
                    .getConstructor(CustomStaff.class).newInstance(this);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            getLogger().warning("Failed to instantiate the plugin channel class.");
            e.printStackTrace();
        }

        if (this.pluginChannel != null) this.pluginChannel.register();

        new StaffChatCommand(this).register("staffchat");
        new StaffCommand(this).register("staff");
        new CustomStaffCommand(this).register("customstaff");

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        try {
            this.pluginChannel.unregister();
        } catch (Exception e) {
            getLogger().info("Failed to unregister plugin channel. This might cause duplicate data," +
                    " a restart is recommended if this is a reload.");
            e.printStackTrace();
        }
    }

    public void debug(String message) {
        if (debugging)
            getLogger().info(message);
    }

    public void sendToStaffChat(Player player, String message) {
        boolean added = staffchat.add(player.getUniqueId());
        player.chat(message);
        if (added) staffchat.remove(player.getUniqueId());
    }

    // Data Handling

    public boolean isInStaffChat(UUID uuid) {
        return staffchat.contains(uuid);
    }

    public void enableStaffChat(UUID uuid) {
        staffchat.add(uuid);
    }

    public void disableStaffChat(UUID uuid) {
        staffchat.remove(uuid);
    }

    public void setHiddenStaff(UUID uuid, boolean hidden) {
        if (Bukkit.getPlayer(uuid) == null) return;

        if (hidden)
            hiddenstaff.add(uuid);
        else
            hiddenstaff.remove(uuid);
    }

    // Fetch

    public boolean debugging() {
        return debugging;
    }

    public ConfigurationFile getConfigurationFile() {
        return configurationFile;
    }

    public LanguageFile getLanguageFile() {
        return languageFile;
    }

    public Set<UUID> getHiddenStaff() {
        return hiddenstaff;
    }

    public PluginChannel getPluginChannel() {
        return pluginChannel;
    }

    public List<Player> getOnlineStaff(Player player) {
        List<Player> list = new ArrayList<>();
        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("customstaff.visible.staff") && !hiddenstaff.contains(staff.getUniqueId())
                    && (player != null && player.canSee(staff))) {
                list.add(staff);
            }
        }
        return list;
    }
}