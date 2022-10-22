package com.soychristian.admintools.commands;

import com.soychristian.admintools.views.UsersOnGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ListUserOnlineCommand implements CommandExecutor {
    static Inventory inventory1;
    static Inventory inventory2;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            player.sendMessage("Opening UsersOnGUI");
            UsersOnGUI.buildGui();
            player.openInventory(UsersOnGUI.getInventory(0));
        }

        return true;
    }
}
