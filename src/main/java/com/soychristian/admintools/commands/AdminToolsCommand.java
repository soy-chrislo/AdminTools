package com.soychristian.admintools.commands;

import com.soychristian.admintools.AdminTools;
import com.soychristian.admintools.config.PlayerFileFactory;
import com.soychristian.admintools.exceptions.InvalidEncodedInventoryFormat;
import com.soychristian.admintools.utils.ChatManager;
import com.soychristian.admintools.utils.EncodingDecodingItems;
import com.soychristian.admintools.utils.SortItems;
import com.soychristian.admintools.utils.VanishManager;
import com.soychristian.admintools.views.AdminToolsGUI;
import com.soychristian.admintools.views.UsersOffGUI;
import com.soychristian.admintools.views.UsersOnGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class AdminToolsCommand implements CommandExecutor {
    AdminTools plugin;
    public AdminToolsCommand(AdminTools plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (args.length == 0){
                // Menu principal del plugin
                AdminToolsGUI.buildGui(player);
                player.sendMessage("Tu ubicacion de configuracion es: " + PlayerFileFactory.getPlayerLocation(player.getName()).toString());
            } else {
                String option = args[0];
                switch (option){
                    case "userson":
                        player.sendMessage("Opening UsersOnGUI");
                        UsersOnGUI.buildGui();
                        player.openInventory(UsersOnGUI.getInventory(0));
                        break;
                    case "usersoff":
                        player.sendMessage("Opening UsersOffGUI");
                        /*UsersOffGUI.buildGui();
                        player.openInventory(UsersOffGUI.getInventory(0));*/
                        player.openInventory(UsersOffGUI.buildGui());
                        break;
                    case "setdisplayname":
                        player.setDisplayName(args[0]);
                        break;
                    case "setnote":
                        if (args.length >= 3){
                            String playername = args[1];

                            String note = "";
                            for (String arg : args){
                                if (arg.equals(args[0]) || arg.equals(args[1])){
                                    continue;
                                }
                                note += arg + " ";
                            }

                            PlayerFileFactory.savePlayerNote(playername, note);
                        } else {
                            player.sendMessage("Usage: /admintools setnote <player> <note>");
                        }
                        break;
                    case "setreport":
                        if (args.length >= 3){
                            String playername = args[1];

                            String report = "";
                            for (String arg : args){
                                if (arg.equals(args[0]) || arg.equals(args[1])){
                                    continue;
                                }
                                report += arg + " ";
                            }
                            PlayerFileFactory.savePlayerReport(playername, report);
                        } else {
                            player.sendMessage("Usage: /admintools setnote <player> <note>");
                        }
                        break;
                    case "setwarn":
                        if (args.length >= 3){
                            String playername = args[1];

                            String warn = "";
                            for (String arg : args){
                                if (arg.equals(args[0]) || arg.equals(args[1])){
                                    continue;
                                }
                                warn += arg + " ";
                            }
                            PlayerFileFactory.savePlayerWarn(playername, warn);
                        } else {
                            player.sendMessage("Usage: /admintools setnote <player> <note>");
                        }
                        break;
                    case "book":
                        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
                        BookMeta bookMeta = (BookMeta) book.getItemMeta();
                        bookMeta.setAuthor("Admin Tools");
                        bookMeta.setTitle("Player Stats");
                        String playerLocation = PlayerFileFactory.getPlayerConfig(player.getName()).get("location").toString()
                                .replace(",", ",\n")
                                .replace("=", "=\n")
                                .replace("x", ChatColor.RED + "x" + ChatColor.RESET)
                                .replace("y", ChatColor.RED + "y" + ChatColor.RESET)
                                .replace("z", ChatColor.RED + "z" + ChatColor.RESET)
                                .replace("name", ChatColor.RED + "world" + ChatColor.RESET)
                                .replace(ChatColor.RED + "y" + ChatColor.RESET + "aw", ChatColor.RED + "yaw" + ChatColor.RESET)
                                .replace("pitch", ChatColor.RED + "pitch" + ChatColor.RESET);

                        bookMeta.addPage("Location: " + playerLocation);
                        book.setItemMeta(bookMeta);
                        player.getInventory().addItem(book);
                        break;
                    case "nosorted":
                        Inventory inventory = Bukkit.createInventory(null, 54, "Sorted Inventory");
                        inventory.setContents(PlayerFileFactory.getOfflinePlayers());
                        player.openInventory(inventory);
                        break;
                    case "sorted":
                        Inventory inventory1 = Bukkit.createInventory(null, 54, "Sorted Inventory");
                        inventory1.setContents(SortItems.sortByName("abc", PlayerFileFactory.getOfflinePlayers()));
                        player.openInventory(inventory1);
                        break;
                    case "clear":
                        ChatManager.clearChat(player);
                        break;
                    case "vanish":
                        String playerName = player.getName();
                        if (VanishManager.isPlayerVanish(playerName)){
                            VanishManager.removeVanish(playerName);
                        } else {
                            VanishManager.applyVanish(playerName);
                        }
                        break;
                    case "reload":
                        plugin.reloadConfig();
                        player.sendMessage("Config reloaded");
                        break;
                    case "inventory":

                        break;
                    case "inventory-restorer":

                        break;
                    case "inventory-cache":
                        // Al adaptarlo en una interfaz, es importante agregarle el titulo "Inventory Cache"
                        String encodedInventoryCache = PlayerFileFactory.getEncodedInventoryCache(player.getName());
                        try {
                            Inventory inventoryCache = Bukkit.createInventory(null, 36, "Inventory Cache");
                            inventoryCache.setContents(EncodingDecodingItems.decodeInventory(encodedInventoryCache).getContents());
                            player.openInventory(inventoryCache);
                        } catch (InvalidEncodedInventoryFormat e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "inventory-size":
                        player.sendMessage("Inventory size: " + player.getInventory().getSize());
                        break;
                    default:
                        player.sendMessage("Opcion no valida.");
                        break;
                }
            }
        } else {
            sender.sendMessage("You must be a player to use this command!");
        }
        return true;
    }
}
