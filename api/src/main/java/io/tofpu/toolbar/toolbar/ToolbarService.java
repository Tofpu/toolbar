package io.tofpu.toolbar.toolbar;

public interface ToolbarService {
    Toolbar register(ToolbarSchema schema);
    Toolbar findToolbarBy(String id);
}
