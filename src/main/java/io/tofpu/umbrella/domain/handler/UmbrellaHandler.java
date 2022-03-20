package io.tofpu.umbrella.domain.handler;

import io.tofpu.umbrella.domain.Umbrella;
import io.tofpu.umbrella.domain.registry.UmbrellaRegistry;
import io.tofpu.umbrella.exception.UmbrellaNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

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

    public void inactivateAll() {
        final Map<UUID, Umbrella> activatedPlayersMap = umbrellaRegistry.getActivatedPlayers();
        for (final Map.Entry<UUID, Umbrella> entry :
                activatedPlayersMap.entrySet()) {
            final Player player = Bukkit.getPlayer(entry.getKey());
            if (player == null) {
                continue;
            }

            entry.getValue().inactivate(player);
        }
        umbrellaRegistry.clearPlayerMap();
    }
}
