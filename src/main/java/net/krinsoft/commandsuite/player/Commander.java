package net.krinsoft.commandsuite.player;

import net.krinsoft.commandsuite.CommandSuite;

/**
 *
 * @author krinsdeath
 */

public class Commander {
	// ------- //
	// STATICS //
	// ------- //
	protected static CommandSuite plugin;

	public static void init(CommandSuite inst) {
		plugin = inst;
	}

	private String name;
	private String locale;
	private String group;

	public Commander(String name) {
		this.name = name;
		this.locale = plugin.getUser(name, "locale");
		this.group = plugin.getUser(name, "group");
	}

    public String getName() {
        return this.name;
    }

	public String getLocale() {
		return this.locale;
	}

	public String getGroup() {
		return this.group;
	}
}
