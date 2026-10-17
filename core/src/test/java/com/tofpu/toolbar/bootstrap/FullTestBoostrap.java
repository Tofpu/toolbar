package com.tofpu.toolbar.bootstrap;

import be.seeseemelk.mockbukkit.ServerMock;
import com.tofpu.toolbar.core.ToolbarAPIImpl;
import com.tofpu.toolbar.api.ToolbarAPI;
import com.tofpu.toolbar.core.settings.Settings;
import com.tofpu.toolbar.impl.PDCItemHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class FullTestBoostrap extends EssentialTestBootstrap {
    protected ToolbarAPIImpl api;
    private boolean load = true;

    public FullTestBoostrap(ServerMock server, JavaPlugin plugin) {
        super(server, plugin);
    }

    public FullTestBoostrap() {
    }

    public void setLoadAndInitializeToolbarAPI(boolean load) {
        this.load = load;
    }

    @BeforeEach
    public void setUp() {
        super.setUp();
        if (load && !ToolbarAPI.isInstanceAvailable()) {
            api = new ToolbarAPIImpl(plugin, PDCItemHandler::new, Settings.DEFAULT);
            ToolbarAPI.setInstance(api);
            api.load();
        } else {
            api = (ToolbarAPIImpl) ToolbarAPI.getInstance();
        }
    }

    @AfterEach
    public void tearDown() {
        if (api != null) {
            api.disable();
        }
        if (load) {
            ToolbarAPI.clearInstance();
        }
        super.tearDown();
    }
}
