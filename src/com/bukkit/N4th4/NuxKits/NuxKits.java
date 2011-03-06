package com.bukkit.N4th4.NuxKits;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class NuxKits extends JavaPlugin {
    private Configuration config;
    private final HashMap<Player, Boolean> debugees;

    public NuxKits() {
        NKLogger.initialize();
        debugees = new HashMap<Player, Boolean>();
    }

    public void onEnable() {
        NKPermissions.initialize(this);
        loadConfig();

        PluginDescriptionFile pdfFile = this.getDescription();
        NKLogger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
    }

    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String commandName = command.getName();
        if (sender instanceof Player) {
            if (commandName.equalsIgnoreCase("NuxKits")) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    help(player);
                } else {
                    if (args[0].equalsIgnoreCase("give")) {
                        if (args.length != 2 && args.length != 3) {
                            sender.sendMessage(ChatColor.RED + "[NuxKits] Usage : /NuxKits give [kit] <joueur>");
                        } else if (!NKPermissions.has(player, "nuxkits.give." + args[1])) {
                            sender.sendMessage(ChatColor.RED + "[NuxKits] Permission denied");
                        } else {
                            if (args.length == 2)
                                giveKit(args[1], player.getName(), player);
                            else if (args.length == 3)
                                giveKit(args[1], args[2], player);
                        }
                    } else if (args[0].equalsIgnoreCase("reload")) {
                        if (args.length != 1) {
                            sender.sendMessage(ChatColor.RED + "[NuxKits] Usage : /NuxKits reload");
                        } else if (!NKPermissions.has(player, "nuxkits.reload")) {
                            sender.sendMessage(ChatColor.RED + "[NuxKits] Permission denied");
                        } else {
                            loadConfig();
                            sender.sendMessage(ChatColor.GREEN + "[NuxKits] File reloaded");
                        }
                    } else if (args[0].equalsIgnoreCase("help")) {
                        if (args.length != 1) {
                            sender.sendMessage(ChatColor.RED + "[NuxKits] Usage : /NuxKits help");
                            // } else if (!NKPermissions.has(player, "nuxkits.help")) {
                            // sender.sendMessage(ChatColor.RED+"[NuxKits] Permission denied");
                        } else {
                            help(player);
                        }
                    } else if (args[0].equalsIgnoreCase("listKits")) {
                        if (args.length != 1) {
                            sender.sendMessage(ChatColor.RED + "[NuxKits] Usage : /NuxKits list");
                            // } else if (!NKPermissions.has(player, "nuxkits.list")) {
                            // sender.sendMessage(ChatColor.RED+"[NuxKits] Permission denied");
                        } else {
                            listKits(player);
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "[NuxKits] Unknown command. Type /NuxKits help");
                    }
                }
            }
            return true;
        } else {
            sender.sendMessage("[NuxKits] Only commands in chat are supported");
            return true;
        }
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

    private void giveKit(String kit, String receiver, Player sender) {
        Player player = getServer().getPlayer(receiver);
        if (player != null) {
            ArrayList<String> materialsList = (ArrayList<String>) config.getKeys("kits." + kit);
            if (materialsList != null) {
                for (int i = 0; i < materialsList.size(); i++) {
                    try {
                        getServer().getPlayer(receiver).getInventory().addItem(new ItemStack(Material.valueOf(materialsList.get(i)), config.getInt("kits." + kit + "." + materialsList.get(i), 0)));
                    } catch (IllegalArgumentException e) {
                        NKLogger.severe("Invalid material : " + materialsList.get(i));
                    }
                }
                if (sender.getName().equals(receiver)) {
                    sender.sendMessage(ChatColor.GREEN + "[NuxKits] You've given yourself the kit \"" + kit + "\"");
                } else {
                    sender.sendMessage(ChatColor.GREEN + "[NuxKits] The kit \"" + kit + "\" was given to " + receiver);
                    player.sendMessage(ChatColor.GREEN + "[NuxKits] " + sender.getName() + " has given you the kit \"" + kit + "\"");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "[NuxKits] The kit " + kit + " doesn't exist");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "[NuxKits] Unknown player \"" + receiver + "\"");
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

    private void help(Player sender) {
        sender.sendMessage(ChatColor.AQUA + "Commands :");
        sender.sendMessage(ChatColor.AQUA + "    /NuxKits give [kit] <joueur>");
        sender.sendMessage(ChatColor.AQUA + "    /NuxKits help");
        sender.sendMessage(ChatColor.AQUA + "    /NuxKits reload");
        sender.sendMessage(ChatColor.AQUA + "    /NuxKits list");
    }

    private void listKits(Player sender) {
        sender.sendMessage(ChatColor.AQUA + "Kits :");
        ArrayList<String> kitsList = (ArrayList<String>) config.getKeys("kits");
        for (int i = 0; i < kitsList.size(); i++) {
            sender.sendMessage(ChatColor.AQUA + "    " + kitsList.get(i));
        }
    }
}