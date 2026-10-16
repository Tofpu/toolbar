package com.tofpu.toolbar.helper;

import com.tofpu.toolbar.api.toolbar.ItemSlot;
import com.tofpu.toolbar.api.toolbar.ToolWithSlot;
import com.tofpu.toolbar.api.toolbar.ToolbarSchema;
import com.tofpu.toolbar.api.toolbar.tool.Tool;

import java.util.Arrays;
import java.util.Collections;

public class ToolbarCreationHelper {
    public static ToolbarSchema toolbarSchema(String id, ToolWithSlot tool) {
        return ToolbarSchema.of(id, Collections.singleton(tool));
    }

    public static ToolbarSchema toolbarSchema(String id, ToolWithSlot[] tools) {
        return ToolbarSchema.of(id, Arrays.asList(tools));
    }

    public static ToolbarSchema toolbarSchema(String id, Tool[] tools) {
        ToolWithSlot[] toolWithSlots = Arrays.stream(tools)
                .map(tool -> new ToolWithSlot(tool, ItemSlot.undefined()))
                .toArray(ToolWithSlot[]::new);
        return toolbarSchema(
                id,
                toolWithSlots
        );
    }

    public static ToolbarSchema toolbarSchema(String id) {
        return ToolbarSchema.of(id, Collections.emptyList());
    }
}
