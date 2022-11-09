package com.soychristian.admintools.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
// TODO: Agregar la parte logica, que es cuando un jugador cambia de DisplayName.

public class PlayerChangedDisplayNameEvent extends Event {
    private Player whoChanged;
    private String oldDisplayName;
    private String newDisplayName;
    public PlayerChangedDisplayNameEvent(Player whoChanged, String oldDisplayName, String newDisplayName){
        this.whoChanged = whoChanged;
        this.oldDisplayName = oldDisplayName;
        this.newDisplayName = newDisplayName;
    }
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public Player getWhoChanged() {
        return whoChanged;
    }

    public void setWhoChanged(Player whoChanged) {
        this.whoChanged = whoChanged;
    }

    public String getOldDisplayName() {
        return oldDisplayName;
    }

    public void setOldDisplayName(String oldDisplayName) {
        this.oldDisplayName = oldDisplayName;
    }

    public String getNewDisplayName() {
        return newDisplayName;
    }

    public void setNewDisplayName(String newDisplayName) {
        this.newDisplayName = newDisplayName;
    }
}
