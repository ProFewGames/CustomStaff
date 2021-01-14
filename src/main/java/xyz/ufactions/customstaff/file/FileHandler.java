package xyz.ufactions.customstaff.file;

import org.apache.commons.lang.Validate;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class FileHandler extends YamlConfiguration {

    public FileHandler(JavaPlugin plugin, String name) {
        this(plugin, null, name);
    }

    public FileHandler(JavaPlugin plugin, String resourcePath, String name) {
        Validate.notNull(name, "File name null");
        Validate.notEmpty(name, "File name empty");
        if (plugin.getDataFolder().mkdirs()) plugin.getLogger().info("Plugins directory created.");

        File file = new File(plugin.getDataFolder(), name);
        boolean created = false;
        if (!file.exists()) {
            if ((resourcePath != null && plugin.getResource(resourcePath) != null) || plugin.getResource(name) != null) {
                plugin.getLogger().info("Attempting to create '" + name + "' from resources.");
                plugin.saveResource(resourcePath != null ? resourcePath : name, false);
            } else {
                try {
                    if (file.createNewFile()) plugin.getLogger().info("File '" + name + "' created.");
                } catch (IOException e) {
                    plugin.getLogger().severe("Failed to create file '" + name + "'");
                    e.printStackTrace();
                }
            }
            created = true;
        }

        try {
            this.load(file);
        } catch (FileNotFoundException e) {
            plugin.getLogger().severe("Failed to locate file @ '" + plugin.getDataFolder().toString() + "/" + name + "'");
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            plugin.getLogger().severe("Failed to load configuration file @ '" + plugin.getDataFolder().toString() + "/" + name + "'");
            e.printStackTrace();
        } catch (IOException e) {
            plugin.getLogger().severe("IOException @ '" + plugin.getDataFolder().toString() + "/" + name + "'");
            e.printStackTrace();
        }
        if (created) onCreate();
    }

    /**
     * Called when the file is created.
     */
    protected void onCreate() {
    }
}