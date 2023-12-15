package io.tofpu.toolbar.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ToolbarRegistry {
    private final Map<String, Toolbar> toolbarMap;
    private final Map<UUID, Toolbar> playerMap;

    public ToolbarRegistry() {
        this.toolbarMap = new HashMap<>();
        this.playerMap = new HashMap<>();
    }

    public void register(final Toolbar toolbar) {
        this.toolbarMap.put(toolbar.getIdentifier(), toolbar);
    }

    public void register(final UUID playerUid, final Toolbar toolbar) {
        this.playerMap.put(playerUid, toolbar);
    }

    public void invalidate(final UUID playerUid) {
        this.playerMap.remove(playerUid);
    }

    public boolean hasEquippedToolbar(final UUID key) {
        return playerMap.containsKey(key);
    }

    public Toolbar findToolbarBy(final String identifier) {
        return toolbarMap.get(identifier);
    }

    public Toolbar findPlayerToolbar(final UUID key) {
        return playerMap.get(key);
    }

    public Map<UUID, Toolbar> getActivatedPlayers() {
        return Collections.unmodifiableMap(this.playerMap);
    }

    public void clearPlayerMap() {
        this.playerMap.clear();
    }
}
