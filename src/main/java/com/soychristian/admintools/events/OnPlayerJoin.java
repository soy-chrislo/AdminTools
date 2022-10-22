package com.soychristian.admintools.events;

import com.soychristian.admintools.views.UsersOnGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class OnPlayerJoin implements Listener {
    // TODO: Probar si actualiza el inventario en la misma pagina.


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        ArrayList<Player> jugadoresActivos = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player playerItem : jugadoresActivos){
            if(playerItem.getOpenInventory().getTitle().equals("BanGUI")){
                playerItem.closeInventory();
                UsersOnGUI.buildGui();
                playerItem.openInventory(UsersOnGUI.getInventory(0));
            }
        }
        // Cada vez que ingrese un jugador, a otro jugador que este viendo la interfaz se le actualice MIENTRAS la observa. Sin tener que salir y volver a ejecutar el comando.
    }
}
