package io.tofpu.toolbar.domain;

import io.tofpu.toolbar.domain.item.ToolFactory;

public final class ToolbarService {
    private final ToolbarRegistry toolbarRegistry;
    private final ToolbarHandler toolbarHandler;
    private final ToolbarFactory toolbarFactory;
    private final ToolFactory toolFactory;

    public ToolbarService() {
        this.toolbarRegistry = new ToolbarRegistry();
        this.toolbarHandler = new ToolbarHandler(toolbarRegistry);
        this.toolbarFactory = new ToolbarFactory(toolbarRegistry);
        this.toolFactory = new ToolFactory();
    }

    public ToolFactory getToolFactory() {
        return toolFactory;
    }

    public ToolbarFactory getToolbarFactory() {
        return toolbarFactory;
    }

    public ToolbarRegistry getToolbarRegistry() {
        return toolbarRegistry;
    }

    public ToolbarHandler getToolbarHandler() {
        return toolbarHandler;
    }
}
