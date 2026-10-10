package io.tofpu.toolbar.listener;

import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.Map;

public class ListenerService {
    private final Map<Class<? extends Event>, ListenerAdapter<?>> listenerAdapterMap = new HashMap<>();

    public ListenerService() {}

    public <E extends Event> void registerListener(ListenerAdapter<E> adapter) {
        listenerAdapterMap.put(adapter.type(), adapter);
    }

    @SuppressWarnings("unused")
    public <E extends Event> void callEventIfPresent(E event) {
        System.out.println("ListenerService#callEventIfPresent was called");
        ListenerAdapter<E> listenerAdapter = (ListenerAdapter<E>) this.listenerAdapterMap.get(event.getClass());
        if (listenerAdapter != null) {
            System.out.println("Found listener adapter for event: " + event.getClass().getSimpleName());
            System.out.println("Calling handle method on listener adapter: " + listenerAdapter.getClass().getSimpleName());
            listenerAdapter.handle(event);
        }
    }
}
