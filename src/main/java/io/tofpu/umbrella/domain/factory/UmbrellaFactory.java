package io.tofpu.umbrella.domain.factory;

import io.tofpu.umbrella.domain.Umbrella;
import io.tofpu.umbrella.domain.item.UmbrellaItem;
import io.tofpu.umbrella.domain.registry.UmbrellaRegistry;

import java.util.Collection;

public final class UmbrellaFactory {
    private final UmbrellaRegistry umbrellaRegistry;

    public UmbrellaFactory(final UmbrellaRegistry umbrellaRegistry) {
        this.umbrellaRegistry = umbrellaRegistry;
    }

    public Umbrella create(final String identifier) {
        return new Umbrella(identifier, umbrellaRegistry);
    }

    public Umbrella create(final String identifier,
            final Collection<UmbrellaItem> umbrellaItems) {
        return new Umbrella(identifier, umbrellaItems, umbrellaRegistry);
    }
}
