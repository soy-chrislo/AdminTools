package com.soychristian.admintools;

import com.soychristian.admintools.commands.AdminToolsCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdminTools extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("AdminTools has been enabled!");

        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommands(){
        this.getCommand("admintools").setExecutor(new AdminToolsCommand());
    }

    public void registerEvents(){
        PluginManager pm = Bukkit.getPluginManager();
    }
}
