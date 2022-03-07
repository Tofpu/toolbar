package io.tofpu.umbrella.domain.registry;

import io.tofpu.umbrella.domain.Umbrella;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class UmbrellaRegistry {
    private final Map<String, Umbrella> umbrellaMap;
    private final Map<UUID, Umbrella> playerMap;

    public UmbrellaRegistry() {
        this.umbrellaMap = new HashMap<>();
        this.playerMap = new HashMap<>();
    }

    public void register(final Umbrella umbrella) {
        this.umbrellaMap.put(umbrella.getIdentifier(), umbrella);
    }

    public void register(final UUID playerUid, final Umbrella umbrella) {
        this.playerMap.put(playerUid, umbrella);
    }

    public void invalidate(final UUID playerUid) {
        this.playerMap.remove(playerUid);
    }

    public boolean isInUmbrella(final UUID key) {
        return playerMap.containsKey(key);
    }

    public Umbrella findUmbrellaBy(final String identifier) {
        return umbrellaMap.get(identifier);
    }

    public Umbrella findPlayerUmbrella(final UUID key) {
        return playerMap.get(key);
    }
}
