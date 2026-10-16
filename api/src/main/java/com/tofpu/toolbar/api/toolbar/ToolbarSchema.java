package com.tofpu.toolbar.api.toolbar;

import com.tofpu.toolbar.api.toolbar.tool.Tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public interface ToolbarSchema {
    static ToolbarSchema of(String id, Collection<Tool> tools) {
        return new ToolbarSchemaImpl(id, tools);
    }

    static ToolbarSchema of(String id) {
        return new ToolbarSchemaImpl(id);
    }

    static ToolbarSchema.Builder newBuilder(String id) {
        return new Builder(id);
    }

    String id();
    Collection<Tool> tools();

    class Builder {
        private final String id;
        private final List<Tool> tools = new ArrayList<>();

        private Builder(String id) {
            this.id = id;
        }

        public final Builder addItems(final Tool... tools) {
            this.tools.addAll(Arrays.asList(tools));
            return this;
        }

        public ToolbarSchema build() {
            return of(id, tools);
        }
    }
}
