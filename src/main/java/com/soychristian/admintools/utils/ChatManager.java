package com.soychristian.admintools.utils;

import com.soychristian.admintools.AdminTools;
import com.soychristian.admintools.config.LogsFileFactory;
import com.soychristian.admintools.config.PlayerFileFactory;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.io.File;

public class ChatManager implements Listener {
    AdminTools plugin;
    public ChatManager(AdminTools plugin){
        this.plugin = plugin;
    }
    public static void clearChat(Player player){
        for(int i = 0; i < 100; i++){
            player.sendMessage(" ");
        }
    }

    public static void clearChat(){
        for(Player player : Bukkit.getOnlinePlayers()){
            for(int i = 0; i < 100; i++){
                player.sendMessage(" ");
            }
        }
    }

    public static void setPlayerMute(String playername, boolean mute){
        File playerFile = PlayerFileFactory.getPlayerFile(playername);
        FileConfiguration playerConfig = PlayerFileFactory.getPlayerConfig(playername);
        playerConfig.set("mute", mute);
        PlayerFileFactory.savePlayerFile(playerFile, playerConfig);
    }

    public static void isPlayerMutet(String playername){
        FileConfiguration playerConfig = PlayerFileFactory.getPlayerConfig(playername);
        playerConfig.getBoolean("mute");
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        boolean isChatMute = plugin.getConfig().getBoolean("chat-mute");
        boolean isPlayerMute = PlayerFileFactory.getPlayerConfig(player.getName()).getBoolean("mute");
        if (isChatMute){
            event.setCancelled(true);
            player.sendMessage("Chat is muted");
        } else if (isPlayerMute){
            event.setCancelled(true);
            player.sendMessage("You are muted");
        }
    }

    @EventHandler
    public void onChatCommand(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (message.startsWith("/")){
            player.sendMessage("has enviado un comando");
            LogsFileFactory.saveRegister("command", message, player.getName());
        }
    }

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();
        player.sendMessage("has enviado un mensaje");
        LogsFileFactory.saveRegister("message", message, player.getName());
    }
}
