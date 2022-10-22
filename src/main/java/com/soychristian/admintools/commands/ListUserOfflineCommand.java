package com.soychristian.admintools.commands;

import com.soychristian.admintools.views.UsersOffGUI;
import com.soychristian.admintools.views.UsersOnGUI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListUserOfflineCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            // TODO: NOT READY YET

            Player player = (Player) sender;
            player.sendMessage("Opening UsersOnGUI");
            UsersOffGUI.buildGui();
            player.openInventory(UsersOffGUI.getInventory(0));
        }

        return true;
    }
}
