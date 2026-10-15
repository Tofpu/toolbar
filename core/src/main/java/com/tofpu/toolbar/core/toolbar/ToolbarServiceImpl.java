package com.tofpu.toolbar.core.toolbar;

import com.tofpu.toolbar.core.toolbar.tool.ToolProcessorImpl;
import com.tofpu.toolbar.api.toolbar.ToolWithSlot;
import com.tofpu.toolbar.api.toolbar.Toolbar;
import com.tofpu.toolbar.api.toolbar.ToolbarService;
import com.tofpu.toolbar.api.toolbar.ToolbarSchema;

import java.util.Collection;

public final class ToolbarServiceImpl implements ToolbarService {
    private final ToolbarRegistry toolbarRegistry;
    private final ToolProcessorImpl toolProcessor;

    public ToolbarServiceImpl(ToolbarRegistry toolbarRegistry, ToolProcessorImpl toolProcessor) {
        this.toolbarRegistry = toolbarRegistry;
        this.toolProcessor = toolProcessor;
    }

    @Override
    public Toolbar register(ToolbarSchema schema) {
        Collection<ToolWithSlot> toolWithSlots = toolProcessor.process(schema.id(), schema.tools());
        Toolbar toolbar = new ToolbarImpl(
                schema.id(),
                toolWithSlots
        );
        toolbarRegistry.register(toolbar);
        return toolbar;
    }

    @Override
    public Toolbar findToolbarBy(final String id) {
        return toolbarRegistry.findToolbarBy(id);
    }
}
