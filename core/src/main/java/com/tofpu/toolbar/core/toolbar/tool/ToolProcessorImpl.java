package com.tofpu.toolbar.core.toolbar.tool;

import com.tofpu.toolbar.api.toolbar.ItemSlot;
import com.tofpu.toolbar.api.toolbar.internal.SlotReserveLog;
import com.tofpu.toolbar.api.toolbar.tool.Tool;
import com.tofpu.toolbar.core.ToolNBTUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ToolProcessorImpl implements ToolProcessor {
    @Override
    public Collection<Tool> process(String ownerId, Collection<Tool> tools) {
        SlotReserveLog slotReserveLog = new SlotReserveLog();
        List<Tool> updatedTools = new ArrayList<>();
        for (Tool tool : tools) {
            processTool(tool, slotReserveLog, updatedTools, ownerId);
        }
        return updatedTools;
    }

    private void processTool(Tool tool, SlotReserveLog slotReserveLog, List<Tool> updatedTools, String id) {
        ToolNBTUtil.tag(id, tool);

        if (tool.slot().isUndefined()) {
            tool = determineItemSlot(tool, slotReserveLog);
        }

        slotReserveLog.reserve(tool.slot());
        updatedTools.add(tool);
    }

    private Tool determineItemSlot(Tool tool, SlotReserveLog slotReserveLog) {
        for (int i = 0; i < 60; i++) {
            if (slotReserveLog.isReserved(i)) continue;
            return tool.at(ItemSlot.atIndex(i));
        }
        throw new IllegalStateException("Failed to find a free slot for " + tool.id() + " tool!");
    }
}
