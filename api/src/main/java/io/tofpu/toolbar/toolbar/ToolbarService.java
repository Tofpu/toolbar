package io.tofpu.toolbar.toolbar;

public final class ToolbarService {
    private final ToolbarRegistry toolbarRegistry;

    public ToolbarService() {
        this.toolbarRegistry = new ToolbarRegistry();
    }

    public void register(final GenericToolbar<?> toolbar) {
        toolbarRegistry.register(toolbar);
    }

    public <T extends GenericToolbar<?>> T findToolbarBy(final String identifier) {
        return (T) toolbarRegistry.findToolbarBy(identifier);
    }
}
