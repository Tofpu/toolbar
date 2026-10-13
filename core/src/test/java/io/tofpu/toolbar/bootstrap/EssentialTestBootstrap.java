package io.tofpu.toolbar.bootstrap;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class EssentialTestBootstrap {
    protected ServerMock server;
    protected JavaPlugin plugin;

    public EssentialTestBootstrap(ServerMock server, JavaPlugin plugin) {
        this.server = server;
        this.plugin = plugin;
    }

    public EssentialTestBootstrap() {
    }

    @BeforeEach
    public void setUp() {
        if (server == null) {
            server = MockBukkit.mock();
        }
        if (plugin == null) {
            plugin = MockBukkit.createMockPlugin();
        }
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }
}
