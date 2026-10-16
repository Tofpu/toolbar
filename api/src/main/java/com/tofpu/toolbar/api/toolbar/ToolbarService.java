package com.tofpu.toolbar.api.toolbar;

public interface ToolbarService {
    Toolbar register(ToolbarSchema schema);
    Toolbar findToolbarBy(String id);
}
