package xyz.ufactions.customstaff.file;

import org.bukkit.plugin.java.JavaPlugin;

public class LanguageFile extends FileHandler {

    public enum Language {
        ENGLISH("language_en");

        private final String resourcePath;

        Language(String resourcePath) {
            this.resourcePath = resourcePath;
        }
    }

    public enum LanguagePath {

        NOPERM("no-permission"),
        NOPLAYER("no-player"),
        NOSTAFF("no-staff"),
        STAFFLIST_HIDDEN("stafflist-hidden"),
        STAFFLIST_VISIBLE("stafflist-visible"),
        STAFFLIST_HEADER("stafflist-header"),
        STAFFLIST_REPEATABLE("stafflist-repeatable"),
        STAFFCHAT_ENABLED("staffchat-enabled"),
        STAFFCHAT_DISABLED("staffchat-disabled");

        private final String path;

        LanguagePath(String path) {
            this.path = path;
        }
    }

    public LanguageFile(JavaPlugin plugin, Language language) {
        super(plugin, language.resourcePath, plugin.getDataFolder(), "language.yml");
    }

    public String get(LanguagePath path) {
        return getString(path.path);
    }
}