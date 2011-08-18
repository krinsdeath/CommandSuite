package net.krinsoft.commandsuite.helpers;

import net.krinsoft.commandsuite.CommandSuite;
import net.krinsoft.commandsuite.util.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author krinsdeath
 */

public class Admin {
	private static CommandSuite plugin;

	public static void init(CommandSuite inst) {
		plugin = inst;
	}

	public static boolean time(CommandSender sender, String[] args) {
		if (sender.hasPermission("commandsuite.admins.time")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args.length == 0) {
					player.getWorld().setTime(0);
					player.getServer().broadcastMessage(ChatColor.GREEN + "Time set to 0 on " + ChatColor.AQUA + player.getWorld().getName());
					return true;
				} else if (args.length >= 1) {
					int time = 0;
					if (args.length == 2) {
						try {
							time = Integer.parseInt(args[1]);
						} catch (NumberFormatException e) {
							time = 0;
						}
					}
					if (args[0].equalsIgnoreCase("set")) {
						player.getWorld().setTime(time);
						player.getServer().broadcastMessage(ChatColor.GREEN + "Time set to " + ChatColor.AQUA + time + ChatColor.GREEN + " on " + ChatColor.AQUA + player.getWorld().getName());
						return true;
					} else if (args[0].equalsIgnoreCase("add")) {
						player.getWorld().setTime(player.getWorld().getTime() + time);
						player.getServer().broadcastMessage(ChatColor.AQUA + "" + time + "" + ChatColor.GREEN + " has been added to the time");
						return true;
					} else if (args[0].equalsIgnoreCase("day")) {
						player.getWorld().setTime(0);
						player.getServer().broadcastMessage(ChatColor.GREEN + "It is now " + ChatColor.AQUA + "daytime" + ChatColor.GREEN + " on " + ChatColor.AQUA + player.getWorld().getName());
						return true;
					} else if (args[0].equalsIgnoreCase("night")) {
						player.getWorld().setTime(14400);
						player.getServer().broadcastMessage(ChatColor.GREEN + "It is now " + ChatColor.AQUA + "nighttime" + ChatColor.GREEN + " on " + ChatColor.AQUA + player.getWorld().getName());
						return true;
					} else if (args[0].equalsIgnoreCase("dusk")) {
						player.getWorld().setTime(12000);
						player.getServer().broadcastMessage(ChatColor.GREEN + "It is now " + ChatColor.AQUA + "dusk" + ChatColor.GREEN + " on " + ChatColor.AQUA + player.getWorld().getName());
						return true;
					} else if (args[0].equalsIgnoreCase("dawn")) {
						player.getWorld().setTime(23000);
						player.getServer().broadcastMessage(ChatColor.GREEN + "It is now " + ChatColor.AQUA + "dawn" + ChatColor.GREEN + " on " + ChatColor.AQUA + player.getWorld().getName());
						return true;
					} else {
						player.getWorld().setTime(0);
						player.getServer().broadcastMessage(ChatColor.GREEN + "Time set to " + ChatColor.AQUA + "0" + ChatColor.GREEN + " on " + ChatColor.AQUA + player.getWorld().getName());
						return true;
					}
				} else {
				}
			} else if (sender instanceof ConsoleCommandSender) {
				for (World world : sender.getServer().getWorlds()) {
					world.setTime(0);
					sender.getServer().broadcastMessage(ChatColor.GREEN + "Time set to 0 on " + ChatColor.AQUA + world.getName());
					System.out.println("Time set to 0 on " + world.getName());
				}
				return true;
			}
		}
		return false;
	}

	public static void weather(CommandSender sender, String type, int duration) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			World world = player.getWorld();
			if (type.equalsIgnoreCase("status")) {
				// TODO - Localizations
				String msg = null;
				int ticks = world.getWeatherDuration();
				if (ticks > 24000) {
					msg = plugin.getLocale(player).getString("weather.long", "The weather shows no signs of relenting.");
				} else if (ticks > 6000) {
					msg = plugin.getLocale(player).getString("weather.soon", "The weather should change soon.");
				} else if (ticks > 600) {
					msg = plugin.getLocale(player).getString("weather.clearing", "The skies are starting to clear.");
				} else {
					msg = plugin.getLocale(player).getString("weather.now", "The skies are clearing.");
				}
				player.sendMessage(msg);
				return;
			}
			if (type.equalsIgnoreCase("off") || type.equalsIgnoreCase("stop")) {
				if (world.hasStorm()) {
					world.setWeatherDuration(1);
					world.setStorm(false);
				}
				if (world.isThundering()) {
					world.setThunderDuration(1);
					world.setThundering(false);
				}
				return;
			}
			if (type.equalsIgnoreCase("rain")) {
				if (world.hasStorm()) { return; }
				world.setStorm(true);
				world.setThundering(false);
				world.setWeatherDuration(duration);
				return;
			}
			if (type.equalsIgnoreCase("snow")) {
				Location loc = player.getLocation();
				if (world.getBiome((int)loc.getX(), (int)loc.getZ()).equals(Biome.TUNDRA)) {
					if (world.hasStorm()) { return; }
					world.setStorm(true);
					world.setThundering(false);
					world.setWeatherDuration(duration);
				} else {
					player.sendMessage("It can't snow here.");
				}
				return;
			}
			if (type.equalsIgnoreCase("storm")) {
				if (!world.hasStorm()) {
					world.setStorm(true);
					world.setWeatherDuration(duration);
				}
				if (world.isThundering()) {
					world.setThundering(true);
					world.setThunderDuration(duration);
				}
				return;
			}
		}
	}

	public static boolean kick(CommandSender sender, String[] args) {
		CommandSuite plugin = (CommandSuite) sender.getServer().getPluginManager().getPlugin("CommandSuite");
		if (sender.hasPermission("commandsuite.admins.kick") || sender instanceof ConsoleCommandSender) {
			if (args.length >= 1) {
				if (sender.getServer().getPlayer(args[0]) != null) {
					StringBuilder msg = new StringBuilder();
					String the = "";
					if (args.length >= 2) {
						msg.append(args[1]);
						for (int i = 2; i < args.length; i++) {
							msg.append(" ");
							msg.append(args[i]);
						}
					} else {
						msg.append(plugin.getDefaultLocale().getString("admin.player_kick", "%n has been kicked."));
						the = msg.toString();
						the = the.replaceAll("%n", args[0]);
					}
					the = Messages.COLOR.matcher(the).replaceAll("\u00A7$1");
					sender.getServer().getPlayer(args[0]).kickPlayer(the);
					return true;
				}
			}
		}
		return false;
	}

	public static boolean ban(CommandSender sender, String[] args) {
		if (sender.hasPermission("commandsuite.admins.ban")) {

		}
		return false;
	}

	public static boolean unban(CommandSender sender, String[] args) {
		if (sender.hasPermission("commandsuite.admins.unban")) {
			
		}
		return false;
	}
}
