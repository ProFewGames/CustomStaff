package xyz.ufactions.customstaff.file;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.List;

public abstract class FileHandler {

    protected final JavaPlugin plugin;
    protected final String fileName;
    private final File directory;
    private final String resourcePath;
    private File file;
    private FileConfiguration config;

    public FileHandler(JavaPlugin plugin, String fileName) {
        this(plugin, plugin.getDataFolder(), fileName);
    }

    public FileHandler(JavaPlugin plugin, File outputDirectory, String fileName) {
        this(plugin, null, outputDirectory, fileName);
    }

    public FileHandler(JavaPlugin plugin, String resourcePath, File outputDirectory, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.directory = outputDirectory;
        this.resourcePath = resourcePath;
        this.reload();
    }

    public final void set(String path, Object value) {
        this.getConfig().set(path, value);
    }

    public boolean contains(String path) {
        return this.getConfig().contains(path);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return this.getConfig().getConfigurationSection(path);
    }

    public List<String> getStringList(String path) {
        return this.getConfig().getStringList(path);
    }

    public long getLong(String path) {
        return this.getConfig().getLong(path);
    }

    public List<?> getList(String path) {
        return this.getConfig().getList(path);
    }

    public boolean getBoolean(String path) {
        return this.getConfig().getBoolean(path);
    }

    public int getInt(String path) {
        return this.getConfig().getInt(path);
    }

    public double getDouble(String path) {
        return this.getConfig().getDouble(path);
    }

    public String getString(String path) {
        return this.getConfig().getString(path);
    }

    public long getLong(String path, long def) {
        return this.getConfig().getLong(path, def);
    }

    public List<?> getList(String path, List<?> def) {
        return this.getConfig().getList(path, def);
    }

    public boolean getBoolean(String path, boolean def) {
        return this.getConfig().getBoolean(path, def);
    }

    public int getInt(String path, int def) {
        return this.getConfig().getInt(path, def);
    }

    public double getDouble(String path, double def) {
        return this.getConfig().getDouble(path, def);
    }

    public String getString(String path, String def) {
        return this.getConfig().getString(path, def);
    }

    public Object get(String path, Object def) {
        return this.getConfig().get(path, def);
    }

    public final void reload() {
        if (this.directory.exists()) { // Directory exist?
            if (!this.directory.isDirectory()) { // File is directory?
                try { // Not directory may contain important info! Error :)
                    throw new NotDirectoryException("'" + this.directory.getName() + "' is not a directory.");
                } catch (NotDirectoryException e) {
                    e.printStackTrace();
                }
            }
        } else { // Directory doesn't exist.
            if (this.directory.mkdirs()) plugin.getLogger().info("'" + this.fileName + "' has been created.");
        }
        this.file = new File(this.directory, this.fileName);
        boolean created = false;
        if (!file.exists()) {
            if ((resourcePath != null && plugin.getResource(resourcePath) != null) || plugin.getResource(fileName) != null) {
                plugin.getLogger().info("Attempting to create '" + this.fileName + "' from resources.");
                plugin.saveResource(resourcePath != null ? resourcePath : fileName, false);
            }
            created = true;
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);
        if (created) onCreate();
        onReload();
    }

    public final void save() throws IOException {
        config.save(file);
    }

    public final boolean exists() {
        return this.file.exists();
    }

    public final FileConfiguration getConfig() {
        return this.config;
    }

    /**
     * Called when a file is created.
     */
    protected void onCreate() {
    }

    /**
     * Called when then file is reloaded
     */
    protected void onReload() {
    }
}