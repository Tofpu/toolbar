package io.tofpu.toolbar.bootstrap;

import be.seeseemelk.mockbukkit.ServerMock;
import io.tofpu.toolbar.ToolbarAPI;
import io.tofpu.toolbar.impl.PDCItemHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class FullTestBoostrap extends EssentialTestBootstrap {
    protected ToolbarAPI api;

    public FullTestBoostrap(ServerMock server, JavaPlugin plugin) {
        super(server, plugin);
    }

    public FullTestBoostrap() {
    }

    @BeforeEach
    public void setUp() {
        super.setUp();
        if (ToolbarAPI.getInstance() == null) {
            api = new ToolbarAPI(plugin, PDCItemHandler::new);
            api.enable();
        } else {
            api = ToolbarAPI.getInstance();
        }
    }

    @AfterEach
    public void tearDown() {
        if (api != null) {
            api.disable();
        }
        super.tearDown();
    }
}
