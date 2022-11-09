package com.soychristian.admintools.deprecated;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

/*
* La lista de jugadores desconectados ya es funcional, por medio del archivo OfflinePlayersFile.java se accede al UUID y demas propiedades almacenadas de los jugadores desconectados.
* TODO: Agregar lore con informacion del jugador, como fecha de ultima conexion, tiempo jugado, etc.
*  TODO: Probar paginacion (ya esta implementada, solo falta probarla)
*
* */

public class UsersOffGUI {
    // TODO: NOT READY YET
    static OfflinePlayer[] offlinePlayers;
    static ArrayList<Inventory> inventories = new ArrayList<Inventory>();
    static ArrayList<String> playersUUID;

    public static void buildGui(){
        OnViewsGUIEvent.getPage().clear();
        clearCache();
        playersUUID =  new ArrayList<String>(OfflinePlayersFile.get().getConfigurationSection("players").getKeys(false));

        inventories.add(Bukkit.createInventory(null, 9 * 6, "Offline Players"));
        int inventario = 0;
        int numJugador = 0;
        int indexInventory = 0;
        for (String player : playersUUID){
            /*ItemStack playerHead = buildHeadItem(player);
            inventories.get(inventario).setItem(indexInventory, playerHead);*/
            // getPlayer() retornaba null aunque el jugador estuviera conectado, se pasaba el UUID del jugador pero en string, quiza era por eso.
            // TODO: Probar con el UUID del jugador en UUID no en string.
            if(!(Bukkit.getPlayer(OfflinePlayersFile.get().getString("players." + player + ".name")) == null)){
                continue;
            }
            ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1);
            SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();
            String playerName = OfflinePlayersFile.get().getString("players." + player + ".name");
            playerHeadMeta.setDisplayName(playerName);
            playerHead.setItemMeta(playerHeadMeta);
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
                inventories.add(Bukkit.createInventory(null, 9 * 6, "Offline Players"));
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

    public static ItemStack buildHeadItem(OfflinePlayer player) {
        ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemMeta playerHeadMeta = playerHead.getItemMeta();
        //SkullMeta skullMeta = (SkullMeta) playerHeadMeta;
        SkullMeta skullMeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM); // 1.8.8

        playerHeadMeta.setDisplayName(player.getPlayer().getDisplayName());
        skullMeta.setOwner(player.getPlayer().getDisplayName()); // 1.8.8
        //skullMeta.setOwnerProfile(playerList.getPlayerProfile());

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Click to ban this player");
        lore.add("Player UUID: " + player.getUniqueId());
        lore.add("Player IP: " + player.getPlayer().getAddress().getAddress().getHostAddress());
        lore.add("Player Location: " + player.getPlayer().getLocation().getBlockX() + ", " + player.getPlayer().getLocation().getBlockY() + ", " + player.getPlayer().getLocation().getBlockZ());
        lore.add("Player World: " + player.getPlayer().getWorld().getName());
        lore.add("Player Gamemode: " + player.getPlayer().getGameMode().name());
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
