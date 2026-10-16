package com.tofpu.toolbar.api.toolbar;

import com.tofpu.toolbar.api.toolbar.tool.Tool;

import java.util.Collection;
import java.util.Collections;

class ToolbarSchemaImpl implements ToolbarSchema {
    private final String id;
    private final Collection<Tool> tools;

    public ToolbarSchemaImpl(String id) {
        this.id = id;
        this.tools = Collections.emptyList();
    }

    public ToolbarSchemaImpl(String id, Collection<Tool> tools) {
        this.id = id;
        this.tools = Collections.unmodifiableCollection(tools);
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public Collection<Tool> tools() {
        return tools;
    }
}
