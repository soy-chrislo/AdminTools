package com.soychristian.admintools.commands;

import com.soychristian.admintools.views.UsersOnGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ListUserCommand implements CommandExecutor {
    static Inventory inventory1;
    static Inventory inventory2;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /*if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("Opening BanGUI");

            ArrayList<Player> listOnlinePlayer = new ArrayList<>(player.getServer().getOnlinePlayers());
            inventory1 = Bukkit.createInventory(null, 9 * 6, "BanGUI");
            inventory2 = Bukkit.createInventory(null, 9 * 6, "BanGUI");

            for (Player playerList : listOnlinePlayer) {
                //ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1);
                ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
                ItemMeta playerHeadMeta = playerHead.getItemMeta();
                //SkullMeta skullMeta = (SkullMeta) playerHeadMeta;
                SkullMeta skullMeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM); // 1.8.8

                playerHeadMeta.setDisplayName(playerList.getName());
                skullMeta.setOwner(playerList.getDisplayName()); // 1.8.8
                //skullMeta.setOwnerProfile(playerList.getPlayerProfile());

                ArrayList<String> lore = new ArrayList<>();
                lore.add("Click to ban this player");
                lore.add("Player UUID: " + playerList.getUniqueId());
                lore.add("Player IP: " + playerList.getAddress().getAddress().getHostAddress());
                lore.add("Player Location: " + playerList.getLocation().getBlockX() + ", " + playerList.getLocation().getBlockY() + ", " + playerList.getLocation().getBlockZ());
                lore.add("Player World: " + playerList.getWorld().getName());
                lore.add("Player Gamemode: " + playerList.getGameMode().name());
                //lore.add("Player Health: " + playerList.getHealth());
                //lore.add("Player Hunger: " + playerList.getFoodLevel());
                //lore.add("Player XP: " + playerList.getExp());
                //lore.add("Player Level: " + playerList.getLevel());
                //lore.add("Player Ping: " + playerList.getPing());
                lore.add("Player Op: " + playerList.isOp());
                playerHeadMeta.setLore(lore);
                playerHead.setItemMeta(playerHeadMeta);
                playerHead.setItemMeta(skullMeta);
                // Hasta el slot 44
                if (inventory1.firstEmpty() == 45) {
                    inventory2.addItem(playerHead);
                } else {
                    inventory1.addItem(playerHead);
                }


            }
            ItemStack previousPage = new ItemStack(Material.ARROW, 1);
            ItemMeta previousPageMeta = previousPage.getItemMeta();
            previousPageMeta.setDisplayName("Previous Page");
            previousPage.setItemMeta(previousPageMeta);
            inventory1.setItem(45, previousPage);
            inventory2.setItem(45, previousPage);

            ItemStack newPage = new ItemStack(Material.ARROW, 1);
            ItemMeta newPageMeta = newPage.getItemMeta();
            newPageMeta.setDisplayName("Next Page");
            newPage.setItemMeta(newPageMeta);
            inventory1.setItem(53, newPage);
            inventory2.setItem(53, newPage);
            player.openInventory(inventory1);
        }*/
        if(sender instanceof Player){
            Player player = (Player) sender;
            player.sendMessage("Opening UsersOnGUI");
            UsersOnGUI.buildGui();
            player.openInventory(UsersOnGUI.getInventory(0));
        }

        return true;
    }
    public static Inventory getInventory1() {
        return inventory1;
    }
    public static Inventory getInventory2() {
        return inventory2;
    }
}
