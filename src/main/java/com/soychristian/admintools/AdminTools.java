package com.soychristian.admintools;

import com.soychristian.admintools.commands.AdminToolsCommand;
import com.soychristian.admintools.commands.ListUserOnlineCommand;
import com.soychristian.admintools.commands.ListUserOfflineCommand;
import com.soychristian.admintools.events.OnPlayerJoin;
import com.soychristian.admintools.events.OnPlayerLeft;
import com.soychristian.admintools.events.OnViewsGUIEvent;
import com.soychristian.admintools.files.OfflinePlayersFile;
import mc.obliviate.inventory.InventoryAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdminTools extends JavaPlugin implements Listener {
    private final InventoryAPI inventoryAPI = new InventoryAPI(this);
    //Pending...
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("AdminTools has been enabled!");

        inventoryAPI.init();
        registerEvents();
        registerCommands();


        getConfig().options().copyDefaults();
        saveDefaultConfig();
        OfflinePlayersFile.setup();
        OfflinePlayersFile.get().options().copyDefaults(true);
        OfflinePlayersFile.save();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommands(){
        this.getCommand("admintools").setExecutor(new AdminToolsCommand());
        this.getCommand("listuseronline").setExecutor(new ListUserOnlineCommand());
        this.getCommand("listuseroffline").setExecutor(new ListUserOfflineCommand());
    }

    public void registerEvents(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(this, this);
        pm.registerEvents(new OnViewsGUIEvent(), this);
        pm.registerEvents(new OnPlayerLeft(), this);
        pm.registerEvents(new OnPlayerJoin(), this);
    }

    @Override
    public void reloadConfig(){
        super.reloadConfig();
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
    }


}
