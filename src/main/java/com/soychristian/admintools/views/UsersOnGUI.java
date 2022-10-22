package com.soychristian.admintools.views;

import com.soychristian.admintools.events.OnViewsGUIEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class UsersOnGUI {
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
        inventories.add(Bukkit.createInventory(null, 9 * 6, "BanGUI"));
        int inventario = 0;
        int numJugador = 0;
        int indexInventory = 0;
        for (Player player : onlinePlayers){
            ItemStack playerHead = buildHeadItem(player);
            inventories.get(inventario).setItem(indexInventory, playerHead);

            Bukkit.broadcastMessage("Agregando el usuario numero: " + numJugador + " a inventario numero: " + inventario + " en el slot: " + indexInventory);


            if (inventario > 0){
                ItemStack previousPage = new ItemStack(Material.ARROW, 1);
                ItemMeta previousPageMeta = previousPage.getItemMeta();
                previousPageMeta.setDisplayName("Previous Page");
                previousPage.setItemMeta(previousPageMeta);
                inventories.get(inventario).setItem(45, previousPage);
            }

            if (inventories.get(inventario).firstEmpty() >= 45){
                Bukkit.broadcastMessage("Inventario " + inventario + " lleno, se crea uno nuevo");

                inventario++;
                indexInventory = -1;
                inventories.add(Bukkit.createInventory(null, 9 * 6, "BanGUI"));
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
        skullMeta.setOwner(player.getDisplayName()); // 1.8.8
        //skullMeta.setOwnerProfile(playerList.getPlayerProfile());

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Click to ban this player");
        lore.add("Player UUID: " + player.getUniqueId());
        lore.add("Player IP: " + player.getAddress().getAddress().getHostAddress());
        lore.add("Player Location: " + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ());
        lore.add("Player World: " + player.getWorld().getName());
        lore.add("Player Gamemode: " + player.getGameMode().name());
        //lore.add("Player Health: " + playerList.getHealth());
        //lore.add("Player Hunger: " + playerList.getFoodLevel());
        //lore.add("Player XP: " + playerList.getExp());
        //lore.add("Player Level: " + playerList.getLevel());
        //lore.add("Player Ping: " + playerList.getPing());
        lore.add("Player Op: " + player.isOp());
        playerHeadMeta.setLore(lore);
        playerHead.setItemMeta(playerHeadMeta);
        playerHead.setItemMeta(skullMeta);
        return playerHead;
    }
}
