package com.soychristian.admintools.task;

import com.soychristian.admintools.events.PlayerChangedDisplayNameEvent;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class CheckDisplayNameTask implements Runnable {
    private HashMap<UUID,String> playersDisplayName = new HashMap<UUID,String>();

    @Override
    public void run() {
        //Bukkit.broadcastMessage("Checking DisplayName...");
        Bukkit.getOnlinePlayers().forEach(player -> {
            UUID playerUUID = player.getUniqueId();
            String playerDisplayName = player.getDisplayName();
            if (!playersDisplayName.containsKey(playerUUID)){
                playersDisplayName.put(playerUUID, playerDisplayName);
            } else {
                if (!playersDisplayName.get(playerUUID).equals(playerDisplayName)){
                    String oldDisplayName = playersDisplayName.get(playerUUID);
                    String newDisplayName = playerDisplayName;
                    Bukkit.broadcastMessage(player.getName() + " changed his DisplayName " + oldDisplayName + " to " + newDisplayName);
                    Bukkit.getPluginManager().callEvent(new PlayerChangedDisplayNameEvent(
                            player,
                            oldDisplayName,
                            newDisplayName
                    ));
                    playersDisplayName.put(playerUUID, newDisplayName);
                }
            }
        });
    }
}
