package io.tofpu.toolbar.listener;

import io.tofpu.toolbar.ToolbarAPI;
import io.tofpu.toolbar.listener.impl.PlayerInteractEntityEventAdapter;
import io.tofpu.toolbar.listener.impl.PlayerInteractEventAdapter;
import org.bukkit.event.Event;

import java.util.*;
import java.util.function.Supplier;

public class ListenerRegistry {
    static final Set<Supplier<ListenerAdapter<? extends Event>>> UNLOADED_LISTENER_ADAPTERS = new HashSet<>();
    static final Map<Class<? extends Event>, ListenerAdapter<? extends Event>> LOADED_LISTENER_ADAPTERS = new HashMap<>();

    static {
        UNLOADED_LISTENER_ADAPTERS.add(() -> new PlayerInteractEventAdapter(ToolbarAPI.getInstance()));
        UNLOADED_LISTENER_ADAPTERS.add(() -> new PlayerInteractEntityEventAdapter(ToolbarAPI.getInstance()));
    }

    public static boolean isListening(Class<? extends Event> eventType) {
        return LOADED_LISTENER_ADAPTERS.containsKey(eventType);
    }

    public synchronized static Collection<ListenerAdapter<? extends Event>> loadAndCopy() {
        UNLOADED_LISTENER_ADAPTERS.forEach(adapterSupplier -> {
            ListenerAdapter<? extends Event> listenerAdapter = adapterSupplier.get();
            LOADED_LISTENER_ADAPTERS.put(listenerAdapter.type(), listenerAdapter);
        });
        return LOADED_LISTENER_ADAPTERS.values();
    }
}
