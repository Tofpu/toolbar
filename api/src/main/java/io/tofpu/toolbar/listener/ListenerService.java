package io.tofpu.toolbar.listener;

import org.bukkit.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ListenerService {
    private static final Logger log = LoggerFactory.getLogger(ListenerService.class);
    private final Map<Class<? extends Event>, ListenerAdapter<?>> listenerAdapterMap = new HashMap<>();

    public ListenerService() {}

    public <E extends Event> void registerListener(ListenerAdapter<E> adapter) {
        listenerAdapterMap.put(adapter.type(), adapter);
    }

    @SuppressWarnings("unused")
    public <E extends Event> void callEventIfPresent(E event) {
        log.debug("ListenerService#callEventIfPresent was called");
        ListenerAdapter<E> listenerAdapter = (ListenerAdapter<E>) this.listenerAdapterMap.get(event.getClass());
        if (listenerAdapter != null) {
            log.debug("Found listener adapter for event: {}", event.getClass().getSimpleName());
            log.debug("Calling handle method on listener adapter: {}", listenerAdapter.getClass().getSimpleName());
            listenerAdapter.handle(event);
        }
    }
}
