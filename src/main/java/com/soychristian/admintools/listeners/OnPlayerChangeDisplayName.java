package com.soychristian.admintools.listeners;

import com.soychristian.admintools.AdminTools;
import com.soychristian.admintools.events.PlayerChangedDisplayNameEvent;
import com.soychristian.admintools.config.PlayerFileFactory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OnPlayerChangeDisplayName implements Listener {
    private AdminTools plugin;
    public OnPlayerChangeDisplayName(AdminTools plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerChangeDisplayName(PlayerChangedDisplayNameEvent e){
        String playerName = e.getWhoChanged().getName();
        File playerFile = PlayerFileFactory.getPlayerFile(playerName);
        List<String> playerDisplayNames = new ArrayList<>();
        FileConfiguration playerConfig = PlayerFileFactory.getPlayerConfig(playerName);
        if (playerConfig.isList("history-displaynames")){
            playerDisplayNames = playerConfig.getStringList("history-displaynames");
            playerDisplayNames.add(e.getNewDisplayName());
            playerConfig.set("history-displaynames", playerDisplayNames);
        } else {
            playerDisplayNames.add(e.getOldDisplayName());
            playerDisplayNames.add(e.getNewDisplayName());
            playerConfig.set("history-displaynames", playerDisplayNames);
        }
        PlayerFileFactory.savePlayerFile(playerFile, playerConfig);
    }

}
