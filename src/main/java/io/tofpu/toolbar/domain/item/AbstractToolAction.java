package io.tofpu.toolbar.domain.item;

import io.tofpu.toolbar.domain.Toolbar;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class AbstractToolAction {
    public abstract void trigger(final Toolbar toolbar,
            final PlayerInteractEvent event);
}
