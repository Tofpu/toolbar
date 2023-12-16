package io.tofpu.toolbar.toolbar.tool;

import io.tofpu.toolbar.toolbar.GenericToolbar;
import org.bukkit.event.player.PlayerInteractEvent;

@FunctionalInterface
public interface ToolAction {
    static ToolAction empty() {
        return (toolbar, event) -> {};
    }

    void trigger(final GenericToolbar toolbar, final PlayerInteractEvent event);
}
