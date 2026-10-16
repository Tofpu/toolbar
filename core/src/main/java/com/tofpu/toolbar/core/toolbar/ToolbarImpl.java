package com.tofpu.toolbar.core.toolbar;

import com.tofpu.toolbar.api.toolbar.ItemSlot;
import com.tofpu.toolbar.api.toolbar.Toolbar;
import com.tofpu.toolbar.api.toolbar.tool.Tool;

import java.util.*;

public class ToolbarImpl implements Toolbar {
    private final String id;
    private final Map<String, Tool> toolMap;

    public ToolbarImpl(String id, Collection<Tool> tools) {
        this.id = id;
        this.toolMap = new HashMap<>();
        populateToolMap(tools);
    }

    private void populateToolMap(Collection<Tool> tools) {
        tools.forEach(tool -> {
            String toolId = tool.id();
            toolMap.put(toolId, tool);
        });
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public Optional<Tool> tool(String identifier) {
        return Optional.ofNullable(toolMap.get(identifier));
    }

    @Override
    public Optional<Tool> toolAt(ItemSlot slot) {
        return this.toolMap.values().stream()
                .filter(tool -> tool.slot().equals(slot))
                .findFirst();
    }

    @Override
    public Collection<Tool> tools() {
        return Collections.unmodifiableCollection(toolMap.values());
    }
}
