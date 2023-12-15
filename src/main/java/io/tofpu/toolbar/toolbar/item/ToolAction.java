package io.tofpu.toolbar.toolbar.item;

import io.tofpu.toolbar.toolbar.Toolbar;
import org.bukkit.event.player.PlayerInteractEvent;

@FunctionalInterface
public interface ToolAction {
    void trigger(final Toolbar toolbar, final PlayerInteractEvent event);
}
