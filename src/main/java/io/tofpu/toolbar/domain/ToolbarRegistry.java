package io.tofpu.toolbar.domain;

import io.tofpu.toolbar.domain.Toolbar;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ToolbarRegistry {
    private final Map<String, Toolbar> umbrellaMap;
    private final Map<UUID, Toolbar> playerMap;

    public ToolbarRegistry() {
        this.umbrellaMap = new HashMap<>();
        this.playerMap = new HashMap<>();
    }

    public void register(final Toolbar toolbar) {
        this.umbrellaMap.put(toolbar.getIdentifier(), toolbar);
    }

    public void register(final UUID playerUid, final Toolbar toolbar) {
        this.playerMap.put(playerUid, toolbar);
    }

    public void invalidate(final UUID playerUid) {
        this.playerMap.remove(playerUid);
    }

    public boolean isInUmbrella(final UUID key) {
        return playerMap.containsKey(key);
    }

    public Toolbar findUmbrellaBy(final String identifier) {
        return umbrellaMap.get(identifier);
    }

    public Toolbar findPlayerUmbrella(final UUID key) {
        return playerMap.get(key);
    }

    public Map<UUID, Toolbar> getActivatedPlayers() {
        return Collections.unmodifiableMap(this.playerMap);
    }

    public void clearPlayerMap() {
        this.playerMap.clear();
    }
}
