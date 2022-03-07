package io.tofpu.umbrella.domain.handler;

import io.tofpu.umbrella.exception.UmbrellaNotFoundException;
import io.tofpu.umbrella.domain.Umbrella;
import io.tofpu.umbrella.domain.registry.UmbrellaRegistry;
import org.bukkit.entity.Player;

public final class UmbrellaHandler {
    private final UmbrellaRegistry umbrellaRegistry;

    public UmbrellaHandler(final UmbrellaRegistry umbrellaRegistry) {
        this.umbrellaRegistry = umbrellaRegistry;
    }

    public void activate(final String identifier, final Player target) throws UmbrellaNotFoundException {
        final Umbrella umbrella = this.umbrellaRegistry.findUmbrellaBy(identifier);

        if (umbrella == null) {
            throw new UmbrellaNotFoundException(identifier);
        }

        umbrella.activate(target);
    }

    public void inactivate(final Player target) {
        final Umbrella umbrella = this.umbrellaRegistry.findPlayerUmbrella(target.getUniqueId());

        // nothing to inactivate
        if (umbrella == null) {
            return;
        }

        umbrella.inactivate(target);
    }
}
