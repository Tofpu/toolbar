package io.tofpu.toolbar.toolbar;

import io.tofpu.toolbar.ToolNBTUtil;
import io.tofpu.toolbar.toolbar.tool.Tool;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class Toolbar {
    private final String identifier;
    private final Map<String, Tool> toolMap;

    public Toolbar(final String identifier) {
        this(identifier, new LinkedList<>());
    }

    public Toolbar(final String identifier, final Collection<Tool> tools) {
        this.identifier = identifier;
        this.toolMap = new LinkedHashMap<>();

        addItem(tools.toArray(new Tool[]{}));
    }

    public void addItem(final Tool... tools) {
        for (final Tool tool : tools) {
            ToolNBTUtil.tag(this, tool);
            this.toolMap.put(tool.getItemIdentifier(), tool);
        }
    }

    public Tool findItemBy(final String identifier) {
        if (identifier == null) return null;
        return toolMap.get(identifier);
    }

    public Tool getToolAt(ItemSlot slot) {
        Objects.requireNonNull(slot, "ItemSlot must be provided.");
        return this.toolMap.values().stream().filter(tool -> tool.getSlot().equals(slot)).findFirst().orElse(null);
    }

    public ItemStack getItemAt(ItemSlot slot) {
        Objects.requireNonNull(slot, "ItemSlot must be provided.");
        Tool tool = getToolAt(slot);
        if (tool != null) {
            return tool.getItem();
        }
        return null;
    }

    public Map<ItemSlot, ItemStack> getItemsWithSlots() {
        Map<ItemSlot, ItemStack> itemStackMap = new HashMap<>();
        for (Map.Entry<String, Tool> entry : this.toolMap.entrySet()) {
            Tool tool = entry.getValue();
            ItemSlot itemSlot = tool.getSlot();
            itemStackMap.put(itemSlot, tool.getItem());
        }
        return itemStackMap;
    }

    public Collection<ItemStack> getItems() {
        return this.toolMap.values().stream().map(Tool::getItem).collect(Collectors.toList());
    }

    public Collection<Tool> getTools() {
        return Collections.unmodifiableCollection(this.toolMap.values());
    }

    public int size() {
        return this.toolMap.size();
    }

    public String getIdentifier() {
        return identifier;
    }
}
