package io.tofpu.toolbar.domain;

import io.tofpu.toolbar.exception.ToolbarNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public final class ToolbarHandler {
    private final ToolbarRegistry toolbarRegistry;

    public ToolbarHandler(final ToolbarRegistry toolbarRegistry) {
        this.toolbarRegistry = toolbarRegistry;
    }

    public void activate(final String identifier, final Player target) throws ToolbarNotFoundException {
        final Toolbar toolbar = this.toolbarRegistry.findToolbarBy(identifier);

        if (toolbar == null) {
            throw new ToolbarNotFoundException(identifier);
        }

        toolbar.activate(target);
    }

    public void inactivate(final Player target) {
        final Toolbar toolbar = this.toolbarRegistry.findPlayerToolbar(target.getUniqueId());

        // nothing to inactivate
        if (toolbar == null) {
            return;
        }

        toolbar.inactivate(target);
    }

    public void inactivateAll() {
        final Map<UUID, Toolbar> activatedPlayersMap = toolbarRegistry.getActivatedPlayers();
        for (final Map.Entry<UUID, Toolbar> entry :
                activatedPlayersMap.entrySet()) {
            final Player player = Bukkit.getPlayer(entry.getKey());
            if (player == null) {
                continue;
            }

            entry.getValue().inactivate(player);
        }
        toolbarRegistry.clearPlayerMap();
    }
}
