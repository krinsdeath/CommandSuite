package net.krinsoft.commandsuite.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import net.krinsoft.commandsuite.CommandSuite;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 *
 * @author krinsdeath
 */

public class Messages {
	public final static Pattern COLOR = Pattern.compile("&([a-fA-F0-9])");
	public static boolean motd;
	public static boolean rules;
	public static boolean afk;
	public static boolean chat;

	private static CommandSuite plugin;

	public static void init(CommandSuite inst) {
		plugin = inst;
		motd = inst.getConfiguration().getBoolean("settings.motd", true);
		rules = inst.getConfiguration().getBoolean("settings.rules", true);
		afk = inst.getConfiguration().getBoolean("settings.afk", true);
		chat = inst.getConfiguration().getBoolean("settings.chat", true);
	}

	public static void worldBroadcast(World world, String msg) {
		msg = COLOR.matcher(msg).replaceAll("\u00A7$1");
		for (Player p : world.getPlayers()) {
			p.sendMessage(msg);
		}
	}

	public static void showMotd(Player player) {
		List<String> msg = plugin.getConfiguration().getStringList("motd", new ArrayList<String>());
		if (msg == null) { return; }
		for (String line : msg) {
			line = line.replaceAll("<plugin>", plugin.info("fullname"));
			line = line.replaceAll("<authors>", plugin.info("authors"));
			line = line.replaceAll("<name>", player.getName());
			line = COLOR.matcher(line).replaceAll("\u00A7$1");
			player.sendMessage(line);
		}
	}

	public static void showRules(Player player) {
		List<String> msg = plugin.getConfiguration().getStringList("rules", new ArrayList<String>());
		if (msg == null) { return; }
		for (String line : msg) {
			line = COLOR.matcher(line).replaceAll("\u00A7$1");
			player.sendMessage(line);
		}
	}
}
