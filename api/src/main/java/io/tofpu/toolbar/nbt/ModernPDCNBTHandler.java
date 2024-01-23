package io.tofpu.toolbar.nbt;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class ModernPDCNBTHandler implements ItemNBTHandler {
    private final ItemStack itemStack;

    public ModernPDCNBTHandler(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void setString(String key, String value) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.getPersistentDataContainer().set(namespacedKey(key), PersistentDataType.STRING, value);
        itemStack.setItemMeta(itemMeta);
    }

    @NotNull
    private NamespacedKey namespacedKey(String key) {
        return new NamespacedKey("toolbar", key);
    }

    @Override
    public String getString(String key) {
        return getItemMeta().getPersistentDataContainer().get(namespacedKey(key), PersistentDataType.STRING);
    }

    @NotNull
    private ItemMeta getItemMeta() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            throw new IllegalStateException(String.format("Unable to retrieve ItemMeta of ItemStack type %s", itemStack.getType()));
        }
        return itemMeta;
    }
}
