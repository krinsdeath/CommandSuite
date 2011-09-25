package net.krinsoft.commandsuite.listeners;

import net.krinsoft.commandsuite.CommandSuite;
import net.krinsoft.commandsuite.util.Messages;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author krinsdeath
 */

public class PlayerListener extends org.bukkit.event.player.PlayerListener {
    private CommandSuite plugin;

    public PlayerListener(CommandSuite aThis) {
        plugin = aThis;
    }

    @Override
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        // set up some vars
        Player player = event.getPlayer();
        String cmd = event.getMessage().split(" ")[0].substring(1);
        // Handler for '/reload'
        if (cmd.equalsIgnoreCase("reload")) {
            if (player.hasPermission("commandsuite.reload")) {
                // success! let's make sure it hasn't been reloaded recently
                if (plugin.safeReload > System.currentTimeMillis()) {
                    // too soon, let's cool off for a bit
                    return;
                }
                // let's reload
                plugin.safeReload = System.currentTimeMillis() + 10000;
                player.sendMessage("Confugrations reloading.");
                plugin.getServer().reload();
                event.setCancelled(true);
                return;
            } else {
                // permission denied
                error(player, "permission_denied");
                event.setCancelled(true);
                return;
            }
        }

        // Handler for '/stop'
        if (cmd.equalsIgnoreCase("stop")) {
            // check for permission
            if (player.hasPermission("commandsuite.stop")) {
                // success!
                // let's make sure it hasn't been reloaded recently
                if (plugin.safeReload > System.currentTimeMillis() && plugin.safeReload != 0) {
                    return;
                }
                // let's schedule the shutdown
                plugin.getServer().broadcastMessage("Server going down in 5 seconds.");
                plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        plugin.stopping = true;
                        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "reload");
                    }
                }, 100);
                event.setCancelled(true);
                return;
            } else {
                // player doesn't have permission
                error(player, "permission_denied");
                event.setCancelled(true);
                return;
            }
        }
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (plugin.getUser(event.getPlayer().getName(), "locale") == null) {
			plugin.newUser(event.getPlayer());
		}
		if (Messages.motd) {
			Messages.showMotd(event.getPlayer());
		}
		if (Messages.rules) {
			Messages.showRules(event.getPlayer());
		}
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent event) {

    }

    @Override
    public void onPlayerKick(PlayerKickEvent event) {

    }
    /*
     * methods
     */
    private void error(Player target, String path) {
        String msg = plugin.getDefaultLocale().getString("errors." + path);
        if (msg != null) {
    		msg = msg.replaceAll("&([a-f0-9A-F])", "\u00A7$1");
            target.sendMessage(msg);
        }
    }
}
