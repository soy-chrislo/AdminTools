package com.soychristian.admintools.views;

import com.mojang.authlib.yggdrasil.response.User;
import com.soychristian.admintools.utils.ItemFactory;
import com.soychristian.admintools.utils.SkullBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class AdminToolsGUI implements Listener {
    static Inventory mainInventory = Bukkit.createInventory(null, 9 * 6, "Admin Tools");
    static ItemStack onlinePlayersItem, offlinePlayersItem, playerNotesItem, playerWarningsItem, playerReportsItem, adminModeItem, chatManager, miningModeItem, inventoryHistoryItem, logsItem;
    public static void buildGui(Player player){
        onlinePlayersItem = ItemFactory.itemBuilder("Online Players", Material.BOOK, 1, true, null);
        mainInventory.setItem(0, onlinePlayersItem);
        offlinePlayersItem = ItemFactory.itemBuilder("Offline Players", Material.BOOK, 1, true, null);
        mainInventory.setItem(1, offlinePlayersItem);
        playerNotesItem = ItemFactory.itemBuilder("Player Notes", Material.BOOK, 1, true, null);
        mainInventory.setItem(2, playerNotesItem);
        playerWarningsItem = ItemFactory.itemBuilder("Player Warnings", Material.BOOK, 1, true, null);
        mainInventory.setItem(3, playerWarningsItem);
        playerReportsItem = ItemFactory.itemBuilder("Player Reports", Material.BOOK, 1, true, null);
        mainInventory.setItem(4, playerReportsItem);
        /* TODO: Admin Mode WIP */
        adminModeItem = ItemFactory.itemBuilder("Admin Mode", Material.BOOK, 1, true, null);
        mainInventory.setItem(5, adminModeItem);
        /* Limpiar chat para uno/todos, mutear a alguien. */
        chatManager = ItemFactory.itemBuilder("Chat Manager", Material.BOOK, 1, true, null);
        mainInventory.setItem(6, chatManager);
        /* TODO: Mining Mode WIP */
        miningModeItem = ItemFactory.itemBuilder("Mining Mode", Material.BOOK, 1, true, null);
        mainInventory.setItem(7, miningModeItem);
        /* TODO: Inventory History WIP - Angel Chest Plus Clone https://www.spigotmc.org/resources/%E2%AD%90-angelchest-plus-%E2%AD%90-death-chests-graveyards.88214/*/
        inventoryHistoryItem = ItemFactory.itemBuilder("Inventory History", Material.BOOK, 1, true, null);
        mainInventory.setItem(8, inventoryHistoryItem);
        logsItem = ItemFactory.itemBuilder("Logs", Material.BOOK, 1, true, null);
        mainInventory.setItem(9, logsItem);


        player.openInventory(mainInventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Inventory inventoryEvent = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        if (inventoryEvent.equals(mainInventory)){
            event.setCancelled(true);
            ItemStack currentItem = event.getCurrentItem();
            if (currentItem == null || currentItem.getType() == Material.AIR) return;
            String displayName = ChatColor.stripColor(currentItem.getItemMeta().getDisplayName());
            if(currentItem.equals(onlinePlayersItem)){
                player.sendMessage("Clicked onlinePlayersItem from instance");
                UsersOnGUI.buildGui();
                player.openInventory(UsersOnGUI.getInventory(0));
            } else if (currentItem.equals(offlinePlayersItem)){
                player.sendMessage("Clicked offlinePlayersItem from instance");
                player.openInventory(UsersOffGUI.buildGui());
            } else if (currentItem.equals(playerNotesItem)){
                player.sendMessage("Clicked playerNotesItem from instance");
            } else if (currentItem.equals(playerWarningsItem)){
                player.sendMessage("Clicked playerWarningsItem from instance");
            } else if (currentItem.equals(playerReportsItem)){
                player.sendMessage("Clicked playerReportsItem from instance");
            } else if (currentItem.equals(adminModeItem)){
                player.sendMessage("Clicked adminModeItem from instance");
            } else if (currentItem.equals(chatManager)){
                player.sendMessage("Clicked chatManager from instance");
            } else if (currentItem.equals(miningModeItem)){
                player.sendMessage("Clicked miningModeItem from instance");
            } else if (currentItem.equals(inventoryHistoryItem)){
                player.sendMessage("Clicked inventoryHistoryItem from instance");
            } else if (currentItem.equals(logsItem)){
                player.sendMessage("Clicked logsItem from instance");
            }
        }
    }
}
