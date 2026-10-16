package com.tofpu.toolbar.api.toolbar;

import java.util.Collection;
import java.util.Collections;

class ToolbarSchemaImpl implements ToolbarSchema {
    private final String id;
    private final Collection<ToolWithSlot> tools;

    public ToolbarSchemaImpl(String id) {
        this.id = id;
        this.tools = Collections.emptyList();
    }

    public ToolbarSchemaImpl(String id, Collection<ToolWithSlot> tools) {
        this.id = id;
        this.tools = Collections.unmodifiableCollection(tools);
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public Collection<ToolWithSlot> tools() {
        return tools;
    }
}
