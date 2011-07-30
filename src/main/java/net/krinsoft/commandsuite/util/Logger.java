package net.krinsoft.commandsuite.util;

import net.krinsoft.commandsuite.CommandSuite;

/**
 *
 * @author krinsdeath
 */

public class Logger {
    private final java.util.logging.Logger log;
    private final CommandSuite plugin;
    private String prefix;

    public Logger(CommandSuite aThis, java.util.logging.Logger logger) {
        plugin = aThis;
        log = logger;
        prefix = "[" + plugin.info.getFullName() + "] ";
    }

    public void info(String msg) {
        log.info(msg);
    }

    public void warn(String msg) {
        log.warning(msg);
    }
}
