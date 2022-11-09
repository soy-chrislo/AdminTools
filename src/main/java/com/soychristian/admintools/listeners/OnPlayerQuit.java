package com.soychristian.admintools.listeners;

import com.soychristian.admintools.AdminTools;
import com.soychristian.admintools.config.PlayerFileBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerQuit implements Listener {
    AdminTools plugin;
    public OnPlayerQuit(AdminTools plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event){
        Player player = event.getPlayer();
        //plugin.saveDataPlayer();
        PlayerFileBuilder.savePlayerData(player);
        PlayerFileBuilder.savePlayerDataOnQuit(player);
        PlayerFileBuilder.savePlayerInventory(player);
        PlayerFileBuilder.changeOnlineStatus(player.getName(), false);

    }
}
