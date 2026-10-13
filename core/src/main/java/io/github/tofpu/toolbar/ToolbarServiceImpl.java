package io.github.tofpu.toolbar;

import io.github.tofpu.toolbar.tool.ToolProcessorImpl;
import io.tofpu.toolbar.toolbar.ToolWithSlot;
import io.tofpu.toolbar.toolbar.Toolbar;
import io.tofpu.toolbar.toolbar.ToolbarService;
import io.tofpu.toolbar.toolbar.ToolbarSchema;

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
