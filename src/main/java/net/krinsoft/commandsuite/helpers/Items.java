package net.krinsoft.commandsuite.helpers;

import net.krinsoft.commandsuite.CommandSuite;
import net.krinsoft.commandsuite.util.Messages;
import net.krinsoft.commandsuite.util.SimpleMat;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author krinsdeath
 */

public class Items {
	private static boolean i = false;
	private static CommandSuite plugin;

	public static void init(CommandSuite aThis) {
		plugin = aThis;
	}

	public static String give(CommandSender sender, String[] args) {
		CommandSuite plugin = (CommandSuite) sender.getServer().getPluginManager().getPlugin("CommandSuite");
		boolean perm = false;
		String key = "";
		if (sender.hasPermission("commandsuite.items.give") || sender instanceof ConsoleCommandSender) {
			key = "commands.give";
			perm = true;
		}
		if (i) {
			i = false;
			if (sender.hasPermission("commandsuite.items.item")) {
				perm = true;
				key = "commands.item";
			} else {
				perm = false;
			}
		}
		if (perm) {
			if (args.length >= 2) {
				if (plugin.getServer().getPlayer(args[0]) != null) {
					Material mat;
					byte data;
					int amt = 1;
					if (args.length >= 3) {
						try {
							amt = Integer.parseInt(args[2]);
						} catch (NumberFormatException e) {
							amt = 1;
						}
					}
					if (args[1].contains(":")) {
						if (args[1].split(":").length > 1) {
							mat = SimpleMat.match(args[1].split(":")[0]);
							if (mat == null) {
								return "no_such_material";
							}
							data = SimpleMat.data(mat.toString().toLowerCase(), args[1].split(":")[1]);
						} else {
							mat = SimpleMat.match(args[1].replace(":", ""));
							if (mat == null) {
								return "no_such_material";
							}
							data = 0;
						}
					} else {
						mat = SimpleMat.match(args[1]);
						if (mat == null) {
							return "no_such_material";
						}
						data = 0;
					}
					if (mat == null) {
						return "no_such_material";
					}
					ItemStack item = new ItemStack(mat, amt, (short) data, data);
					plugin.getServer().getPlayer(args[0]).getInventory().addItem(item);
					String tmp = item.getType().toString().toLowerCase().replaceAll("_", " ");
					String msg = plugin.getDefaultLocale().getString(key);
					if (msg != null) {
						msg = msg.replaceAll("%n", plugin.getServer().getPlayer(args[0]).getName());
						msg = msg.replaceAll("%a", "" + amt);
						msg = msg.replaceAll("%i", "" + tmp);
						msg = Messages.COLOR.matcher(msg).replaceAll("\u00A7$1");
						sender.sendMessage(msg);
					}
					return "success";
				}
			} else {
				return "not_enough_args";
			}
		} else {
			return "permission_denied";
		}
		return "generic";
	}

	public static String item(Player player, String[] args) {
		i = true;
		String[] tmp = new String[5];
		tmp[0] = player.getName();
		System.arraycopy(args, 0, tmp, 1, args.length);
		return give((CommandSender) player, tmp);
	}

	public static String max(Player player) {
		return "";
	}

}
