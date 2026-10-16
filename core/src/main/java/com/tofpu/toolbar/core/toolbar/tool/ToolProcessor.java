package com.tofpu.toolbar.core.toolbar.tool;

import com.tofpu.toolbar.api.toolbar.ToolWithSlot;

import java.util.Collection;

public interface ToolProcessor {
    Collection<ToolWithSlot> process(String ownerId, Collection<ToolWithSlot> toolWithSlots);
}
