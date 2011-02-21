package com.bukkit.N4th4.NuxKits;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

public class NuxKits extends JavaPlugin {
    private final NKPlayerListener playerListener;
    private final HashMap<Player, Boolean> debugees;

    public NuxKits() {
        NKLogger.initialize();
        playerListener = new NKPlayerListener(this);
        debugees = new HashMap<Player, Boolean>();
    }

    public void onEnable() {
        NKPermissions.initialize(getServer());

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Normal, this);

        PluginDescriptionFile pdfFile = this.getDescription();
        NKLogger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " est activé !");
    }

    public void onDisable() {
    }

    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
}
