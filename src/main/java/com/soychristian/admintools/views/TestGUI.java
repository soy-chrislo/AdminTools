package com.soychristian.admintools.views;

import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class TestGUI extends Gui {
    //HeadsUtils hu = HeadsUtils.getInstance();
    public TestGUI(Player player, String id, String title, int rows) {
        super(player, id, title, rows);
    }

    public TestGUI(Player player, String id, String title, InventoryType inventoryType) {
        super(player, id, title, inventoryType);
    }

    @Override
    public void onOpen(InventoryOpenEvent inventoryOpenEvent) {
        /*hu.setDatabaseLoader(new MinecraftHeadsLoader(null));
        Head head;
        try {
            head = hu.getHead("Michael Myers");
        } catch (IOException | AuthenticationException e) {
            throw new RuntimeException(e);
        }
        ItemStack itemHead = HeadCreator.getItemStack(head);*/

        //HeadDatabaseAPI api = new HeadDatabaseAPI();
        //ItemStack itemHead = api.getItemHead("Michael Myers");

        addItem(0, listaUsuariosIcon());
        addItem(1, menuAccionesIcon());
        addItem(2, menuAdministradorIcon());
        //addItem(3, new Icon(itemHead));
    }
    public Icon listaUsuariosIcon(){
        Icon icon = new Icon(Material.DIRT);
        icon.setName("Lista de usuarios");
        icon.setLore("Click Izquierdo - Conectados", "Click Derecho - Desconectados", "Click Medio - Todos");

        icon.onClick(itemEvent -> {
            if (itemEvent.getClick().isLeftClick()){
                itemEvent.getWhoClicked().sendMessage("Click Izquierdo - Conectados");
            } else if (itemEvent.getClick().isRightClick()){
                itemEvent.getWhoClicked().sendMessage("Click Derecho - Desconectados");
            } else {
                itemEvent.getWhoClicked().sendMessage("Click Medio - Todos");
            }
        });

        return icon;
    }

    public Icon menuAccionesIcon(){
        Icon icon = new Icon(Material.DIRT);
        icon.setName("Menú de acciones");

        icon.onClick(itemEvent -> {
            itemEvent.getWhoClicked().sendMessage("Abriendo menú de acciones");
        });
        return icon;
    }

    public Icon menuAdministradorIcon(){
        Icon icon = new Icon(Material.DIRT);
        icon.setName("Menú Administrador");

        icon.onClick(itemEvent -> {
            itemEvent.getWhoClicked().sendMessage("Abriendo menú de administrador");
        });
        return icon;
    }
}
