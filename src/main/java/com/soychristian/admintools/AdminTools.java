package com.soychristian.admintools;

import com.soychristian.admintools.commands.AdminToolsCommand;
import com.soychristian.admintools.config.LogsFileFactory;
import com.soychristian.admintools.config.PlayerFileFactory;
import com.soychristian.admintools.listeners.*;
import com.soychristian.admintools.task.CheckDisplayNameTask;
import com.soychristian.admintools.utils.ChatManager;
import com.soychristian.admintools.utils.VanishManager;
import com.soychristian.admintools.views.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


/*
* Ya la parte de guardar el inventario y givearlo al jugador funciona. (encode y decode)
* TODO: Faltaría adaptar la interfaz para ver el inventario del jugador.
* TODO: Al igual que se hizo con el inventario, cambiar el lugar de desconexion de un jugador. Mas adelante, un historial de conexiones y desconexiones, seria necesario crear otro folder, tipo 'connections', y subcarpetas con los nombres de los jugadores, y dentro de cada carpeta, los archivos 'connections' y 'disconnections'. MUCHO MAS ADELANTE, carpeta 'player-history', y dentro, subcarpetas de cada jugador, dentro de cada carpeta de jugador, una carpeta para cada tipo de historial, carpeta de 'connections' (dentro de esta 'connections' y 'disconnections'), carpeta de 'inventories', carpeta de 'chat', carpeta de 'commands', etc. (Dependiendo del tipo del historial, la distribucion de ficheros y archivos puede variar).
* TODO: Establecer en configuracion el periodo de tiempo que revisa el displayname de los jugadores. (en busca de cambios)
* TODO: Establecer en configuracion la zona horaria deseada para la fecha y hora. (actualmente para hora de conexion y desconexion)
* TODO: En GuiOffTest.java, para saber si un jugador esta conectado o no (para agregar a los conectados) solo basta con tener un listener y cambiar la propiedad de conectado a true o false dependiendo si se disparan los eventos de PlayerJoinEvent o PlayerQuitEvent. ✅
*    - Falta listar en GuiOffTest.java los jugadores desconectados segun esta propiedad. ✅
* TODO: Estamos en UserStatsGUI.java, ya se abre el menu de stats y en el titulo el nombre del jugaodr, solo queda agregar cada item con el nombre de la propiedad y el lore con el valor de esta. Puede ser un papel.
* TODO: Clase para filtrar/sortear los elementos de un inventario, en este caso, sortear jugadores conectados y desconectados segun el valor de sus propiedades. NullPointerException linea 23 SortItems.java. Esta clase y los comandos sorted y nosorted de AdminToolsCommand.java, estan en proceso de desarrollo.
* TODO: El desarrollo de la GUI principal, AdminToolsGUI.java, esta en proceso.
* TODO: Registro de comandos y mensajes en proceso con LogsFileFactory.java y ChatManager.java
*
*
* Inventory Cache: 1.8.8 ✅ | 1.19.2 ✅
* */

public final class AdminTools extends JavaPlugin implements Listener {
    private static String pluginPrefix = "[AdminTools] ";
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("AdminTools has been enabled!");
        new PlayerFileFactory(this);
        new LogsFileFactory(this);

        registerEvents();
        registerCommands();
        registerTasks();

        /*getConfig().set("chat-mute", false);
        getConfig().set("timezone", "America/Bogota");*/
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        PlayerFileFactory.setupFolderData();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommands(){
        this.getCommand("admintools").setExecutor(new AdminToolsCommand(this));
    }

    public void registerEvents(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(this, this);
        /* Events Listener */
        pm.registerEvents(new OnPlayerQuit(this), this);
        pm.registerEvents(new OnPlayerJoin(this), this);
        pm.registerEvents(new OnPlayerChangeDisplayName(this), this);
        pm.registerEvents(new OnInventoryClose(), this);
        /* Users Interfaces Listener */
        pm.registerEvents(new UsersOffGUI(), this);
        pm.registerEvents(new UsersOnGUI(), this);
        pm.registerEvents(new UsersOptionsGUI(), this);
        pm.registerEvents(new UserStatsGUI(), this);
        pm.registerEvents(new AdminToolsGUI(), this);
        /* Utilities */
        pm.registerEvents(new ChatManager(this), this);
        pm.registerEvents(new VanishManager(), this);
    }

    public void registerTasks(){
        getServer().getScheduler().runTaskTimer(this, new CheckDisplayNameTask(), 0, 20 * 20);
    }

    @Override
    public void reloadConfig(){
        super.reloadConfig();
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public static String getPluginPrefix(){
        return pluginPrefix;
    }

}
