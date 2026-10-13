package io.tofpu.toolbar.toolbar;

import io.tofpu.toolbar.toolbar.tool.Tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public interface ToolbarSchema {
    static ToolbarSchema of(String id, Collection<ToolWithSlot> tools) {
        return new ToolbarSchemaImpl(id, tools);
    }

    static ToolbarSchema of(String id) {
        return new ToolbarSchemaImpl(id);
    }

    static ToolbarSchema.Builder newBuilder(String id) {
        return new Builder(id);
    }

    String id();
    Collection<ToolWithSlot> tools();

    class Builder {
        private final String id;
        private final List<ToolWithSlot> tools = new ArrayList<>();

        private Builder(String id) {
            this.id = id;
        }

        @SafeVarargs
        public final Builder addItems(final ToolWithSlot<Tool>... tools) {
            this.tools.addAll(Arrays.asList(tools));
            return this;
        }

        public Builder addItems(final Tool... tools) {
            for (Tool tool : tools) {
                this.addItems(new ToolWithSlot<>(tool, ItemSlot.undefined()));
            }
            return this;
        }

        public ToolbarSchema build() {
            return of(id, tools);
        }
    }
}
