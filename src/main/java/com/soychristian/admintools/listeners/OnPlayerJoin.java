package com.soychristian.admintools.listeners;

import com.soychristian.admintools.AdminTools;
import com.soychristian.admintools.exceptions.InvalidEncodedInventoryFormat;
import com.soychristian.admintools.config.PlayerFileFactory;
import com.soychristian.admintools.utils.EncodingDecodingItems;
import com.soychristian.admintools.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


/*
* TODO: Descerializar el inventorio, y ponerlo en el inventorio del jugador
* */

public class OnPlayerJoin implements Listener {
    AdminTools plugin;
    public OnPlayerJoin(AdminTools plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        //plugin.saveDataPlayer();
        player.getInventory().clear();
        PlayerManager.clearArmor(player);
        String encodedInventory = PlayerFileFactory.getEncodedInventory(player.getName());
        String encodedHelment = PlayerFileFactory.getHelmetEncoded(player.getName());
        String encodedChestplate = PlayerFileFactory.getChestplateEncoded(player.getName());
        String encodedLeggings = PlayerFileFactory.getLeggingsEncoded(player.getName());
        String encodedBoots = PlayerFileFactory.getBootsEncoded(player.getName());
        if (player.hasPlayedBefore()){
            try {
                Inventory decodedInventory = EncodingDecodingItems.decodeInventory(encodedInventory);
                if (decodedInventory != null){
                    player.getInventory().setContents(decodedInventory.getContents());
                }
                ItemStack decodedHelmet = EncodingDecodingItems.decodeItemStack(encodedHelment);
                if (decodedHelmet != null){
                    player.getEquipment().setHelmet(decodedHelmet);
                }
                ItemStack decodedChestplate = EncodingDecodingItems.decodeItemStack(encodedChestplate);
                if (decodedChestplate != null){
                    player.getEquipment().setChestplate(decodedChestplate);
                }
                ItemStack decodedLeggings = EncodingDecodingItems.decodeItemStack(encodedLeggings);
                if (decodedLeggings != null){
                    player.getEquipment().setLeggings(decodedLeggings);
                }
                ItemStack decodedBoots = EncodingDecodingItems.decodeItemStack(encodedBoots);
                if (decodedBoots != null){
                    player.getEquipment().setBoots(decodedBoots);
                }
                /*for(ItemStack item : EncodingDecodingItems.decodeInventory(encodedArmor)){
                    if (item != null){
                        *//*
                        * Al utilizar Inventory#addItem() pasando un inventario como argumento, agregara los items al inventario incluyendo los slots vacios (null)
                        * *//*
                        player.getInventory().addItem(item);
                    }
                }*/
            } catch (InvalidEncodedInventoryFormat e){
                e.printStackTrace();
            }
        } else {
            PlayerFileFactory.savePlayerInventory(player);
        }
        player.sendMessage("Your inventory has been restored from PlayersEachFile");
        PlayerFileFactory.savePlayerData(player);
        PlayerFileFactory.savePlayerDataOnJoin(player);
    }
}
