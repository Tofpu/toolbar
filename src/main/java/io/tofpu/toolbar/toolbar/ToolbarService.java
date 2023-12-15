package io.tofpu.toolbar.toolbar;

import io.tofpu.toolbar.toolbar.item.ToolFactory;

public final class ToolbarService {
    private final ToolbarRegistry toolbarRegistry;
    private final ToolbarFactory toolbarFactory;
    private final ToolFactory toolFactory;

    public ToolbarService() {
        this.toolbarRegistry = new ToolbarRegistry();
        this.toolbarFactory = new ToolbarFactory();
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
}
