package com.soychristian.admintools.utils;

import com.soychristian.admintools.config.PlayerFileFactory;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
* TODO:  Falta configurar el permiso para que entre los admin puedan verse.
* TODO: Falta probar con otra cuenta si el vanish funciona para todos los casos, cuando el usuario esta conectado, cuando el usuario se conecta, el mensaje de conexiones y desconexion de un jugador vanish.
* */

public class VanishManager implements Listener {
    public static void setPlayerVanish(String playername, boolean vanish){
        File playerFile = PlayerFileFactory.getPlayerFile(playername);
        FileConfiguration playerConfig = PlayerFileFactory.getPlayerConfig(playername);
        playerConfig.set("vanish", vanish);
        PlayerFileFactory.savePlayerFile(playerFile, playerConfig);
    }

    public static boolean isPlayerVanish(String playername){
        FileConfiguration playerConfig = PlayerFileFactory.getPlayerConfig(playername);
        return playerConfig.getBoolean("vanish");
    }

    public static void applyVanish(String playername){
        setPlayerVanish(playername, true);
        for (Player player : Bukkit.getOnlinePlayers()){
            player.hidePlayer(Bukkit.getPlayer(playername));
        }
    }

    public static void removeVanish(String playername){
        setPlayerVanish(playername, false);
        for (Player player : Bukkit.getOnlinePlayers()){
            player.showPlayer(Bukkit.getPlayer(playername));
        }
    }

    @EventHandler
    public void onJoinOthers(PlayerJoinEvent event){
        Player player = event.getPlayer();
        List<String> vanishPlayers = new ArrayList<String>();
        FileConfiguration[] playerConfigs = PlayerFileFactory.getPlayersConfig();
        for (FileConfiguration playerConfig : playerConfigs){
            if (playerConfig.getBoolean("vanish") && playerConfig.getBoolean("is-online")){
                vanishPlayers.add(playerConfig.getString("name"));
            }
        }
        for (String vanishPlayer : vanishPlayers){
            Player vanishPlayerObject = Bukkit.getPlayer(vanishPlayer);
            if (vanishPlayerObject == null){
                continue;
            }
            player.hidePlayer(vanishPlayerObject);
        }
    }

    @EventHandler
    public void onJoinVanishedPlayer(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (isPlayerVanish(player.getName())){
            event.setJoinMessage(null);
            for (Player otherPlayer : Bukkit.getOnlinePlayers()){
                otherPlayer.hidePlayer(player);
            }
        }
    }

    @EventHandler
    public void onQuitVanishedPlayer(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (isPlayerVanish(player.getName())){
            event.setQuitMessage(null);
            for (Player otherPlayer : Bukkit.getOnlinePlayers()){
                otherPlayer.showPlayer(player);
            }
        }
    }
}
