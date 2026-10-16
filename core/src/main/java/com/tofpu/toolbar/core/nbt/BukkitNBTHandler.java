package com.tofpu.toolbar.core.nbt;

import de.tr7zw.changeme.nbtapi.NBTItem;
import com.tofpu.toolbar.api.nbt.ItemNBTHandler;
import org.bukkit.inventory.ItemStack;

public class BukkitNBTHandler implements ItemNBTHandler {
    private final NBTItem nbtItem;

    public BukkitNBTHandler(ItemStack itemStack) {
        this.nbtItem = new NBTItem(itemStack, true);
    }

    @Override
    public void setString(String key, String value) {
        nbtItem.setString(key, value);
    }

    @Override
    public String getString(String key) {
        return nbtItem.getString(key);
    }
}
