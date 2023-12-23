package io.tofpu.toolbar.toolbar.tool.action;

import io.tofpu.toolbar.ToolbarAPI;
import io.tofpu.toolbar.listener.ListenerRegistry;
import io.tofpu.toolbar.toolbar.GenericToolbar;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class ToolActionUtil {
    @NotNull
    public static <T extends Event> ToolAction<T> listenFor(Class<T> clazz, ToolAction<T> action) {
        if (!ListenerRegistry.isListening(clazz)) {
            ToolbarAPI.getInstance().notListeningWarn(clazz);
        }

        return new ToolAction<T>() {
            @Override
            public void handle(GenericToolbar<?> owner, T event) {
                action.handle(owner, event);
            }

            @Override
            public Class<T> type() {
                return clazz;
            }
        };
    }
}
