package io.tofpu.toolbar.toolbar;

public final class ToolbarService {
    private final ToolbarRegistry toolbarRegistry;

    public ToolbarService() {
        this.toolbarRegistry = new ToolbarRegistry();
    }

    public void register(final Toolbar toolbar) {
        toolbarRegistry.register(toolbar);
    }

    public Toolbar findToolbarBy(final String identifier) {
        return toolbarRegistry.findToolbarBy(identifier);
    }
}
