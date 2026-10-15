package com.tofpu.toolbar.core.toolbar.tool;

import com.tofpu.toolbar.core.ToolNBTUtil;
import com.tofpu.toolbar.api.toolbar.ItemSlot;
import com.tofpu.toolbar.api.toolbar.ToolWithSlot;
import com.tofpu.toolbar.api.toolbar.internal.SlotReserveLog;
import com.tofpu.toolbar.api.toolbar.tool.Tool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ToolProcessorImpl implements ToolProcessor {
    @Override
    public Collection<ToolWithSlot> process(String ownerId, Collection<ToolWithSlot> toolWithSlots) {
        SlotReserveLog slotReserveLog = new SlotReserveLog();
        List<ToolWithSlot> updatedTools = new ArrayList<>();
        for (ToolWithSlot toolWithSlot : toolWithSlots) {
            processTool(toolWithSlot, slotReserveLog, updatedTools, ownerId);
        }
        return updatedTools;
    }

    private void processTool(ToolWithSlot toolWithSlot, SlotReserveLog slotReserveLog, List<ToolWithSlot> updatedTools, String id) {
        Tool tool = toolWithSlot.tool();
        ToolNBTUtil.tag(id, tool);

        if (toolWithSlot.slot().isUndefined()) {
            toolWithSlot = determineItemSlot(toolWithSlot, slotReserveLog);
        }

        slotReserveLog.reserve(toolWithSlot.slot());
        updatedTools.add(toolWithSlot);
    }

    private ToolWithSlot determineItemSlot(ToolWithSlot toolWithSlot, SlotReserveLog slotReserveLog) {
        for (int i = 0; i < 60; i++) {
            if (slotReserveLog.isReserved(i)) continue;
            return new ToolWithSlot<>(toolWithSlot, ItemSlot.atIndex(i));
        }
        throw new IllegalStateException("Failed to find a free slot for " + toolWithSlot.tool() + " tool!");
    }
}
