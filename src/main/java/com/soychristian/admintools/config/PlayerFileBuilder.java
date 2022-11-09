package com.soychristian.admintools.config;

import com.soychristian.admintools.AdminTools;
import com.soychristian.admintools.utils.EncodingDecodingItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
/*
* TODO: File to save history names of players, changed with getName().
* */

public class PlayerFileBuilder {
    private static File playerDataFolder;
    private static File playerFile;
    private static FileConfiguration playerConfig;
    private static AdminTools plugin;

    public PlayerFileBuilder(AdminTools plugin){
        PlayerFileBuilder.plugin = plugin;
    }

    public static void savePlayerData(Player player){
        playerFile = setupPlayerFile(player.getName());
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        playerConfig.set("name", player.getName());
        playerConfig.set("display-name", player.getDisplayName());
        playerConfig.set("uuid", player.getUniqueId().toString());
        playerConfig.set("ip", player.getAddress().getAddress().getHostAddress());
        //playerConfig.set("first-join", player.getFirstPlayed());
        //playerConfig.set("last-join", player.getLastPlayed());
        playerConfig.set("is-op", player.isOp());
        playerConfig.set("is-allow-fly", player.getAllowFlight());
        //playerConfig.set("bed-spawn-location", player.getBedSpawnLocation());
        playerConfig.set("exp", player.getExp());
        playerConfig.set("level", player.getLevel());
        playerConfig.set("food-level", player.getFoodLevel());
        playerConfig.set("health", player.getHealth());
        playerConfig.set("gamemode", player.getGameMode().toString());

        savePlayerFile(playerFile, playerConfig);
    }

    public static void savePlayerDataOnQuit(Player player){
        savePlayerData(player);
        playerFile = setupPlayerFile(player.getName());
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        playerConfig.set("disconnect-time", ZonedDateTime.now(ZoneId.of("America/Bogota")).toString());
        playerConfig.set("location", player.getLocation().toString());

        savePlayerFile(playerFile, playerConfig);
    }

    public static void savePlayerDataOnJoin(Player player){
        savePlayerData(player);
        playerFile = setupPlayerFile(player.getName());
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        playerConfig.set("connect-time", ZonedDateTime.now(ZoneId.of("America/Bogota")).toString());

        savePlayerFile(playerFile, playerConfig);
    }

    public static File setupFolderData(){
        playerDataFolder = new File(plugin.getDataFolder().getPath() + File.separator + "playerData");
        if (!playerDataFolder.exists()) {
            boolean result = playerDataFolder.mkdir();
            Bukkit.getLogger().info(result ? "Player data folder created" : "Player data folder not created, already exists");
        }
        return playerDataFolder;
    }

    public static File setupPlayerFile(String playerName){
        File playerFile = new File(setupFolderData(), playerName + ".yml");
        if (!playerFile.exists()){
            try {
                boolean result = playerFile.createNewFile();
                Bukkit.getLogger().info(result ? "File created" : "File not created, already exists");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return playerFile;
    }

    public static void savePlayerFile(File playerFile, FileConfiguration playerConfig){
        try {
            playerConfig.save(playerFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Player getPlayerByName(String playerName){
        playerFile = setupPlayerFile(playerName);
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        return Bukkit.getPlayer(playerConfig.getString("uuid"));
    }

    public static FileConfiguration getPlayerConfig(String playerName){
        playerFile = setupPlayerFile(playerName);
        return YamlConfiguration.loadConfiguration(playerFile);
    }

    public static File getPlayerFile(String playerName){
        return setupPlayerFile(playerName);
    }

    // TODO: Nos quedamos en la parte de salvar el inventario del jugador, vamos a crear el decoder y encoder para el inventario ✅

    public static void savePlayerInventory(Player player){
        playerFile = setupPlayerFile(player.getName());
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        playerConfig.set("main-inventory", EncodingDecodingItems.encodeInventory(player.getInventory()));
        //playerConfig.set("armor", player.getInventory().getArmorContents());

        savePlayerFile(playerFile, playerConfig);
    }

    public static String getEncodedInventory(String playerName){
        playerFile = setupPlayerFile(playerName);
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        return playerConfig.getString("main-inventory");
    }

    public static void savePlayerNote(String playerName, String note){
        playerFile = setupPlayerFile(playerName);
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        if(playerConfig.isList("notes")){
            List<String> notes = playerConfig.getStringList("notes");
            notes.add(note);
            playerConfig.set("notes", notes);
        } else {
            List<String> notes = new ArrayList<>();
            notes.add(note);
            playerConfig.set("notes", notes);
        }
        savePlayerFile(playerFile, playerConfig);
    }

    public static void savePlayerReport(String playerName, String report){
        playerFile = setupPlayerFile(playerName);
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        if(playerConfig.isList("reports")){
            List<String> reports = playerConfig.getStringList("reports");
            reports.add(report);
            playerConfig.set("reports", reports);
        } else {
            List<String> reports = new ArrayList<>();
            reports.add(report);
            playerConfig.set("reports", reports);
        }
        savePlayerFile(playerFile, playerConfig);
    }

    public static void savePlayerWarn(String playerName, String warn){
        playerFile = setupPlayerFile(playerName);
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        if(playerConfig.isList("warns")){
            List<String> warns = playerConfig.getStringList("warns");
            warns.add(warn);
            playerConfig.set("warns", warns);
        } else {
            List<String> warns = new ArrayList<>();
            warns.add(warn);
            playerConfig.set("warns", warns);
        }
        savePlayerFile(playerFile, playerConfig);
    }

    public static FileConfiguration[] getPlayersConfig(){
        File[] playersFiles = setupFolderData().listFiles();
        FileConfiguration[] playersConfig = new FileConfiguration[playersFiles.length];
        for (int i = 0; i < playersFiles.length; i++) {
            playersConfig[i] = YamlConfiguration.loadConfiguration(playersFiles[i]);
        }
        return playersConfig;
    }

    public static void changeOnlineStatus(String playerName, boolean status){
        playerFile = setupPlayerFile(playerName);
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        playerConfig.set("is-online", status);
        savePlayerFile(playerFile, playerConfig);
    }

    public static ItemStack[] getOfflinePlayers(){
        File[] playersFiles = setupFolderData().listFiles();
        //ItemStack[] offlinePlayers = new ItemStack[playersFiles.length];
        ItemStack[] offlinePlayers = new ItemStack[54];
        //for (int i = 0; i < playersFiles.length; i++) {
        for (int i = 0; i < 53; i++) {
            playerConfig = YamlConfiguration.loadConfiguration(playersFiles[i]);
            if(!playerConfig.getBoolean("is-online")){
                offlinePlayers[i] = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                ItemMeta skullMeta = offlinePlayers[i].getItemMeta();
                skullMeta.setDisplayName(playerConfig.getString("name"));
                offlinePlayers[i].setItemMeta(skullMeta);
            }
        }
        return offlinePlayers;
    }
}
