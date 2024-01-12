package io.tofpu.toolbar.toolbar.tool.action;

import io.tofpu.toolbar.toolbar.GenericToolbar;
import org.bukkit.event.Event;

@FunctionalInterface
public interface ToolAction<E extends Event> extends ToolActionHandle<E> {
    static ToolAction<?> nothing() {
        return (toolbar, event) -> {};
    }

    default void call(final GenericToolbar<?> owner, final Event event) {
        if (!isCompatible(event)) {
            throw new IllegalStateException(String.format("%s event is not compatible with %s event", event.getClass(), type()));
        }
        handle(owner, (E) event);
    }

    default boolean isCompatible(Event target) {
        return type().isInstance(target);
    }

    default Class<E> type() {
        throw new UnsupportedOperationException();
    }
}
