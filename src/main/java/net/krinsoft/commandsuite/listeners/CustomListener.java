package net.krinsoft.commandsuite.listeners;

import net.krinsoft.commandsuite.CommandSuite;
import net.krinsoft.commandsuite.events.CustomWeatherEvent;
import net.krinsoft.commandsuite.events.CustomWeatherEvent.Weather;
import net.krinsoft.commandsuite.util.Messages;
import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;

/**
 *
 * @author krinsdeath
 */

public class CustomListener extends CustomEventListener {
	private CommandSuite plugin;

	public CustomListener(CommandSuite instance) {
		plugin = instance;
	}

	@Override
	public void onCustomEvent(Event event) {
		if (event instanceof CustomWeatherEvent) {
			CustomWeatherEvent evt = (CustomWeatherEvent) event;
			if (evt.getWeather() == Weather.SNOW) {
				Messages.worldBroadcast(evt.getLocation().getWorld(), "It has started snowing.");
			} else if (evt.getWeather() == Weather.RAIN) {
				Messages.worldBroadcast(evt.getLocation().getWorld(), "It has started raining.");
			} else if (evt.getWeather() == Weather.STORM) {
				Messages.worldBroadcast(evt.getLocation().getWorld(), "A storm is starting!");
			}
		}
	}


}
