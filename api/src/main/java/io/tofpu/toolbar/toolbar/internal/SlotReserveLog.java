package io.tofpu.toolbar.toolbar.internal;

import io.tofpu.toolbar.toolbar.ItemSlot;

import java.util.HashMap;
import java.util.Map;

public class SlotReserveLog {
    private final Map<Integer, ItemSlot> reservedSlots = new HashMap<>();

    public void reserve(ItemSlot slot) {
        requireValidSlot(slot);
        reservedSlots.put(slot.asIndex(), slot);
    }

    private static void requireValidSlot(ItemSlot slot) {
        if (slot.isUndefined()) throw new IllegalStateException("Cannot reserve an undefined item slot!");
    }

    public boolean isReserved(ItemSlot slot) {
        return isReserved(slot.asIndex());
    }

    public boolean isReserved(int index) {
        if (index < 0) {
            return true;
        }
        return this.reservedSlots.containsKey(index);
    }
}
