package io.tofpu.toolbar.nbt;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

public class BukkitNBTHandler implements ItemNBTHandler {
    private final NBTItem nbtItem;

    public BukkitNBTHandler(ItemStack itemStack) {
        this.nbtItem = new NBTItem(itemStack);
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
