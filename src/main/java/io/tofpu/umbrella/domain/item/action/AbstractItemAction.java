package io.tofpu.umbrella.domain.item.action;

import io.tofpu.umbrella.domain.Umbrella;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class AbstractItemAction {
    private final String identifier;

    public AbstractItemAction(final String identifier) {
        this.identifier = identifier;
    }

    public abstract void trigger(final Umbrella umbrella,
            final PlayerInteractEvent event);

    public String getIdentifier() {
        return identifier;
    }
}
