package io.tofpu.toolbar.toolbar.tool;

import io.tofpu.toolbar.toolbar.Toolbar;
import org.bukkit.event.player.PlayerInteractEvent;

@FunctionalInterface
public interface ToolAction {
    static ToolAction empty() {
        return (toolbar, event) -> {};
    }

    void trigger(final Toolbar toolbar, final PlayerInteractEvent event);
}
