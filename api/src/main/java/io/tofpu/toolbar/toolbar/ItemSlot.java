package io.tofpu.toolbar.toolbar;

import java.util.StringJoiner;

public class ItemSlot {
    public static final int MAXIMUM_SLOT_INDEX = 53;
    public static final int MAXIMUM_SLOT_NUMBER = 54;
    private final int index;

    public static ItemSlot atIndex(int index) {
        checkSlotIsValid(index);
        return new ItemSlot(index);
    }

    public static ItemSlot atNumber(int number) {
        return atIndex(number - 1);
    }

    private ItemSlot(int index) {
        this.index = index;
    }

    public static ItemSlot undefined() {
        return new ItemSlot(-1);
    }

    public boolean isUndefined() {
        return this.index == -1;
    }

    public int asIndex() {
        checkSlotIsValid(index);
        return index;
    }

    public int asNumber() {
        checkSlotIsValid(index);
        return index + 1;
    }

    private static void checkSlotIsValid(int index) {
        if (index < 0 || index > MAXIMUM_SLOT_INDEX) {
            throw new RuntimeException("ItemSlot must range between 0-53: " + index);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemSlot itemSlot = (ItemSlot) o;

        return index == itemSlot.index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ItemSlot.class.getSimpleName() + "[", "]")
                .add("index=" + index)
                .toString();
    }
}
