package com.soychristian.admintools.commands;

import com.soychristian.admintools.views.TestGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminToolsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            player.sendMessage("Hello World!");
            new TestGUI(player, "test", "Test GUI", 3).open();
        } else {
            sender.sendMessage("You must be a player to use this command!");
        }
        return true;
    }
}
