package io.tofpu.toolbar.bootstrap;

import io.tofpu.toolbar.ToolbarAPI;
import io.tofpu.toolbar.impl.PDCItemHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class FullTestBoostrap extends EssentialTestBootstrap {
    protected ToolbarAPI api;

    @BeforeEach
    public void setUp() {
        super.setUp();
        api = new ToolbarAPI(plugin, PDCItemHandler::new);
        api.enable();
    }

    @AfterEach
    public void tearDown() {
        api.disable();
        super.tearDown();
    }
}
