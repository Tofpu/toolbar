package com.tofpu.toolbar.core.listener;

import com.tofpu.toolbar.core.listener.impl.PlayerInteractEntityEventListener;
import com.tofpu.toolbar.core.listener.impl.PlayerInteractEventListener;
import com.tofpu.toolbar.api.listener.ToolEventListener;
import org.bukkit.event.Event;

import java.util.*;
import java.util.function.Supplier;

public class ListenerRegistry {
    static final Set<Supplier<ToolEventListener<? extends Event>>> UNLOADED_LISTENER_ADAPTERS = new HashSet<>();
    static final Map<Class<? extends Event>, ToolEventListener<? extends Event>> LOADED_LISTENER_ADAPTERS = new HashMap<>();

    static {
        UNLOADED_LISTENER_ADAPTERS.add(PlayerInteractEventListener::new);
        UNLOADED_LISTENER_ADAPTERS.add(PlayerInteractEntityEventListener::new);
    }

    public static boolean isListening(Class<? extends Event> eventType) {
        return LOADED_LISTENER_ADAPTERS.containsKey(eventType);
    }

    public static synchronized Collection<ToolEventListener<? extends Event>> loadAndCopy() {
        UNLOADED_LISTENER_ADAPTERS.forEach(adapterSupplier -> {
            ToolEventListener<? extends Event> toolEventListener = adapterSupplier.get();
            LOADED_LISTENER_ADAPTERS.put(toolEventListener.type(), toolEventListener);
        });
        return LOADED_LISTENER_ADAPTERS.values();
    }
}
