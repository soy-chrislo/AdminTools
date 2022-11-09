package com.soychristian.admintools.utils;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SortItems {
    /**
     * Sorts an array of ItemStacks by their attributes.
     * @param order Acepta los valores "abc" y "cba" para ordenar de forma ascendente y descendente respectivamente.
     * @param items
     * @return
     */
    public static ItemStack[] sortByName(String order, ItemStack[] items){
        //ItemStack[] noSortedItems = items;
        List<ItemStack> noSortedItems = Arrays.asList(items);
        List<ItemStack> sortedItems = new ArrayList<>();
        if (!(order.equals("abc") || order.equals("cba"))) return null;

        if (order.equals("abc")){
            List<ItemStack> noSortedItemsTemp = new ArrayList<>(noSortedItems);
            Comparator<ItemStack> compareByName = Comparator.comparing((ItemStack itemStack) -> itemStack.getItemMeta().getDisplayName());
            //Collections.sort(noSortedItems, compareByName);
            noSortedItemsTemp.sort(compareByName);
            sortedItems.addAll(noSortedItemsTemp);
        }

        if (order.equals("cba")){
            List<ItemStack> noSortedItemsTemp = new ArrayList<>(noSortedItems);
            Comparator<ItemStack> compareByName = Comparator.comparing((ItemStack itemStack) -> itemStack.getItemMeta().getDisplayName());
            //Collections.sort(noSortedItems, compareByName);
            noSortedItemsTemp.sort(compareByName);
            Collections.reverse(noSortedItemsTemp);
            sortedItems.addAll(noSortedItemsTemp);
        }

        ItemStack[] sortedItemsArray = new ItemStack[sortedItems.size()];
        sortedItemsArray = sortedItems.toArray(sortedItemsArray);
        return sortedItemsArray;
    }
}
