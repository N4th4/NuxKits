package com.bukkit.N4th4.NuxKits;

import java.io.File;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

public class NuxKits extends JavaPlugin {
    private final NuxKitsPlayerListener playerListener;
    private final HashMap<Player, Boolean> debugees;

    public NuxKits(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
        NuxKitsLogger.initialize();
        playerListener = new NuxKitsPlayerListener(this);
        debugees = new HashMap<Player, Boolean>();
    }
    public void onEnable() {
    	NuxKitsPermissions.initialize(getServer());
    	
        PluginManager pm = getServer().getPluginManager();
        /*pm.registerEvent(Event.Type.PLAYER_JOIN,  playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT,  playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_COMMAND,  playerListener, Priority.Normal, this);*/
        pm.registerEvent(Event.Type.PLAYER_JOIN,  playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_COMMAND,  playerListener, Priority.Normal, this);

        PluginDescriptionFile pdfFile = this.getDescription();
        NuxKitsLogger.info( pdfFile.getName() + " version " + pdfFile.getVersion() + " est activé !" );
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
