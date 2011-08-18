package net.krinsoft.commandsuite.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import net.krinsoft.commandsuite.CommandSuite;
import org.bukkit.util.config.Configuration;

/**
 *
 * @author krinsdeath
 */
public class Setup {

	private CommandSuite plugin;
	private File dataFolder;

	public Setup(CommandSuite aThis) {
		plugin = aThis;
		dataFolder = plugin.getDataFolder();
		load();
	}

	private void load() {
		Configuration conf = null;
		File tmp = makeConfig(new File(dataFolder, "config.yml"));
		if (tmp.exists()) {
			conf = new Configuration(tmp);
			conf.load();
			plugin.setConfiguration(conf);
		} else {
			plugin.getLogger().warn("Something is wrong.");
		}
		tmp = makeConfig(new File(dataFolder, "items.yml"));
		if (tmp.exists()) {
			conf = new Configuration(tmp);
			conf.load();
			CommandSuite.ITEMS = conf;
		} else {
			plugin.getLogger().warn("Something is wrong.");
		}
		List<String> locs = plugin.getConfiguration().getStringList("plugin.available_locales", new ArrayList<String>());
		for (String key : locs) {
			tmp = makeConfig(new File(dataFolder + File.separator + "languages", key + ".yml"));
			if (tmp.exists()) {
				plugin.addLocale(key, tmp);
				if (plugin.getConfiguration().getString("plugin.default_locale", "en_US").equalsIgnoreCase(key)) {
					plugin.setDefaultLocale(key);
				}
			} else {
				plugin.getLogger().warn(tmp.getName() + " doesn't exist: removing it from locale list");
				locs.remove(key);
				plugin.getConfiguration().setProperty("plugin.available_locales", locs);
				plugin.getConfiguration().save();
			}
		}
		tmp = new File(dataFolder + File.separator + "users", "users.yml");
		tmp.getParentFile().mkdirs();
		plugin.setUsers(tmp);
	}

	private File makeConfig(File file) {
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			InputStream in = CommandSuite.class.getResourceAsStream("/defaults/" + file.getName());
			FileOutputStream out = null;
			if (in != null) {
				try {
					out = new FileOutputStream(file);
					byte[] buffer = new byte[128];
					int length = 0;
					while ((length = in.read(buffer)) > 0) {
						out.write(buffer, 0, length);
					}
					plugin.getLogger().info(file.getName() + " created successfully.");
				} catch (IOException e) {
					plugin.getLogger().severe("Error creating file " + file.getName() + ": " + e);
				} finally {
					try {
						in.close();
						out.close();
					} catch (IOException e) {
						plugin.getLogger().severe("Error closing stream: " + e);
					}
				}
			} else {
				plugin.getLogger().severe("Can't find resource " + file.getName() + "... aborting.");
			}
		}
		return file;
	}
}
