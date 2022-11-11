package com.soychristian.admintools.views;

import com.soychristian.admintools.config.PlayerFileFactory;
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

public class UserStatsGUI implements Listener {
    private static String objetivePlayer;
    public static Inventory buildGui(String playerName){
        String playername = playerName;
        Inventory inventory = Bukkit.createInventory(null, 9 * 3, "User Stats GUI " + playerName);

        String objectiveName = PlayerFileFactory.getPlayerConfig(playername).getString("name");

        ItemStack nameItem = new ItemStack(Material.PAPER);
        ItemMeta nameItemMeta = nameItem.getItemMeta();
        nameItemMeta.setDisplayName("Name: " + objectiveName);
        nameItem.setItemMeta(nameItemMeta);
        inventory.addItem(nameItem);
        return inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        //if (event instanceof InventoryCreativeEvent) return;
        Inventory inventoryEvent = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        // Si el inventario no es tipo chest, retornar null
        //if(inventoryEvent.getType() == InventoryType.CHEST){

            if(event.getView().getTitle().contains("User Options GUI ")){
                objetivePlayer = event.getView().getTitle().split(" ")[3];
                event.setCancelled(true);
                ItemStack currentItem = event.getCurrentItem();
                if (currentItem != null) {
                    if (currentItem.getType().equals(Material.BOOK)) {
                        if (currentItem.getItemMeta().getDisplayName().equals("Stats")) {
                            player.openInventory(UserStatsGUI.buildGui(objetivePlayer));
                        }
                    }
                }
            }
        //}

    }
}
