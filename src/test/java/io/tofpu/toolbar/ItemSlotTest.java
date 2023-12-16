package io.tofpu.toolbar;

import io.tofpu.toolbar.toolbar.tool.ItemSlot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemSlotTest {
    @Test
    void undefined_item_slot() {
        ItemSlot itemSlot = ItemSlot.undefined();
        assertTrue(itemSlot.isUndefined());
        assertThrows(Exception.class, itemSlot::asIndex);
    }

    @Test
    void slot_with_invalid_index() {
        assertThrows(Exception.class, () -> ItemSlot.atIndex(-1));
    }

    @Test
    void slot_with_invalid_number() {
        assertThrows(Exception.class, () -> ItemSlot.atNumber(0));
    }

    @Test
    void slot_with_min_index() {
        ItemSlot itemSlot = ItemSlot.atIndex(0);
        assertFalse(itemSlot.isUndefined());
        assertEquals(0, itemSlot.asIndex());
    }

    @Test
    void slot_with_min_number() {
        ItemSlot itemSlot = ItemSlot.atNumber(1);
        assertFalse(itemSlot.isUndefined());
        assertEquals(0, itemSlot.asIndex());
    }

    @Test
    void slot_with_max_index() {
        ItemSlot itemSlot = ItemSlot.atIndex(ItemSlot.MAXIMUM_SLOT_INDEX);
        assertFalse(itemSlot.isUndefined());
        assertEquals(ItemSlot.MAXIMUM_SLOT_INDEX, itemSlot.asIndex());
    }

    @Test
    void slot_with_max_number() {
        ItemSlot itemSlot = ItemSlot.atNumber(ItemSlot.MAXIMUM_SLOT_INDEX + 1);
        assertFalse(itemSlot.isUndefined());
        assertEquals(ItemSlot.MAXIMUM_SLOT_INDEX, itemSlot.asIndex());
    }

    @Test
    void slot_with_exceeded_max_index() {
        assertThrows(Exception.class, () -> ItemSlot.atIndex(ItemSlot.MAXIMUM_SLOT_INDEX + 1));
    }

    @Test
    void slot_with_exceeded_max_number() {
        assertThrows(Exception.class, () -> ItemSlot.atIndex(ItemSlot.MAXIMUM_SLOT_NUMBER + 1));
    }
}
