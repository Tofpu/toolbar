package io.tofpu.toolbar.toolbar.tool.action;

import io.tofpu.toolbar.toolbar.Toolbar;
import org.bukkit.event.Event;

@FunctionalInterface
public interface ToolAction<E extends Event> extends ToolActionHandle<E> {
    static ToolAction<?> nothing() {
        return (toolbar, event) -> {};
    }

    static <T extends Event> ToolAction<T> of(Class<T> clazz, ToolActionHandle<T> action) {
        return new ToolAction<T>() {
            @Override
            public void handle(Toolbar owner, T event) {
                action.handle(owner, event);
            }

            @Override
            public Class<T> type() {
                return clazz;
            }
        };
    }

    default void call(final Toolbar owner, final Event event) {
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
