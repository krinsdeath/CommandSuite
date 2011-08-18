package net.krinsoft.commandsuite.listeners;

import java.util.Arrays;
import java.util.List;
import net.krinsoft.commandsuite.CommandSuite;
import net.krinsoft.commandsuite.util.Messages;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 *
 * @author krinsdeath
 */

public class WeatherListener extends org.bukkit.event.weather.WeatherListener {
	private List<Biome> rain = Arrays.asList(Biome.FOREST, Biome.PLAINS, Biome.SAVANNA, Biome.SWAMPLAND, Biome.RAINFOREST, Biome.SEASONAL_FOREST, Biome.SHRUBLAND, Biome.SKY);
	private List<Biome> snow = Arrays.asList(Biome.TUNDRA, Biome.ICE_DESERT);
	private CommandSuite plugin;

	public WeatherListener(CommandSuite instance) {
		plugin = instance;
	}

	@Override
	public void onWeatherChange(WeatherChangeEvent event) {
		if (event.isCancelled()) { return; }
		boolean w = event.toWeatherState();
		World world = event.getWorld();
		Location loc = null;
		int x = 0, z = 0;
		String msg = null;
		for (Player p : world.getPlayers()) {
			loc = p.getLocation();
			x = (int)loc.getX();
			z = (int)loc.getZ();
			if (rain.contains(world.getBiome(x, z))) {
				if (w) {
					msg = "It has started raining.";
				} else {
					msg = "The rain has stopped.";
				}
			} else if (snow.contains(world.getBiome(x, z))) {
				if (w) {
					msg = "It has started snowing.";
				} else {
					msg = "The snow has stopped.";
				}
			} else {
				msg = null;
			}
			if (msg != null) {
				p.sendMessage(msg);
			}
		}
	}

	@Override
	public void onThunderChange(ThunderChangeEvent event) {
		if (event.isCancelled()) { return; }
		if (event.toThunderState()) {
			Messages.worldBroadcast(event.getWorld(), "A storm is starting!");
		}
	}
}
