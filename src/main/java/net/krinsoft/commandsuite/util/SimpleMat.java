package net.krinsoft.commandsuite.util;

import net.krinsoft.commandsuite.CommandSuite;
import org.bukkit.Material;
import org.bukkit.util.config.Configuration;

/**
 *
 * @author krinsdeath
 */

public class SimpleMat {

    /**
     * Attempts to match a material from
     * @param string
     * @return
     */
    public static Material match(String string) {
        Material mat = null;
        try {
            int id = Integer.parseInt(string);
            mat = Material.getMaterial(id);
        } catch (NumberFormatException e) {
            mat = Material.matchMaterial(string);
            if (mat == null) {
                mat = SimpleMat.fetch(string);
            }
        }
        return mat;
    }

    public static Material fetch(String string) {
        Configuration items = CommandSuite.ITEMS;
        if (items.getKeys().contains(string)) {
            if (items.getKeys(string) == null) {
                return Material.getMaterial(items.getInt(string, 0));
            } else {
                return Material.getMaterial(items.getInt(string + "._self_", 0));
            }
        }
        return null;
    }

    public static byte data(String mat, String data) {
        Configuration items = CommandSuite.ITEMS;
        if (items.getKeys(mat) != null) {
            if (items.getKeys(mat).contains(data)) {
                return (byte) items.getInt(mat + "." + data, 0);
            }
        }
        try {
            byte d = Byte.parseByte(data);
            if (d > 15) {
                return 0;
            } else {
                return d;
            }
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
