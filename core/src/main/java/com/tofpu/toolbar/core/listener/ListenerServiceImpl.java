package com.tofpu.toolbar.core.listener;

import com.tofpu.toolbar.api.listener.ListenerService;
import com.tofpu.toolbar.api.listener.ToolEventListener;
import org.bukkit.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ListenerServiceImpl implements ListenerService {
    private static final Logger log = LoggerFactory.getLogger(ListenerServiceImpl.class);
    private final Map<Class<? extends Event>, ToolEventListener<? extends Event>> listenerMap = new HashMap<>();
    private final ToolEventListener.ToolActivator<Event> toolActivator;

    public ListenerServiceImpl(ToolEventListener.ToolActivator<Event> toolActivator) {
        this.toolActivator = toolActivator;
    }

    @Override
    public <E extends Event> void registerListener(ToolEventListener<E> listener) {
        listenerMap.put(listener.type(), listener);
    }

    @SuppressWarnings("unused")
    @Override
    public <E extends Event> void callEventIfPresent(E event) {
        log.debug("ListenerService#callEventIfPresent was called");
        ToolEventListener<E> eventListener = (ToolEventListener<E>) this.listenerMap.get(event.getClass());
        if (eventListener != null) {
            log.debug("Found listener adapter for event: {}", event.getClass().getSimpleName());
            log.debug("Calling handle method on listener adapter: {}", eventListener.getClass().getSimpleName());
            eventListener.onTrigger(event, toolActivator);
        }
    }
}
