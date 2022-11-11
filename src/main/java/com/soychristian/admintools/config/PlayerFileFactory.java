package com.soychristian.admintools.config;

import com.soychristian.admintools.AdminTools;
import com.soychristian.admintools.exceptions.InvalidEncodedInventoryFormat;
import com.soychristian.admintools.utils.EncodingDecodingItems;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
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

public class PlayerFileFactory {
    private static File playerDataFolder;
    private static File playerFile;
    private static FileConfiguration playerConfig;
    private static AdminTools plugin;

    public PlayerFileFactory(AdminTools plugin){
        PlayerFileFactory.plugin = plugin;
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
        if (!(player.hasPlayedBefore())){
            playerConfig.set("mute", false);
            playerConfig.set("vanish", false);
        }

        savePlayerFile(playerFile, playerConfig);
    }

    public static void savePlayerDataOnQuit(Player player){
        savePlayerData(player);
        playerFile = setupPlayerFile(player.getName());
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        String zoneId = plugin.getConfig().getString("timezone");

        playerConfig.set("disconnect-time", ZonedDateTime.now(ZoneId.of(zoneId)).toString());
        //playerConfig.set("location", player.getLocation().toString());
        setPlayerLocation(player.getName(), player.getLocation());
        playerConfig.set("is-online", false);

        savePlayerFile(playerFile, playerConfig);
    }

    public static void savePlayerDataOnJoin(Player player){
        savePlayerData(player);
        playerFile = setupPlayerFile(player.getName());
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        playerConfig.set("connect-time", ZonedDateTime.now(ZoneId.of("America/Bogota")).toString());
        playerConfig.set("is-online", true);

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

    /*public static void savePlayerFile(String playername){
        playerFile = setupPlayerFile(playername);
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        savePlayerFile(playerFile, playerConfig);
    }*/

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
        playerConfig.set("main-armor.helmet", EncodingDecodingItems.encodeItemStack(player.getEquipment().getHelmet()));
        playerConfig.set("main-armor.chestplate", EncodingDecodingItems.encodeItemStack(player.getEquipment().getChestplate()));
        playerConfig.set("main-armor.leggings", EncodingDecodingItems.encodeItemStack(player.getEquipment().getLeggings()));
        playerConfig.set("main-armor.boots", EncodingDecodingItems.encodeItemStack(player.getEquipment().getBoots()));

        savePlayerFile(playerFile, playerConfig);
        // 1.19.2 - Inventory Cache
        if(player.getInventory().getSize() != 36){
            ItemStack[] inventoryContents = player.getInventory().getContents();

            ItemStack offhand = inventoryContents[40];
            Inventory inventoryCache = Bukkit.createInventory(null,  36, "Inventory Cache");
            if (offhand != null){
                if (PlayerFileFactory.getPlayerConfig(player.getName()).isString("cache-inventory")){
                    try {
                        inventoryCache.setContents(EncodingDecodingItems.decodeInventory(PlayerFileFactory.getPlayerConfig(player.getName()).getString("cache-inventory")).getContents());

                    } catch (InvalidEncodedInventoryFormat e) {
                        throw new RuntimeException(e);
                    }
                }
                inventoryCache.addItem(offhand);
                PlayerFileFactory.savePlayerInventoryCache(player, inventoryCache);
            }
        }

    }

    public static void savePlayerInventoryCache(Player player, Inventory inventory){
        playerFile = setupPlayerFile(player.getName());
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        playerConfig.set("cache-inventory", EncodingDecodingItems.encodeInventory(inventory));

        savePlayerFile(playerFile, playerConfig);
    }

    public static String getEncodedInventory(String playerName){
        playerFile = setupPlayerFile(playerName);
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        return playerConfig.getString("main-inventory");
    }

    public static String getEncodedInventoryCache(String playerName){
        playerFile = setupPlayerFile(playerName);
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        return playerConfig.getString("cache-inventory");
    }

    public static String getEncodedArmor(String playerName){
        playerFile = setupPlayerFile(playerName);
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        return playerConfig.getString("main-armor");
    }

    public static String getHelmetEncoded(String playerName){
        playerFile = setupPlayerFile(playerName);
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        return playerConfig.getString("main-armor.helmet");
    }

    public static String getChestplateEncoded(String playerName){
        playerFile = setupPlayerFile(playerName);
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        return playerConfig.getString("main-armor.chestplate");
    }

    public static String getLeggingsEncoded(String playerName){
        playerFile = setupPlayerFile(playerName);
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        return playerConfig.getString("main-armor.leggings");
    }

    public static String getBootsEncoded(String playerName){
        playerFile = setupPlayerFile(playerName);
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        return playerConfig.getString("main-armor.boots");
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

    public static ItemStack[] getOfflinePlayers() {
        File[] playersFiles = setupFolderData().listFiles();
        //ItemStack[] offlinePlayers = new ItemStack[playersFiles.length];
        ItemStack[] offlinePlayers = new ItemStack[54];
        //for (int i = 0; i < playersFiles.length; i++) {
        for (int i = 0; i < 53; i++) {
            playerConfig = YamlConfiguration.loadConfiguration(playersFiles[i]);
            if (!playerConfig.getBoolean("is-online")) {
                offlinePlayers[i] = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                ItemMeta skullMeta = offlinePlayers[i].getItemMeta();
                skullMeta.setDisplayName(playerConfig.getString("name"));
                offlinePlayers[i].setItemMeta(skullMeta);
            }
        }
        return offlinePlayers;
    }

    public static Location getPlayerLocation(String playername){
        Location location = null;
        playerFile = setupPlayerFile(playername);
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        String world = playerConfig.getString("location.world");
        double x = playerConfig.getDouble("location.x");
        double y = playerConfig.getDouble("location.y");
        double z = playerConfig.getDouble("location.z");
        float yaw = (float) playerConfig.getDouble("location.yaw");
        float pitch = (float) playerConfig.getDouble("location.pitch");

        location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);

        return location;
    }

    public static void setPlayerLocation(String playername, Location location){
        playerFile = setupPlayerFile(playername);
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        String world = location.getWorld().getName();

        playerConfig.set("location.x", x);
        playerConfig.set("location.y", y);
        playerConfig.set("location.z", z);
        playerConfig.set("location.yaw", yaw);
        playerConfig.set("location.pitch", pitch);
        playerConfig.set("location.world", world);
        savePlayerFile(playerFile, playerConfig);
    }
}
