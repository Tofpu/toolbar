package io.tofpu.toolbar.listener;

import org.bukkit.event.Event;

public interface ListenerService {
    <E extends Event> void registerListener(ToolEventListener<E> listener);

    @SuppressWarnings("unused")
    <E extends Event> void callEventIfPresent(E event);
}
