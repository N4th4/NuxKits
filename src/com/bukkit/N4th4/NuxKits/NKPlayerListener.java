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
                        sender.sendMessage(ChatColor.RED + "[NuxKits] Utilisation : /NuxKits give [kit] [joueur]");
                    } else if (!NKPermissions.has(sender, "nuxkits.give." + command[2])) {
                        sender.sendMessage(ChatColor.RED + "[NuxKits] Permission refusée");
                    } else {
                        giveKit(command[2], command[3], sender);
                    }
                } else if (command[1].equalsIgnoreCase("reload")) {
                    if (command.length != 2) {
                        sender.sendMessage(ChatColor.RED + "[NuxKits] Utilisation : /NuxKits reload");
                    } else if (!NKPermissions.has(sender, "nuxkits.reload")) {
                        sender.sendMessage(ChatColor.RED + "[NuxKits] Permission refusée");
                    } else {
                        loadConfig();
                        sender.sendMessage(ChatColor.RED + "[NuxKits] Fichier de conf' rechargé");
                    }
                } else if (command[1].equalsIgnoreCase("help")) {
                    if (command.length != 2) {
                        sender.sendMessage(ChatColor.RED + "[NuxKits] Utilisation : /NuxKits help");
                        // } else if (!NKPermissions.has(sender, "nuxkits.help")) {
                        // sender.sendMessage(ChatColor.RED+"[NuxKits] Permission refusée");
                    } else {
                        help(sender);
                    }
                } else if (command[1].equalsIgnoreCase("listKits")) {
                    if (command.length != 2) {
                        sender.sendMessage(ChatColor.RED + "[NuxKits] Utilisation : /NuxKits listKits");
                        // } else if (!NKPermissions.has(sender, "nuxkits.listkits")) {
                        // sender.sendMessage(ChatColor.RED+"[NuxKits] Permission refusée");
                    } else {
                        listKits(sender);
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "[NuxKits] Commande inconnue");
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
                        NKLogger.severe("Matériel invalide : " + materialsList.get(i));
                    }
                }
            } else {
                sender.sendMessage("[NuxKits] Le kit " + kit + " n'existe pas");
            }
        } else {
            sender.sendMessage("[NuxKits] Le joueur " + reciver + " n'est pas en ligne");
        }
    }

    private void loadConfig() {
        File configFile = new File("plugins/NuxKits/config.yml");
        if (configFile.exists()) {
            config = new Configuration(configFile);
            config.load();
        } else {
            NKLogger.severe("Fichier de configuration non trouvé : plugins/NuxKits/config.yml");
        }
    }

    private void listKits(Player sender) {
        sender.sendMessage(ChatColor.RED + "Kits :");
        ArrayList<String> kitsList = (ArrayList<String>) config.getKeys("kits");
        for (int i = 0; i < kitsList.size(); i++) {
            sender.sendMessage(ChatColor.RED + "    " + kitsList.get(i));
        }
    }

    private void help(Player sender) {
        sender.sendMessage(ChatColor.RED + "Commandes :");
        sender.sendMessage(ChatColor.RED + "    /NuxKits give [kit] [joueur]");
        sender.sendMessage(ChatColor.RED + "    /NuxKits help");
        sender.sendMessage(ChatColor.RED + "    /NuxKits reload");
        sender.sendMessage(ChatColor.RED + "    /NuxKits listKits");
    }
}