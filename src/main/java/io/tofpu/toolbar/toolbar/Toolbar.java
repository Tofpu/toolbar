package io.tofpu.toolbar.toolbar;

import io.tofpu.toolbar.ToolNBTUtil;
import io.tofpu.toolbar.toolbar.item.Tool;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class Toolbar {
    private final String identifier;
    private final Map<String, Tool> itemMap;

    public Toolbar(final String identifier) {
        this(identifier, new LinkedList<>());
    }

    public Toolbar(final String identifier, final Collection<Tool> tools) {
        this.identifier = identifier;
        this.itemMap = new HashMap<>();

        for (final Tool tool : tools) {
            this.itemMap.put(tool.getItemIdentifier(), tool);
        }
    }

    public void addItem(final Tool... tools) {
        for (final Tool tool : tools) {
            ToolNBTUtil.tag(this, tool);
            this.itemMap.put(tool.getItemIdentifier(), tool);
        }
    }

    public Tool findItemBy(final String identifier) {
        if (identifier == null) return null;
        return itemMap.get(identifier);
    }

    public Map<Integer, ItemStack> getItemStacks() {
        return this.itemMap.values().stream().collect(Collectors.toMap(Tool::getInventoryIndex, Tool::getItem));
    }

    public String getIdentifier() {
        return identifier;
    }

    public Map<String, Tool> getCopyItemMap() {
        return Collections.unmodifiableMap(this.itemMap);
    }
}
