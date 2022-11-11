package com.soychristian.admintools.listeners;

import com.soychristian.admintools.config.PlayerFileFactory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class OnInventoryClose implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        Inventory inventoryEvent = event.getInventory();
        if(event.getView().getTitle().contains("Inventory Cache")){
            PlayerFileFactory.savePlayerInventoryCache(player, inventoryEvent);
        }
    }

    @EventHandler
    public void onInventoryMoveItem(InventoryClickEvent event){
        InventoryView view = event.getView();
        Inventory clickedInventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        if(view.getTitle().contains("Inventory Cache")){
            if(clickedInventory.equals(view.getBottomInventory())){
                if (event.isShiftClick() || event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                    event.setCancelled(true);
                }
            }
            if(clickedInventory.equals(view.getTopInventory())){
                if (event.getCursor() != null) {
                    //event.setCancelled(true);
                    switch (event.getAction()) {
                        case PLACE_ALL:
                        case PLACE_ONE:
                        case PLACE_SOME:
                        case SWAP_WITH_CURSOR:
                            ItemStack item = event.getCursor();
                            event.setCursor(null);
                            player.getInventory().addItem(item);
                            break;
                        case HOTBAR_SWAP:
                            event.setCancelled(true);
                            break;
                        default:
                            break;
                    }
                } else if (event.getAction() == InventoryAction.HOTBAR_SWAP) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
