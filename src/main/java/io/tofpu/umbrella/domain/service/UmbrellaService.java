package io.tofpu.umbrella.domain.service;

import io.tofpu.umbrella.domain.factory.UmbrellaFactory;
import io.tofpu.umbrella.domain.handler.UmbrellaHandler;
import io.tofpu.umbrella.domain.item.factory.UmbrellaItemFactory;
import io.tofpu.umbrella.domain.registry.UmbrellaRegistry;

public final class UmbrellaService {
    private final UmbrellaRegistry umbrellaRegistry;
    private final UmbrellaHandler umbrellaHandler;
    private final UmbrellaFactory umbrellaFactory;
    private final UmbrellaItemFactory umbrellaItemFactory;

    public UmbrellaService() {
        this.umbrellaRegistry = new UmbrellaRegistry();
        this.umbrellaHandler = new UmbrellaHandler(umbrellaRegistry);
        this.umbrellaFactory = new UmbrellaFactory(umbrellaRegistry);
        this.umbrellaItemFactory = new UmbrellaItemFactory();
    }

    public UmbrellaItemFactory getUmbrellaItemFactory() {
        return umbrellaItemFactory;
    }

    public UmbrellaFactory getUmbrellaFactory() {
        return umbrellaFactory;
    }

    public UmbrellaRegistry getUmbrellaRegistry() {
        return umbrellaRegistry;
    }

    public UmbrellaHandler getUmbrellaHandler() {
        return umbrellaHandler;
    }
}
