package com.tofpu.toolbar.api.toolbar.tool.action;

import com.tofpu.toolbar.api.toolbar.Toolbar;
import org.bukkit.event.Event;

@FunctionalInterface
public interface ToolActionHandle<E extends Event> {
    void handle(final Toolbar owner, final E event);
}
