package io.github.tofpu.toolbar;

import io.tofpu.toolbar.toolbar.ItemSlot;
import io.tofpu.toolbar.toolbar.ToolWithSlot;
import io.tofpu.toolbar.toolbar.Toolbar;

import java.util.*;

public class ToolbarImpl implements Toolbar {
    private final String id;
    private final Map<String, ToolWithSlot> toolMap;

    public ToolbarImpl(String id, Collection<ToolWithSlot> toolWithSlots) {
        this.id = id;
        this.toolMap = new HashMap<>();
        populateToolMap(toolWithSlots);
    }

    private void populateToolMap(Collection<ToolWithSlot> toolWithSlots) {
        toolWithSlots.forEach(toolWithSlot -> {
            String toolId = toolWithSlot.tool().id();
            toolMap.put(toolId, toolWithSlot);
        });
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public Optional<ToolWithSlot> toolWithSlot(String identifier) {
        return Optional.ofNullable(toolMap.get(identifier));
    }

    @Override
    public Optional<ToolWithSlot> toolWithSlotAt(ItemSlot slot) {
        return this.toolMap.values().stream()
                .filter(tool -> tool.slot().equals(slot))
                .findFirst();
    }

    @Override
    public Collection<ToolWithSlot> toolsWithSlots() {
        return Collections.unmodifiableCollection(toolMap.values());
    }
}
