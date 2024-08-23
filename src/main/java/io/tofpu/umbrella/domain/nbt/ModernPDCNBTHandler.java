package io.tofpu.umbrella.domain.nbt;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ModernPDCNBTHandler implements ItemNBTHandler {
    private final ItemStack itemStack;

    public ModernPDCNBTHandler(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void setString(String key, String value) {
        if (itemStack.getType() == Material.AIR) {
            return;
        }
        ItemMeta itemMeta = getItemMeta();
        itemMeta.getPersistentDataContainer().set(namespacedKey(key), PersistentDataType.STRING, value);
        itemStack.setItemMeta(itemMeta);
    }

    private NamespacedKey namespacedKey(String key) {
        return new NamespacedKey("toolbar", key);
    }

    @Override
    public String getString(String key) {
        if (itemStack.getType() == Material.AIR) {
            return null;
        }
        return getItemMeta().getPersistentDataContainer().get(namespacedKey(key), PersistentDataType.STRING);
    }

    private ItemMeta getItemMeta() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            throw new IllegalStateException(String.format("Unable to retrieve ItemMeta of ItemStack type %s", itemStack.getType()));
        }
        return itemMeta;
    }
}