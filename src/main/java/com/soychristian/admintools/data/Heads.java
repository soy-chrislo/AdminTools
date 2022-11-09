package com.soychristian.admintools.data;

import com.soychristian.admintools.utils.SkullBuilder;
import org.bukkit.inventory.ItemStack;

public enum Heads {
    YOUTUBE("ZDJmNmMwN2EzMjZkZWY5ODRlNzJmNzcyZWQ2NDU0NDlmNWVjOTZjNmNhMjU2NDk5YjVkMmI4NGE4ZGNlIn19fQ==","youtube"),
    CAT("Njk3NDljYTk1MjM4YjVhZjkzOTA3ZjkyNGYxZDU5MTVkZjc3MzFlZTgzYzE1YWM3NzZjYTgzMmM0M2U0MDYyIn19fQ==","cat"),
    MINI("OTIxNzg4MGEyZWFiYjllZmY5MTI4NWVmMmJkZjJjN2EzN2E0NzIyN2Q3NWNjZTVlNzM0MDRjNjcxZmE2Mjg4NyJ9fX0=", "mini"),
    TWITCH_1("YjI1ODA0ODliMmQ0NGU1ZDlhOWIzZjgzNmVmMjE5ZjAzMTI5OTJkNDBiMTRkOTlmNTZjNWFmMDVjNDBmNzE1In19fQ==", "twitch_1"),
    YOUTUBE_1("ZTY3OWQ2MzBmODUxYzU4OTdkYTgzYTY0MjUxNzQzM2Y2NWRjZmIzMmIxYmFiYjFmZWMzMmRhNzEyNmE5ZjYifX19", "youtube_1"),
    WORLD_1("OGIwZjNhM2Q2ZjBjZTI0Mjc2MzdmZTYwZGE5ZDZkMGZlYjJkOGRmMGJjZmIyNzI5MWJiNzhjNjAwNjBlZmUzZSJ9fX0=", "world_1"),
    LOGS_1("YTdlZDY2ZjVhNzAyMDlkODIxMTY3ZDE1NmZkYmMwY2EzYmYxMWFkNTRlZDVkODZlNzVjMjY1ZjdlNTAyOWVjMSJ9fX0=", "logs_1"),
    BOOKSHELF_1("ZDAwM2E1NjUxYzRkMWY4YTA4YTEwNzAxYjAwNTBmYWEyMmNlYzI2ZmM2Njc3YmUwODgzODA2M2IyYTk3Y2RjZCJ9fX0=", "bookshelf_1"),;

    private ItemStack item;
    private String idTag;
    private String prefix = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";
    private Heads(String texture, String id)
    {
        item = SkullBuilder.createSkull(prefix + texture, id);
        idTag = id;
    }


    public ItemStack getItemStack()
    {
        return item;
    }

    public String getName()
    {
        return idTag;
    }
}
