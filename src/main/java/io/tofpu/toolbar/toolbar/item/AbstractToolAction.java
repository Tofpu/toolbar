package io.tofpu.toolbar.toolbar.item;

import io.tofpu.toolbar.toolbar.Toolbar;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class AbstractToolAction {
    public abstract void trigger(final Toolbar toolbar,
            final PlayerInteractEvent event);
}
