package com.soychristian.admintools.views;

import com.soychristian.admintools.config.PlayerFileBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class UsersOffGUI implements Listener {
    static ArrayList<Inventory> inventories = new ArrayList<Inventory>();
    static int inventoryPage = 0;
    static int currentPage = 0;
    public static Inventory buildGui(){
        int indexInventory = 0;

        FileConfiguration[] players = PlayerFileBuilder.getPlayersConfig();

        inventories.add(Bukkit.createInventory(null, 54, "Offline Players"));

        for (FileConfiguration player : players) {
            boolean isOnline = player.getBoolean("is-online");

            String playerName = player.getString("name");
            ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            ItemMeta playerHeadMeta = playerHead.getItemMeta();
            playerHeadMeta.setDisplayName(playerName);
            playerHead.setItemMeta(playerHeadMeta);

            if (indexInventory < 45) {
                if (!isOnline) {
                    inventories.get(inventoryPage).setItem(indexInventory, playerHead);
                    indexInventory++;
                }
            } else {
                indexInventory = 0;
                inventoryPage++;

                ItemStack nextPage = new ItemStack(Material.ARROW, 1);
                ItemMeta nextPageMeta = nextPage.getItemMeta();
                nextPageMeta.setDisplayName("Next Page");
                nextPage.setItemMeta(nextPageMeta);
                inventories.get(inventoryPage - 1).setItem(53, nextPage);

                inventories.add(Bukkit.createInventory(null, 54, "Offline Players"));
            }
            if (inventoryPage > 0) {
                ItemStack previousPage = new ItemStack(Material.ARROW, 1);
                ItemMeta previousPageMeta = previousPage.getItemMeta();
                previousPageMeta.setDisplayName("Previous Page");
                previousPage.setItemMeta(previousPageMeta);
                inventories.get(inventoryPage).setItem(45, previousPage);
            }
        }
        return inventories.get(0);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getName().equals("Offline Players")) {
            event.setCancelled(true);
            ItemStack currentItem = event.getCurrentItem();
            if (currentItem != null) {
                if (currentItem.getType() == Material.ARROW) {
                    String itemName = currentItem.getItemMeta().getDisplayName();
                    if (itemName.equals("Next Page")) {
                        event.getWhoClicked().openInventory(inventories.get(currentPage + 1));
                        currentPage++;
                    } else if (itemName.equals("Previous Page")) {
                        event.getWhoClicked().openInventory(inventories.get(currentPage - 1));
                        currentPage--;
                    }
                }
            }
        }
    }
}
