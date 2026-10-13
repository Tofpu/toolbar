package io.tofpu.toolbar;

import io.tofpu.toolbar.listener.ListenerService;
import io.tofpu.toolbar.nbt.ItemNBTHandler;
import io.tofpu.toolbar.player.EquipService;
import io.tofpu.toolbar.toolbar.ToolbarService;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicReference;

public interface ToolbarAPI {
    class Holder {
        private static final AtomicReference<ToolbarAPI> instance = new AtomicReference<>();

        private Holder() {
            /* This utility class should not be instantiated */
        }
    }

    static boolean isInstanceAvailable() {
        return Holder.instance.get() != null;
    }

    static ToolbarAPI getInstance() {
        ToolbarAPI toolbarAPI = Holder.instance.get();
        if (toolbarAPI == null) {
            throw new IllegalStateException("ToolbarAPI has not been loaded yet! Did you call the loader in onEnable?");
        }
        return toolbarAPI;
    }

    static void setInstance(ToolbarAPI instance) {
        if (!Holder.instance.compareAndSet(null, instance)) {
            throw new IllegalStateException("ToolbarAPI is already initialized!");
        }
    }

    void enable();
    void disable();

    EquipService equipService();
    ToolbarService toolbarService();
    ListenerService listenerService();
    ItemNBTHandler itemNBTHandler(ItemStack itemStack);

    PlatformMetadata platformMetadata();
}
