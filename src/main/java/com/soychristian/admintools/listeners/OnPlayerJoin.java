package com.soychristian.admintools.listeners;

import com.soychristian.admintools.AdminTools;
import com.soychristian.admintools.exceptions.InvalidEncodedInventoryFormat;
import com.soychristian.admintools.config.PlayerFileBuilder;
import com.soychristian.admintools.utils.EncodingDecodingItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


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
        String decodeInventory = PlayerFileBuilder.getEncodedInventory(player.getName());
        if (player.hasPlayedBefore()){
            try {
                player.getInventory().setContents(EncodingDecodingItems.decodeInventory(decodeInventory).getContents());
            } catch (InvalidEncodedInventoryFormat e){
                e.printStackTrace();
            }
        } else {
            PlayerFileBuilder.savePlayerInventory(player);
        }
        player.sendMessage("Your inventory has been restored from PlayersEachFile");
        PlayerFileBuilder.savePlayerData(player);
        PlayerFileBuilder.savePlayerDataOnJoin(player);
        PlayerFileBuilder.changeOnlineStatus(player.getName(), true);
    }
}
