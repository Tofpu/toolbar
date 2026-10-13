package io.github.tofpu.toolbar.tool;

import io.tofpu.toolbar.toolbar.ToolWithSlot;
import io.tofpu.toolbar.toolbar.Toolbar;

import java.util.Collection;

public interface ToolProcessor {
    Collection<ToolWithSlot> process(String ownerId, Collection<ToolWithSlot> toolWithSlots);
}
