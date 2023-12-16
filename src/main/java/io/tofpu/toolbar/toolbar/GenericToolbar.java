package io.tofpu.toolbar.toolbar;

import io.tofpu.toolbar.ToolNBTUtil;
import io.tofpu.toolbar.toolbar.tool.Tool;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class GenericToolbar<T extends Tool> {
    private final String identifier;
    private final Map<String, T> toolMap;

    public GenericToolbar(final String identifier) {
        this(identifier, new LinkedList<>());
    }

    public GenericToolbar(final String identifier, final Collection<? extends T> tools) {
        this.identifier = identifier;
        this.toolMap = new LinkedHashMap<>();

        T[] array = (T[]) tools.toArray(new Tool[]{});
        addItem(array);
    }

    public void addItem(final T... tools) {
        for (final T tool : tools) {
            ToolNBTUtil.tag(this, tool);
            this.toolMap.put(tool.getItemIdentifier(), tool);
        }
    }

    public T findItemBy(final String identifier) {
        if (identifier == null) return null;
        return toolMap.get(identifier);
    }

    public T getToolAt(ItemSlot slot) {
        Objects.requireNonNull(slot, "ItemSlot must be provided.");
        return this.toolMap.values().stream().filter(tool -> tool.getSlot().equals(slot)).findFirst().orElse(null);
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
        for (Map.Entry<String, T> entry : this.toolMap.entrySet()) {
            Tool tool = entry.getValue();
            ItemSlot itemSlot = tool.getSlot();
            itemStackMap.put(itemSlot, tool.getItem());
        }
        return itemStackMap;
    }

    public Collection<ItemStack> getItems() {
        return this.toolMap.values().stream().map(Tool::getItem).collect(Collectors.toList());
    }

    public Collection<T> getTools() {
        return Collections.unmodifiableCollection(this.toolMap.values());
    }

    public int size() {
        return this.toolMap.size();
    }

    public String getIdentifier() {
        return identifier;
    }
}
