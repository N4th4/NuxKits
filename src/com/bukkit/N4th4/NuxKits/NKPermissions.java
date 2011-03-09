package com.bukkit.N4th4.NuxKits;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.dataholder.worlds.WorldsHolder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class NKPermissions {
    private static WorldsHolder wd;

    public static void initialize(NuxKits plugin) {
        Plugin p = plugin.getServer().getPluginManager().getPlugin("GroupManager");
        if (p != null) {
            if (!plugin.getServer().getPluginManager().isPluginEnabled(p)) {
                plugin.getServer().getPluginManager().enablePlugin(p);
            }
            GroupManager gm = (GroupManager) p;
            wd = gm.getWorldsHolder();
        } else {
            NKLogger.severe("Plugin GroupManager not found");
            plugin.getPluginLoader().disablePlugin(plugin);
        }
    }

    public static String getGroup(String playerName) {
        return wd.getWorldPermissionsByPlayerName(playerName).getGroup(playerName);
    }

    public static boolean has(CommandSender player, String string) {
        if (player instanceof Player) {
            Player playerBis = (Player) player;
            return wd.getWorldPermissions(playerBis).has(playerBis, string);
        } else {
            return player.isOp();
        }
    }
}
