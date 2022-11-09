package com.soychristian.admintools.deprecated;

import com.soychristian.admintools.views.UsersOnGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;

public class OnViewsGUIEvent implements Listener {
    static HashMap<Player, Integer> page = new HashMap<>();
    @EventHandler
    public void onViewGUIEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(!(page.containsKey(player))){
            page.put(player, 0);
        }
        if (event.getView().getTitle().equals("Online Players")){
            event.setCancelled(true);
            if (event.getCurrentItem() == null){
                return;
            }
            if(event.getCurrentItem().getType() == Material.ARROW){
                player.closeInventory();
                if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Previous Page")){
                    page.put(player, page.get(player) - 1);
                    player.openInventory(UsersOnGUI.getInventory(page.get(player)));
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Next Page")){
                    page.put(player, page.get(player) + 1);
                    player.openInventory(UsersOnGUI.getInventory(page.get(player)));
                }
            }
        }
    }
    public static HashMap<Player, Integer> getPage() {
        return page;
    }
}
