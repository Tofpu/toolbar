package com.tofpu.toolbar.api;

import com.tofpu.toolbar.api.listener.ListenerService;
import com.tofpu.toolbar.api.nbt.ItemNBTHandler;
import com.tofpu.toolbar.api.player.EquipService;
import com.tofpu.toolbar.api.toolbar.ToolbarService;
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

    void load();
    void disable();

    EquipService equipService();
    ToolbarService toolbarService();
    ListenerService listenerService();
    ItemNBTHandler itemNBTHandler(ItemStack itemStack);

    PlatformMetadata platformMetadata();
}
