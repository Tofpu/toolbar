package io.tofpu.umbrella.domain.item.action;

import io.tofpu.umbrella.domain.Umbrella;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class AbstractItemAction {
    public abstract void trigger(final Umbrella umbrella,
            final PlayerInteractEvent event);
}
