package com.bukkit.N4th4.NuxKits;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.config.Configuration;

public class NKPlayerListener extends PlayerListener {
    private final NuxKits plugin;
    private Configuration config;

    public NKPlayerListener(NuxKits instance) {
        plugin = instance;
        loadConfig();
    }

    public void onPlayerCommand(PlayerChatEvent event) {
        String[] command = event.getMessage().split(" ");
        if (command[0].equalsIgnoreCase("/NuxKits")) {
            Player sender = event.getPlayer();
            if (command.length == 1) {
                help(sender);
            } else {
                if (command[1].equalsIgnoreCase("give")) {
                    if (command.length != 4) {
                        sender.sendMessage(ChatColor.RED + "[NuxKits] Usage : /NuxKits give [kit] [joueur]");
                    } else if (!NKPermissions.has(sender, "nuxkits.give." + command[2])) {
                        sender.sendMessage(ChatColor.RED + "[NuxKits] Permission denied");
                    } else {
                        giveKit(command[2], command[3], sender);
                    }
                } else if (command[1].equalsIgnoreCase("reload")) {
                    if (command.length != 2) {
                        sender.sendMessage(ChatColor.RED + "[NuxKits] Usage : /NuxKits reload");
                    } else if (!NKPermissions.has(sender, "nuxkits.reload")) {
                        sender.sendMessage(ChatColor.RED + "[NuxKits] Permission denied");
                    } else {
                        loadConfig();
                        sender.sendMessage(ChatColor.GREEN + "[NuxKits] File reloaded");
                    }
                } else if (command[1].equalsIgnoreCase("help")) {
                    if (command.length != 2) {
                        sender.sendMessage(ChatColor.RED + "[NuxKits] Usage : /NuxKits help");
                        // } else if (!NKPermissions.has(sender, "nuxkits.help")) {
                        // sender.sendMessage(ChatColor.RED+"[NuxKits] Permission denied");
                    } else {
                        help(sender);
                    }
                } else if (command[1].equalsIgnoreCase("listKits")) {
                    if (command.length != 2) {
                        sender.sendMessage(ChatColor.RED + "[NuxKits] Usage : /NuxKits listKits");
                        // } else if (!NKPermissions.has(sender, "nuxkits.listkits")) {
                        // sender.sendMessage(ChatColor.RED+"[NuxKits] Permission denied");
                    } else {
                        listKits(sender);
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "[NuxKits] Unknown command");
                }
            }
            event.setCancelled(true);
        }
    }

    private void giveKit(String kit, String reciver, Player sender) {
        Player player = plugin.getServer().getPlayer(reciver);
        if (player != null) {
            ArrayList<String> materialsList = (ArrayList<String>) config.getKeys("kits." + kit);
            if (materialsList != null) {
                for (int i = 0; i < materialsList.size(); i++) {
                    try {
                        plugin.getServer().getPlayer(reciver).getInventory().addItem(new ItemStack(Material.valueOf(materialsList.get(i)), config.getInt("kits." + kit + "." + materialsList.get(i), 0)));
                    } catch (IllegalArgumentException e) {
                        NKLogger.severe("Invalid material : " + materialsList.get(i));
                    }
                }
                sender.sendMessage(ChatColor.GREEN + "[NuxKits] The kit " + kit + " was given to " + reciver);
            } else {
                sender.sendMessage(ChatColor.RED + "[NuxKits] The kit " + kit + " doesn't exist");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "[NuxKits] The player " + reciver + " isn't online");
        }
    }

    private void loadConfig() {
        File configFile = new File("plugins/NuxKits/config.yml");
        if (configFile.exists()) {
            config = new Configuration(configFile);
            config.load();
        } else {
            NKLogger.severe("File not found : plugins/NuxKits/config.yml");
        }
    }

    private void listKits(Player sender) {
        sender.sendMessage(ChatColor.AQUA + "Kits :");
        ArrayList<String> kitsList = (ArrayList<String>) config.getKeys("kits");
        for (int i = 0; i < kitsList.size(); i++) {
            sender.sendMessage(ChatColor.AQUA + "    " + kitsList.get(i));
        }
    }

    private void help(Player sender) {
        sender.sendMessage(ChatColor.AQUA + "Commands :");
        sender.sendMessage(ChatColor.AQUA + "    /NuxKits give [kit] [joueur]");
        sender.sendMessage(ChatColor.AQUA + "    /NuxKits help");
        sender.sendMessage(ChatColor.AQUA + "    /NuxKits reload");
        sender.sendMessage(ChatColor.AQUA + "    /NuxKits listKits");
    }
}
