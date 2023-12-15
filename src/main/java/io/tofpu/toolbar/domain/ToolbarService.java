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

    public ToolFactory getUmbrellaItemFactory() {
        return toolFactory;
    }

    public ToolbarFactory getUmbrellaFactory() {
        return toolbarFactory;
    }

    public ToolbarRegistry getUmbrellaRegistry() {
        return toolbarRegistry;
    }

    public ToolbarHandler getUmbrellaHandler() {
        return toolbarHandler;
    }
}
