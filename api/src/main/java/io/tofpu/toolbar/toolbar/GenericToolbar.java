package io.tofpu.toolbar.toolbar;

import io.tofpu.toolbar.ToolNBTUtil;
import io.tofpu.toolbar.toolbar.internal.SlotReserveLog;
import io.tofpu.toolbar.toolbar.tool.Tool;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class GenericToolbar<T extends Tool> {
    @SuppressWarnings("rawtypes")
    private static final ToolWithSlot[] EMPTY_INSTANCE_OF_TOOL_WITH_SLOT = {};
    private final String identifier;
    private final Map<String, ToolWithSlot<T>> toolMap;

    private final SlotReserveLog slotReserveLog = new SlotReserveLog();

    public GenericToolbar(final String identifier) {
        //noinspection unchecked
        this(identifier, EMPTY_INSTANCE_OF_TOOL_WITH_SLOT);
    }

    @SafeVarargs
    public GenericToolbar(final String identifier, final T... tools) {
        this.identifier = identifier;
        this.toolMap = new LinkedHashMap<>();
        addItem(tools);
    }

    public GenericToolbar(final String identifier, final ToolWithSlot<T>... toolWithSlots) {
        this.identifier = identifier;
        this.toolMap = new LinkedHashMap<>();
        addItem(toolWithSlots);
    }

    @SafeVarargs
    public final void addItem(final ToolWithSlot<T>... tools) {
        for (ToolWithSlot<T> toolWithSlot : tools) {
            T tool = toolWithSlot.tool();
            ToolNBTUtil.tag(this, tool);

            if (toolWithSlot.slot().isUndefined()) {
                toolWithSlot = determineItemSlot(toolWithSlot);
            }

            slotReserveLog.reserve(toolWithSlot.slot());
            this.toolMap.put(tool.getItemIdentifier(), toolWithSlot);
        }
    }

    public void addItem(final T... tools) {
        for (T tool : tools) {
            this.addItem(new ToolWithSlot<>(tool, ItemSlot.undefined()));
        }
    }

    public void addItem(final int index, final T tool) {
        this.addItem(new ToolWithSlot<>(tool, ItemSlot.atIndex(index)));
    }

    private ToolWithSlot<T> determineItemSlot(ToolWithSlot<T> toolWithSlot) {
        for (int i = 0; i < 60; i++) {
            if (slotReserveLog.isReserved(i)) continue;
            return new ToolWithSlot<>(toolWithSlot, ItemSlot.atIndex(i));
        }
        throw new IllegalStateException("Failed to find a free slot for " + toolWithSlot.tool() + " tool!");
    }

    public T findItemBy(final String identifier) {
        if (identifier == null) return null;
        return getToolFromWrapperIfPresent(toolMap.get(identifier));
    }

    public T getToolAt(ItemSlot slot) {
        Objects.requireNonNull(slot, "ItemSlot must be provided.");
        ToolWithSlot<T> toolWithSlot = this.toolMap.values().stream()
                .filter(tool -> tool.slot().equals(slot)).findFirst()
                .orElse(null);

        return getToolFromWrapperIfPresent(toolWithSlot);
    }

    @Nullable
    private T getToolFromWrapperIfPresent(ToolWithSlot<T> toolWithSlot) {
        if (toolWithSlot != null) {
            return toolWithSlot.tool();
        }
        return null;
    }

    public ItemStack getItemAt(ItemSlot slot) {
        Objects.requireNonNull(slot, "ItemSlot must be provided.");
        T tool = getToolAt(slot);
        if (tool != null) {
            return tool.getItem();
        }
        return null;
    }

    public Map<ItemSlot, ItemStack> getItemsWithSlots() {
        Map<ItemSlot, ItemStack> itemStackMap = new HashMap<>();
        for (Map.Entry<String, ToolWithSlot<T>> entry : this.toolMap.entrySet()) {
            ToolWithSlot<T> toolWithSlot = entry.getValue();
            ItemSlot itemSlot = toolWithSlot.slot();
            itemStackMap.put(itemSlot, toolWithSlot.tool().getItem());
        }
        return itemStackMap;
    }

    public Collection<ItemStack> getItems() {
        return this.toolMap.values().stream()
                .map(ToolWithSlot::tool)
                .map(Tool::getItem)
                .collect(Collectors.toList());
    }

    public Collection<T> getTools() {
        return Collections.unmodifiableCollection(this.toolMap.values().stream()
                .map(ToolWithSlot::tool)
                .collect(Collectors.toList()));
    }

    public int size() {
        return this.toolMap.size();
    }

    public String getIdentifier() {
        return identifier;
    }
}
