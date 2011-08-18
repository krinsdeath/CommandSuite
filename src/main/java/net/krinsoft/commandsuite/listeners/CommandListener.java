package net.krinsoft.commandsuite.listeners;

import java.util.ArrayList;
import java.util.List;
import net.krinsoft.commandsuite.CommandSuite;
import net.krinsoft.commandsuite.helpers.Admin;
import net.krinsoft.commandsuite.helpers.Items;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author krinsdeath
 */

public class CommandListener implements CommandExecutor {
    private CommandSuite plugin;

    public CommandListener(CommandSuite aThis) {
        plugin = aThis;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        /*
         * Item based commands
         */

        // Handler for '/give'
        if (cmd.getName().equalsIgnoreCase("give")) {
			String error = Items.give(sender, args);
			if (error.equals("success")) {
				return true;
			} else if (error.equals("no_such_material")) {
				error(sender, "no_such_material");
				return true;
			} else if (error.equals("permission_denied")) {
				error(sender, "permission_denied");
				return true;
			} else if (error.equals("not_enough_args")) {
				error(sender, "not_enough_args");
				usage(sender, "give", label);
				return true;
			} else if (error.equals("generic")) {
				error(sender, "generic");
				usage(sender, "give", label);
				return true;
			}
        }

        // Handler for '/item'
        if (cmd.getName().equalsIgnoreCase("item")) {
			if (checkPlayer(sender)) {
				String error = Items.item((Player) sender, args);
				if (error.equals("success")) {
					return true;
				} else if (error.equals("no_such_material")) {
					error(sender, "no_such_material");
					return true;
				} else if (error.equals("permission_denied")) {
					error(sender, "permission_denied");
					return true;
				} else if (error.equals("not_enough_args")) {
					error(sender, "not_enough_args");
					usage(sender, "item", label);
					return true;
				} else if (error.equals("generic")) {
					error(sender, "generic");
					usage(sender, "item", label);
					return true;
				}
			}
            return true;
        }
        
        // Handler for '/max'
        if (cmd.getName().equalsIgnoreCase("max")) {
			if (!checkPlayer(sender)) {
				error(sender, "player_needed");
				return true;
			}
			Player player = (Player) sender;
			if (player.hasPermission("commandsuite.items.max")) {
				if (!checkPlayer(sender)) {
					error(sender, "player_needed");
					return true;
				}
				if (player.getItemInHand().getType() != Material.AIR) {
					player.getItemInHand().setAmount(player.getItemInHand().getMaxStackSize());
					return true;
				} else {
					error(sender, "nothing_in_hand");
					return true;
				}
			} else {
				error(sender, "permission_denied");
	            return true;
			}
        }

        /*
         * Administrative commands
         */

        // Handler for '/time'
        if (cmd.getName().equalsIgnoreCase("time")) {
			if (Admin.time(sender, args)) {
				return true;
			} else {
				error(sender, "permission_denied");
			}
        }

		// Handler for '/weather'
		if (cmd.getName().equalsIgnoreCase("weather")) {
			int duration = 0;
			if (args.length >= 2) {
				try {
					duration = Integer.parseInt(args[1]) * 1200;
				} catch (NumberFormatException e) {
					duration = 1200;
				}
			} else {
				duration = 1200;
			}
			if (sender.hasPermission("commandsuite.admins.weather")) {
				if (label.equalsIgnoreCase("rain")) {
					Admin.weather(sender, "rain", duration);
					return true;
				} else if (label.equalsIgnoreCase("storm")) {
					Admin.weather(sender, "storm", duration);
					return true;
				} else if (label.equalsIgnoreCase("snow")) {
					Admin.weather(sender, "snow", duration);
					return true;
				}
			}
			if (args.length == 0) {
				Admin.weather(sender, "status", 0);
				return true;
			} else {
				if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("stop")) {
					Admin.weather(sender, "off", 0);
					return true;
				}
				if (sender.hasPermission("commandsuite.admins.weather")) {
					if (args[0].equalsIgnoreCase("rain")) {
						Admin.weather(sender, "rain", duration);
						return true;
					} else if (args[0].equalsIgnoreCase("storm")) {
						Admin.weather(sender, "storm", duration);
						return true;
					} else if (args[0].equalsIgnoreCase("snow")) {
						Admin.weather(sender, "snow", duration);
						return true;
					}
				}
			}
		}

        // Handler for '/kick'
		if (cmd.getName().equalsIgnoreCase("kick")) {
			if (Admin.kick(sender, args)) {
				return true;
			} else {
				usage(sender, "kick", label);
				return true;
			}
		}

        // Handler for '/ban'
		if (cmd.getName().equalsIgnoreCase("ban")) {
			return Admin.ban(sender, args);
		}

        // Handler for '/pardon'
		if (cmd.getName().equalsIgnoreCase("unban")) {
			return Admin.unban(sender, args);
		}

        return false;
    }

    private boolean checkPlayer(CommandSender sender) {
        if (sender instanceof Player) {
            return true;
        } else {
            return false;
        }
    }

    private void error(CommandSender target, String path) {
        String msg = plugin.getDefaultLocale().getString("errors." + path);
        if (msg != null) {
    		msg = msg.replaceAll("&([a-f0-9A-F])", "\u00A7$1");
            target.sendMessage(ChatColor.RED + msg);
        }
    }

	private void usage(CommandSender target, String path, String label) {
		List<String> msg = plugin.getDefaultLocale().getStringList("usage." + path, new ArrayList<String>());
		for (String line : msg) {
			line = line.replaceAll("&([a-f0-9A-F])", "\u00A7$1");
			line = line.replaceAll("<command>|<cmd>", label);
			target.sendMessage(line);
		}
	}
}
