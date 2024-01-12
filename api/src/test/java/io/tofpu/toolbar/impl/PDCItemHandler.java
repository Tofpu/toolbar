package io.tofpu.toolbar.impl;

import io.tofpu.toolbar.nbt.ItemNBTHandler;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class PDCItemHandler implements ItemNBTHandler {
    private final ItemStack itemStack;

    public PDCItemHandler(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void setString(String key, String value) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey namespacedKey = namespacedKey(key);
        itemMeta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, value);
        itemStack.setItemMeta(itemMeta);
    }

    @NotNull
    private static NamespacedKey namespacedKey(String key) {
        return new NamespacedKey("test", key);
    }

    @Override
    public String getString(String key) {
        PersistentDataContainer persistentDataContainer = itemStack.getItemMeta().getPersistentDataContainer();
        NamespacedKey namespacedKey = namespacedKey(key);
        return persistentDataContainer.get(namespacedKey, PersistentDataType.STRING);
    }
}
