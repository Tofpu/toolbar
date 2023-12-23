package io.tofpu.toolbar;

import io.tofpu.toolbar.bootstrap.EssentialTestBootstrap;
import io.tofpu.toolbar.nbt.ItemNBTHandler;
import io.tofpu.toolbar.impl.PDCItemHandler;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemNBTHandlerTest extends EssentialTestBootstrap {
    @Test
    void write_and_get_string_test_with_different_instances() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND, 64);

        itemNBTHandler(itemStack).setString("special", "yes");
        assertEquals("yes", itemNBTHandler(itemStack).getString("special"));
    }

    @Test
    void write_and_get_string_test_with_shared_instances() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND, 64);
        ItemNBTHandler itemNBTHandler = itemNBTHandler(itemStack);

        itemNBTHandler.setString("special", "yes");
        assertEquals("yes", itemNBTHandler.getString("special"));
    }

    @NotNull
    private static ItemNBTHandler itemNBTHandler(ItemStack itemStack) {
        return new PDCItemHandler(itemStack);
    }
}
