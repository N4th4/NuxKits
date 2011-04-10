package com.bukkit.N4th4.NuxKits;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class NKPermissions {
    private static PermissionHandler Permissions;
    private static Server server;

    public static void initialize(NuxKits plugin) {
        server = plugin.getServer();
        Plugin test = server.getPluginManager().getPlugin("Permissions");

        if (test != null) {
            Permissions = ((Permissions) test).getHandler();
        } else {
            NKLogger.info("Permission system not detected, defaulting to OP");
        }
    }

    public static String getGroup(String playerName) {
        return Permissions.getGroup(server.getPlayer(playerName).getWorld().getName(), playerName);
    }

    public static boolean has(CommandSender player, String string) {
        if (player instanceof Player) {
            Player playerBis = (Player) player;
            return Permissions.has(playerBis, string);
        } else {
            return player.isOp();
        }
    }
}
