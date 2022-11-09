package com.soychristian.admintools.views;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AdminToolsGUI implements Listener {
    static Inventory mainInventory = Bukkit.createInventory(null, 9 * 6, "Admin Tools");
    static ItemStack playersItem;
    public static void buildGui(Player player){
        playersItem = itemBuilder("&6Players", Material.BOOK, 1, true,
                "&4Lore mas chulo",
                "&8del barrio",
                "&7y del mundo mundial");
        mainInventory.setItem(0, playersItem);

        player.openInventory(mainInventory);
    }

    public static ItemStack itemBuilder(String displayName, Material material, int amount, boolean glow, String... lore){
        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));

        //List<String> loreList = new ArrayList<>(Arrays.asList(lore));
        List<String> loreList = new ArrayList<>();
        for(String loreLine : lore){
            loreList.add(ChatColor.translateAlternateColorCodes('&', loreLine));
        }
        itemMeta.setLore(loreList);

        if (glow) {
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(itemMeta);
        return item;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Inventory inventoryEvent = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        if (inventoryEvent.equals(mainInventory)){
            player.sendMessage("Clicked on main inventory");
            ItemStack currentItem = event.getCurrentItem();
            if (currentItem == null || currentItem.getType() == Material.AIR) return;
            String displayName = currentItem.getItemMeta().getDisplayName();
            if(displayName.equals(ChatColor.translateAlternateColorCodes('&', "&6Players"))){
                player.sendMessage("Clicked on players from displayname comparation");
            }
            if(currentItem.equals(playersItem)){
                player.sendMessage("Clicked on players from instance");
            }
        }
    }
}
