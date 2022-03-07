package io.tofpu.umbrella.domain.item.action;

import org.bukkit.event.player.PlayerInteractEvent;

public abstract class AbstractItemAction {
    private final String identifier;

    public AbstractItemAction(final String identifier) {
        this.identifier = identifier;
    }

    public abstract void trigger(final PlayerInteractEvent event);

    public String getIdentifier() {
        return identifier;
    }
}
