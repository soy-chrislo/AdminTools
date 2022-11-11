package com.soychristian.admintools.views;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UsersOptionsGUI implements Listener {
    static Inventory inventory;
    public static Inventory buildGui(String playerName){
        inventory = Bukkit.createInventory(null, 9 * 3, "User Options GUI " + playerName);
        ItemStack statsOption = new ItemStack(Material.BOOK, 1);
        ItemMeta statsOptionMeta = statsOption.getItemMeta();
        statsOptionMeta.setDisplayName("Stats");
        statsOption.setItemMeta(statsOptionMeta);
        inventory.setItem(0, statsOption);
        return inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        //if (event instanceof InventoryCreativeEvent) return;
        Inventory inventoryEvent = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        //if(inventoryEvent.getType() == InventoryType.CHEST){
            if(event.getView().getTitle().contains("Offline Players") || event.getView().getTitle().contains("Online Players")){
                event.setCancelled(true);
                ItemStack currentItem = event.getCurrentItem();
                if (currentItem != null) {
                    if (currentItem.getType().equals(Material.SKULL_ITEM)) {
                        String playerName = currentItem.getItemMeta().getDisplayName();
                        player.openInventory(UsersOptionsGUI.buildGui(playerName));
                    }
                }
            }
        //}
    }
}
