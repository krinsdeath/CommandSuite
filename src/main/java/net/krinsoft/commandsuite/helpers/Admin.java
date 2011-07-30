package net.krinsoft.commandsuite.helpers;

import net.krinsoft.commandsuite.CommandSuite;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author krinsdeath
 */

public class Admin {

	public static boolean time(CommandSender sender, String[] args) {
		if (sender.hasPermission("commandsuite.admin.time")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args.length == 0) {
					player.getWorld().setTime(0);
					player.getServer().broadcastMessage(ChatColor.GREEN + "Time set to 0 on " + ChatColor.AQUA + player.getWorld().getName());
					return true;
				} else if (args.length >= 1) {
					if (args[0].equalsIgnoreCase("set")) {
						int time = 0;
						if (args.length == 2) {
							try {
								time = Integer.parseInt(args[1]);
							} catch (NumberFormatException e) {
								time = 0;
							}
						}
						player.getWorld().setTime(time);
						player.getServer().broadcastMessage(ChatColor.GREEN + "Time set to " + ChatColor.AQUA + time + ChatColor.GREEN + " on " + ChatColor.AQUA + player.getWorld().getName());
						return true;
					} else if (args[0].equalsIgnoreCase("add")) {
						int time = 0;
						if (args.length == 2) {
							try {
								time = Integer.parseInt(args[1]);
							} catch (NumberFormatException e) {
								time = 0;
							}
						}
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

	public static boolean kick(CommandSender sender, String[] args) {
		CommandSuite plugin = (CommandSuite) sender.getServer().getPluginManager().getPlugin("CommandSuite");
		if (sender.hasPermission("commandsuite.admin.kick") || sender instanceof ConsoleCommandSender) {
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
					the = the.replaceAll("&([a-f0-9A-F])", "\u00A7$1");
					sender.getServer().getPlayer(args[0]).kickPlayer(the);
					System.out.println(args[0] + " has been kicked: " + msg);
					return true;
				}
			}
		}
		return false;
	}

	public static boolean ban(CommandSender sender, String[] args) {
		if (sender.hasPermission("commandsuite.admin.ban")) {

		}
		return false;
	}

	public static boolean unban(CommandSender sender, String[] args) {
		if (sender.hasPermission("commandsuite.admin.unban")) {
			
		}
		return false;
	}
}
