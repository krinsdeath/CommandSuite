package net.krinsoft.commandsuite.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.krinsoft.commandsuite.CommandSuite;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

/**
 *
 * @author krinsdeath
 */

public class Localization {
	private static CommandSuite plugin;
	private static HashMap<String, Configuration> locales = new HashMap<String, Configuration>();

	public Localization(CommandSuite inst) {
		plugin = inst;
	}

	public static void addLocale(File file) {
		Configuration conf = new Configuration(file);
		conf.load();
		locales.put(file.getName().split("\\.")[0], conf);
	}

	public static String getString(Player p, String path) {
		String loc = plugin.getUser(p.getName(), "locale");
		return locales.get(loc).getString(path);
	}

	public static List<String> getStringList(Player p, String path) {
		String loc = plugin.getUser(p.getName(), "locale");
		return locales.get(loc).getStringList(path, new ArrayList<String>());
	}

	public static boolean getBoolean(Player p, String path) {
		String loc = plugin.getUser(p.getName(), "locale");
		return locales.get(loc).getBoolean(path, false);
	}

	public static List<Boolean> getBooleanList(Player p, String path) {
		String loc = plugin.getUser(p.getName(), "locale");
		return locales.get(loc).getBooleanList(path, new ArrayList<Boolean>());
	}

	public static int getInt(Player p, String path) {
		String loc = plugin.getUser(p.getName(), "locale");
		return locales.get(loc).getInt(path, 0);
	}

	public static List<Integer> getIntList(Player p, String path) {
		String loc = plugin.getUser(p.getName(), "locale");
		return locales.get(loc).getIntList(path, new ArrayList<Integer>());
	}
}
