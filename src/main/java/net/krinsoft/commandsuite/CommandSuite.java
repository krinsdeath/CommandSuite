package net.krinsoft.commandsuite;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.krinsoft.commandsuite.listeners.CommandListener;
import net.krinsoft.commandsuite.listeners.PlayerListener;
import net.krinsoft.commandsuite.util.CommandLogger;
import net.krinsoft.commandsuite.util.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

/**
 *
 * @author krinsdeath
 */

public class CommandSuite extends JavaPlugin {
	protected final CommandLogger LOGGER = new CommandLogger();

    // private final members
    private final PlayerListener pListener = new PlayerListener(this);
    private final CommandListener cListener = new CommandListener(this);
    private final List<String> commands = Arrays.asList(
            "give", "item", "max", "time", "kick", "ban", "pardon", "stop", "reload",
            "tp", "tpto", "tphere", "tpall", "back", "rewind",
            "list", "say");

    // public static variables
    public static Configuration ITEMS;
    // public variables
    public PluginDescriptionFile info;
    public PluginManager manager;
    public Settings settings;

    // private configurations
    private HashMap<String, Configuration> locales = new HashMap<String, Configuration>();
    private String default_locale;
    private Configuration config;
    public boolean stopping;
    public long safeReload = 0;

    @Override
    public void onEnable() {
		LOGGER.setParent(this);
        manager = getServer().getPluginManager();
        info = getDescription();
        settings = new Settings(this);

		getLogger().info(getDefaultLocale().getString("plugin.enabled"));
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return cListener.onCommand(sender, cmd, label, args);
    }

    /**
     * Gets the default localization for the plugin
     * @return
     * the localization
     */
    public Configuration getDefaultLocale() {
        return locales.get(default_locale);
    }

    /**
     * sets the default localization file for the console
     *
     * @param locale
     */
    public void setDefaultLocale(String locale) {
        default_locale = locale;
    }
    /**
     * Gets the localization based on the key provided
     *
     * @param key
     * The key with with to search through the localizations
     * @return
     * The localization if successful, otherwise null
     */
    public Configuration getLocale(String key) {
        if (locales.containsKey(key)) {
            return locales.get(key);
        } else {
            return null;
        }
    }

    /**
     * Adds a localization to the HashMap and loads it
     * @param key
     * The key by which the localization is accessed
     * @param locale
     * The localization to load into the HashMap
     */
    public void addLocale(String key, File locale) {
        Configuration loc = new Configuration(locale);
        loc.load();
        locales.put(key, loc);
    }

    /**
     * Sets the configuration for this plugin
     * 
     * @param conf
     * the configuration to set
     */
    public void setConfiguration(Configuration conf) {
        config = conf;
    }

    /**
     * Gets the configuration for this plugin
     *
     * @return config
     * The Configuration for this plugin
     */
    @Override
    public Configuration getConfiguration() {
        return config;
    }

	/**
	 * Gets the logger for this plugin
	 * @return
	 * the logger
	 */
	public CommandLogger getLogger() {
		return LOGGER;
	}

	/**
	 * Returns plugin information
	 * @param field
	 * The field to fetch from plugin.yml
	 * @return
	 * the associated information
	 */
	public String info(String field) {
		if (field.equalsIgnoreCase("fullname")) {
			return info.getFullName();
		} else if (field.equalsIgnoreCase("name")) {
			return info.getName();
		} else if (field.equalsIgnoreCase("version")) {
			return info.getVersion();
		} else if (field.equalsIgnoreCase("authors")) {
			return info.getAuthors().get(0);
		}
		return info.getName();
	}
}
