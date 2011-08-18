package net.krinsoft.commandsuite.events;

import org.bukkit.Location;
import org.bukkit.event.Event;

/**
 *
 * @author krinsdeath
 */

public class CustomWeatherEvent extends Event {
	public enum Weather {
		OFF,
		SNOW,
		RAIN,
		STORM;
	}

	private Weather type;
	private Location where;

	public CustomWeatherEvent(final Location where, final Weather type) {
		super("CustomWeatherEvent");
		this.where = where;
		this.type = type;
	}

	public Location getLocation() {
		return this.where;
	}

	public Weather getWeather() {
		return this.type;
	}
}
