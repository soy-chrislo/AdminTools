package com.soychristian.admintools.views;

import com.soychristian.admintools.deprecated.OnViewsGUIEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class UsersOnGUI implements Listener {
    /*public void practica(){
        ArrayList<Inventory> inventories = new ArrayList<Inventory>();
        inventories.add(Bukkit.createInventory(null, 9 * 6, "BanGUI"));

    }*/
    static ArrayList<Player> onlinePlayers = new ArrayList<Player>();
    static ArrayList<Inventory> inventories = new ArrayList<Inventory>();


    public static void buildGui() {
        OnViewsGUIEvent.getPage().clear();
        clearCache();
        onlinePlayers = new ArrayList<Player>(Bukkit.getOnlinePlayers());
        inventories.add(Bukkit.createInventory(null, 9 * 6, "Online Players"));
        int inventario = 0;
        int numJugador = 0;
        int indexInventory = 0;
        for (Player player : onlinePlayers){
            ItemStack playerHead = buildHeadItem(player);
            inventories.get(inventario).setItem(indexInventory, playerHead);

            //Bukkit.broadcastMessage("Agregando el usuario numero: " + numJugador + " a inventario numero: " + inventario + " en el slot: " + indexInventory);


            if (inventario > 0){
                ItemStack previousPage = new ItemStack(Material.ARROW, 1);
                ItemMeta previousPageMeta = previousPage.getItemMeta();
                previousPageMeta.setDisplayName("Previous Page");
                previousPage.setItemMeta(previousPageMeta);
                inventories.get(inventario).setItem(45, previousPage);
            }

            if (inventories.get(inventario).firstEmpty() >= 45){
                //Bukkit.broadcastMessage("Inventario " + inventario + " lleno, se crea uno nuevo");

                inventario++;
                indexInventory = -1;
                inventories.add(Bukkit.createInventory(null, 9 * 6, "Online Players"));
                Bukkit.broadcastMessage("New inventory created");
            }

            ItemStack newPage = new ItemStack(Material.ARROW, 1);
            ItemMeta newPageMeta = newPage.getItemMeta();
            newPageMeta.setDisplayName("Next Page");
            newPage.setItemMeta(newPageMeta);
            inventories.get(inventario).setItem(53, newPage);

            numJugador++;
            indexInventory++;
        }
        inventories.get(inventario).setItem(53, new ItemStack(Material.AIR));
    }
    public static Inventory getInventory(int i){
        if (i < 0) return inventories.get(0);
        return inventories.get(i);
    }

    public static void clearCache(){
        inventories.clear();
    }

    public static ItemStack buildHeadItem(Player player) {
        ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemMeta playerHeadMeta = playerHead.getItemMeta();
        //SkullMeta skullMeta = (SkullMeta) playerHeadMeta;
        SkullMeta skullMeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM); // 1.8.8

        playerHeadMeta.setDisplayName(player.getDisplayName());
        skullMeta.setDisplayName(ChatColor.WHITE + player.getDisplayName());
        skullMeta.setOwner(player.getDisplayName()); // 1.8.8
        //skullMeta.setOwnerProfile(playerList.getPlayerProfile());

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Player UUID: " + ChatColor.GOLD + player.getUniqueId());
        lore.add(ChatColor.GRAY + "Player IP: " + ChatColor.GOLD + player.getAddress().getAddress().getHostAddress());
        lore.add(ChatColor.GRAY + "Player Location: " + ChatColor.GOLD + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ());
        lore.add(ChatColor.GRAY + "Player World: " + ChatColor.GOLD + player.getWorld().getName());
        lore.add(ChatColor.GRAY + "Player Gamemode: " + ChatColor.GOLD + player.getGameMode().name());
        lore.add(ChatColor.GRAY + "Player Health: " + ChatColor.GOLD + player.getHealth());
        lore.add(ChatColor.GRAY + "Player Hunger: " + player.getFoodLevel());
        lore.add(ChatColor.GRAY + "Player XP: " + ChatColor.GOLD + player.getExp());
        lore.add(ChatColor.GRAY + "Player Level: " + ChatColor.GOLD + player.getLevel());
        lore.add(ChatColor.GRAY + "Player Ping: " + ChatColor.GOLD + ((CraftPlayer) player).getHandle().ping);
        lore.add(ChatColor.GRAY + "Player Op: " + ChatColor.GOLD + player.isOp());
        playerHeadMeta.setLore(lore);
        skullMeta.setLore(lore);
        playerHead.setItemMeta(playerHeadMeta);
        playerHead.setItemMeta(skullMeta);
        return playerHead;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Inventory inventory = event.getInventory();
        if (inventory.getName().equals("Online Players")){
            event.setCancelled(true);
            ItemStack currentItem = event.getCurrentItem();
            Player player = (Player) event.getWhoClicked();
            if(currentItem.getType() == Material.ARROW){
                if (currentItem.getItemMeta().getDisplayName().equals("Next Page")){
                    player.openInventory(getInventory(inventories.indexOf(inventory) + 1));
                } else if (currentItem.getItemMeta().getDisplayName().equals("Previous Page")){
                    player.openInventory(getInventory(inventories.indexOf(inventory) - 1));
                }
            }
        }
    }
}
