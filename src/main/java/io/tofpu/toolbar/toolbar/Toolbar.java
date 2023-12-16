package io.tofpu.toolbar.toolbar;

import io.tofpu.toolbar.ToolNBTUtil;
import io.tofpu.toolbar.toolbar.item.Tool;
import org.bukkit.inventory.ItemStack;

import java.util.*;

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

    public Map<Integer, ItemStack> getItemStacks() {
        Map<Integer, ItemStack> itemStackMap = new HashMap<>();
        for (Map.Entry<String, Tool> entry : this.toolMap.entrySet()) {
            Tool tool = entry.getValue();
            int inventoryIndex = tool.getInventoryIndex();
            if (inventoryIndex < 0) {
                inventoryIndex = itemStackMap.size();
            }
            itemStackMap.put(inventoryIndex, tool.getItem());
        }
        return itemStackMap;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Collection<Tool> getTools() {
        return Collections.unmodifiableCollection(this.toolMap.values());
    }
}
