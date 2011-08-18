package net.krinsoft.commandsuite;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.krinsoft.commandsuite.helpers.Admin;
import net.krinsoft.commandsuite.helpers.Items;
import net.krinsoft.commandsuite.helpers.Teleport;
import net.krinsoft.commandsuite.listeners.CommandListener;
import net.krinsoft.commandsuite.listeners.CustomListener;
import net.krinsoft.commandsuite.listeners.PlayerListener;
import net.krinsoft.commandsuite.listeners.WeatherListener;
import net.krinsoft.commandsuite.player.Commander;
import net.krinsoft.commandsuite.util.CommandLogger;
import net.krinsoft.commandsuite.util.Messages;
import net.krinsoft.commandsuite.util.Setup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

/**
 *
 * @author krinsdeath
 */
public class CommandSuite extends JavaPlugin {
	protected final CommandLogger LOGGER = new CommandLogger(this);

	// private final members
	private final PlayerListener pListener = new PlayerListener(this);
	private final CommandListener cListener = new CommandListener(this);
	private final WeatherListener wListener = new WeatherListener(this);
	private final CustomListener custom = new CustomListener(this);
	private final List<String> commands = Arrays.asList(
			"give", "item", "max", "time", "kick", "ban", "pardon", "stop", "reload",
			"tp", "tpto", "tphere", "tpall", "back", "rewind",
			"list", "say");
	// public static variables
	public static Configuration ITEMS;
	// public variables
	public PluginDescriptionFile info;
	public PluginManager manager;
	public Setup settings;
	public static Plugin plugin;
	// private configurations
	private HashMap<String, Configuration> locales = new HashMap<String, Configuration>();
	private String default_locale;
	private Configuration config;
	private Configuration users;
	public boolean stopping;
	public long safeReload = 0;
	private HashMap<String, Commander> players = new HashMap<String, Commander>();

	@Override
	public void onEnable() {
		plugin = this;
		manager = getServer().getPluginManager();
		info = getDescription();
		settings = new Setup(this);

		PluginManager manager = getServer().getPluginManager();
		manager.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, pListener, Event.Priority.Normal, this);
		manager.registerEvent(Event.Type.PLAYER_JOIN, pListener, Event.Priority.Normal, this);
		manager.registerEvent(Event.Type.PLAYER_QUIT, pListener, Event.Priority.Normal, this);
		manager.registerEvent(Event.Type.PLAYER_KICK, pListener, Event.Priority.Normal, this);
		manager.registerEvent(Event.Type.CUSTOM_EVENT, custom, Event.Priority.Normal, this);
		manager.registerEvent(Event.Type.WEATHER_CHANGE, wListener, Event.Priority.Normal, this);
		manager.registerEvent(Event.Type.THUNDER_CHANGE, wListener, Event.Priority.Normal, this);

		Messages.init(this);
		Admin.init(this);
		Items.init(this);
		Teleport.init(this);
		getLogger().info(getDefaultLocale().getString("plugin.enabled"));
	}

	@Override
	public void onDisable() {
		plugin = null;

		getLogger().info(getDefaultLocale().getString("plugin.disabled"));
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
			return locales.get(default_locale);
		}
	}

	public Configuration getLocale(Player player) {
		return locales.get(players.get(player.getName()).getLocale());
	}	/**
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
			return info.getAuthors().toString();
		}
		return info.getName();
	}

	public String getUser(String name, String string) {
		return users.getString(name + "." + string);
	}

	public void newUser(Player player) {
		
	}

	public void setUsers(File tmp) {
		Configuration c = new Configuration(tmp);
		c.load();
		users = c;
	}
}
