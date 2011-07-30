package net.krinsoft.commandsuite.util;

import java.util.logging.Logger;
import net.krinsoft.commandsuite.CommandSuite;

/**
 *
 * @author krinsdeath
 */

public class CommandLogger {

	private enum Level {
		INFO(0),
		WARN(1),
		SEVERE(2);

		private final int level;
		Level(int lv) {
			level = lv;
		}

		private static Level getLevel(int lv) {
			switch (lv) {
				case 0: return Level.INFO;
				case 1: return Level.WARN;
				case 2: return Level.SEVERE;
				default: return Level.INFO;
			}
		}

		public int getId() {
			return this.level;
		}
	}

	private CommandSuite plugin;
	private final static Logger LOGGER = Logger.getLogger("Cooldowns");
	private static String PREFIX;

	public CommandLogger() {
	}

	public void setParent(CommandSuite aThis) {
		plugin = aThis;
	}

	/**
	 * Logs a standard message [INFO]
	 * @param msg
	 * the message
	 */
	public void info(String msg) {
		PREFIX = validate(Level.INFO);
		msg = PREFIX + msg;
		msg = parse(msg);
		LOGGER.info(msg);
	}

	/**
	 * Logs a warning [WARNING]
	 * @param msg
	 * the message
	 */
	public void warn(String msg) {
		PREFIX = validate(Level.WARN);
		msg = PREFIX + msg;
		msg = parse(msg);
		LOGGER.warning(msg);
	}

	/**
	 * Logs a critical error [SEVERE]
	 * @param msg
	 * the message
	 */
	public void severe(String msg) {
		PREFIX = validate(Level.SEVERE);
		msg = PREFIX + msg;
		msg = parse(msg);
		LOGGER.warning(msg);
	}

	/**
	 * Returns a string to use as the log prefix
	 * @param lv
	 * The level of the message (INFO, WARN, SEVERE)
	 * @return
	 * the string
	 */
	private String validate(Level lv) {
		String os = "";
		try {
			os = System.getProperty("os.name");
		} catch (SecurityException e) {
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException e) {
		}
		if (os.contains("Windows")) {
			return "[" + plugin.info("name") + "] ";
		} else {
			switch (lv) {
				case INFO:		return "&A[" + plugin.info("name") + "] &F";
				case WARN:		return "&3[" + plugin.info("name") + "] &F";
				case SEVERE:	return "&C[" + plugin.info("name") + "] &F";
				default:		return "[" + plugin.info("name") + "] ";
			}
		}
	}

	private String parse(String msg) {
		msg = msg.replaceAll("<version>", plugin.info("version"));
		msg = msg.replaceAll("<name>", plugin.info("name"));
		msg = msg.replaceAll("<fullname>", plugin.info("fullname"));
		msg = msg.replaceAll("&([a-fA-F0-9])", "\u00A7$1");
		return msg;
	}
}
