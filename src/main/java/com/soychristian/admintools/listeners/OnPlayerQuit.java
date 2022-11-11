package com.soychristian.admintools.listeners;

import com.soychristian.admintools.AdminTools;
import com.soychristian.admintools.config.PlayerFileFactory;
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
        PlayerFileFactory.savePlayerData(player);
        PlayerFileFactory.savePlayerDataOnQuit(player);
        PlayerFileFactory.savePlayerInventory(player);

    }
}
