package com.soychristian.admintools.events;

import com.soychristian.admintools.files.OfflinePlayersFile;
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
        for(Player jugador : jugadoresActivos){
            String nombreJugador = jugador.getDisplayName();
            String uuidJugador = jugador.getUniqueId().toString();
            String ipJugador = jugador.getAddress().getAddress().toString();
            String locateJugador = jugador.getLocation().toString();
            String worldJugador = jugador.getWorld().getName();
            String gamemodeJugador = jugador.getGameMode().toString();
            String opJugador = String.valueOf(jugador.isOp());
            String bedSpawnLocationJugador, lastDamageCauseJugador;
            if (jugador.getBedSpawnLocation() != null){
                bedSpawnLocationJugador = jugador.getBedSpawnLocation().toString();
            } else {
                bedSpawnLocationJugador = "null";
            }
            if (jugador.getLastDamageCause() != null){
                lastDamageCauseJugador = jugador.getLastDamageCause().toString();
            } else {
                lastDamageCauseJugador = "null";
            }
            String healthJugador = String.valueOf(jugador.getHealth());
            String foodJugador = String.valueOf(jugador.getFoodLevel());
            String expJugador = String.valueOf(jugador.getExp());
            String levelJugador = String.valueOf(jugador.getLevel());


            OfflinePlayersFile.get().set("players." + uuidJugador + ".name", nombreJugador);
            OfflinePlayersFile.get().set("players." + uuidJugador + ".ip", ipJugador);
            OfflinePlayersFile.get().set("players." + uuidJugador + ".locate", locateJugador);
            OfflinePlayersFile.get().set("players." + uuidJugador + ".world", worldJugador);
            OfflinePlayersFile.get().set("players." + uuidJugador + ".gamemode", gamemodeJugador);
            OfflinePlayersFile.get().set("players." + uuidJugador + ".op", opJugador);
            OfflinePlayersFile.get().set("players." + uuidJugador + ".bedSpawnLocation", bedSpawnLocationJugador);
            OfflinePlayersFile.get().set("players." + uuidJugador + ".lastDamageCause", lastDamageCauseJugador);
            OfflinePlayersFile.get().set("players." + uuidJugador + ".health", healthJugador);
            OfflinePlayersFile.get().set("players." + uuidJugador + ".food", foodJugador);
            OfflinePlayersFile.get().set("players." + uuidJugador + ".exp", expJugador);
            OfflinePlayersFile.get().set("players." + uuidJugador + ".level", levelJugador);


            OfflinePlayersFile.save();
            OfflinePlayersFile.reload();
        }
        // Cada vez que ingrese un jugador, a otro jugador que este viendo la interfaz se le actualice MIENTRAS la observa. Sin tener que salir y volver a ejecutar el comando.
    }
}
