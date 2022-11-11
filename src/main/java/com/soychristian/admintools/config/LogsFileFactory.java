package com.soychristian.admintools.config;

import com.soychristian.admintools.AdminTools;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*
* TODO: Establecer formato de fecha y hora junto a el nombre de quien ejecuto el comando/mensaje.
* */

public class LogsFileFactory {
    private static File logsFolder;
    private static File logFile;
    private static FileConfiguration logConfig;
    private static AdminTools plugin;

    public LogsFileFactory(AdminTools plugin){
        LogsFileFactory.plugin = plugin;
    }

    public static File setupLogsFolder(){
        logsFolder = new File(plugin.getDataFolder(), "logs");
        if (!logsFolder.exists()){
            boolean result = logsFolder.mkdir();
        }
        return logsFolder;
    }

    public static File setupCurrentDayFolder(){
        //String currentDay = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE); // 20210101
        String currentDay = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        File currentDayFolder = new File(setupLogsFolder(), currentDay);
        if (!currentDayFolder.exists()){
            boolean result = currentDayFolder.mkdir();
        }
        return currentDayFolder;
    }

    public static File setupCommandsFile(){
        logFile = new File(setupCurrentDayFolder(), "commands.yml");
        if (!logFile.exists()){
            try {
                boolean result = logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logFile;
    }

    public static File setupMessagesFile(){
        logFile = new File(setupCurrentDayFolder(), "messages.yml");
        if (!logFile.exists()){
            try {
                boolean result = logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logFile;
    }

    public static FileConfiguration getCommandsConfig(){
        logConfig = YamlConfiguration.loadConfiguration(setupCommandsFile());
        return logConfig;
    }

    public static FileConfiguration getMessagesConfig(){
        logConfig = YamlConfiguration.loadConfiguration(setupMessagesFile());
        return logConfig;
    }

    public static void saveLogFile(File logFile, FileConfiguration logConfig){
        try {
            logConfig.save(logFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveRegister(String registerType, String content, String playername){
        String timezone = plugin.getConfig().getString("timezone");
        String pluginPrefix = AdminTools.getPluginPrefix();
        if(!(ZoneId.getAvailableZoneIds().contains(timezone))){
            Bukkit.getLogger().info(pluginPrefix + "Invalid timezone {%a}, please check your config.yml".replace("%a", timezone));
            return;
        }
        ZoneId zoneId = ZoneId.of(timezone);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);

        String currentTime = zonedDateTime.getHour() + ":" + zonedDateTime.getMinute() + ":" + zonedDateTime.getSecond() + " | " + zonedDateTime.getZone().toString();

        String register = "[" + currentTime + " | " + playername + "]: " + content;
        FileConfiguration logConfig;
        switch (registerType){
            case "command":
                logFile = setupCommandsFile();
                logConfig = getCommandsConfig();
                if (logConfig.isList("commands")){
                    List<String> commands = logConfig.getStringList("commands");
                    commands.add(register);
                    logConfig.set("commands", commands);
                } else {
                    List<String> commands = new ArrayList<>();
                    commands.add(register);
                    logConfig.set("commands", commands);
                }
                saveLogFile(logFile, logConfig);
                break;
            case "message":
                logFile = setupMessagesFile();
                logConfig = getMessagesConfig();

                if (logConfig.isList("messages")){
                    List<String> messages = logConfig.getStringList("messages");
                    messages.add(register);
                    logConfig.set("messages", messages);
                } else {
                    List<String> messages = new ArrayList<>();
                    messages.add(register);
                    logConfig.set("messages", messages);
                }
                saveLogFile(logFile, logConfig);
                break;

        }
    }
}
