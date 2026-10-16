package com.tofpu.toolbar.core.toolbar.tool;

import com.tofpu.toolbar.api.toolbar.tool.Tool;

import java.util.Collection;

public interface ToolProcessor {
    Collection<Tool> process(String ownerId, Collection<Tool> tools);
}
