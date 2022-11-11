package com.soychristian.admintools.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemFactory {
    public static ItemStack itemBuilder(String displayName, Material material, int amount, boolean glow, String... lore){
        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));

        //List<String> loreList = new ArrayList<>(Arrays.asList(lore));
        if (lore != null){
            List<String> loreList = new ArrayList<>();
            for (String loreLine : lore){
                loreList.add(ChatColor.translateAlternateColorCodes('&', loreLine));
            }
            itemMeta.setLore(loreList);
        }

        if (glow) {
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack itemBuilder(String displayName, ItemStack itemStack, int amount, boolean glow, String... lore){
        ItemStack item = new ItemStack(itemStack);
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
}
