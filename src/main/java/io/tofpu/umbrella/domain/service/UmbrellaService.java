package io.tofpu.umbrella.domain.service;

import io.tofpu.umbrella.domain.Umbrella;
import io.tofpu.umbrella.domain.factory.UmbrellaFactory;
import io.tofpu.umbrella.domain.handler.UmbrellaHandler;
import io.tofpu.umbrella.domain.item.UmbrellaItem;
import io.tofpu.umbrella.domain.registry.UmbrellaRegistry;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

public final class UmbrellaService {
    private final UmbrellaRegistry umbrellaRegistry;
    private final UmbrellaHandler umbrellaHandler;
    private final UmbrellaFactory umbrellaFactory;

    public UmbrellaService() {
        this.umbrellaRegistry = new UmbrellaRegistry();
        this.umbrellaHandler = new UmbrellaHandler(umbrellaRegistry);
        this.umbrellaFactory = new UmbrellaFactory(umbrellaRegistry);
    }

    public Umbrella create(final String identifier) {
        return getUmbrellaFactory().create(identifier);
    }

    private UmbrellaFactory getUmbrellaFactory() {
        return umbrellaFactory;
    }

    public Umbrella create(final String identifier, final Collection<UmbrellaItem> umbrellaItems) {
        return getUmbrellaFactory().create(identifier, umbrellaItems);
    }

    public void register(final Umbrella umbrella) {
        getUmbrellaRegistry().register(umbrella);
    }

    private UmbrellaRegistry getUmbrellaRegistry() {
        return umbrellaRegistry;
    }

    public void register(final UUID playerUid, final Umbrella umbrella) {
        getUmbrellaRegistry().register(playerUid, umbrella);
    }

    public void inactivate(final Player player) {
        getUmbrellaHandler().inactivate(player);
    }

    private UmbrellaHandler getUmbrellaHandler() {
        return umbrellaHandler;
    }

    public void invalidate(final UUID playerUid) {
        getUmbrellaRegistry().invalidate(playerUid);
    }

    public boolean isInUmbrella(final UUID key) {
        return getUmbrellaRegistry().isInUmbrella(key);
    }

    public Umbrella findUmbrellaBy(final String identifier) {
        return getUmbrellaRegistry().findUmbrellaBy(identifier);
    }
}
