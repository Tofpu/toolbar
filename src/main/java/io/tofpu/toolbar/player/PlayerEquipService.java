package io.tofpu.toolbar.player;

import io.tofpu.toolbar.toolbar.Toolbar;
import io.tofpu.toolbar.toolbar.ToolbarRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public final class PlayerEquipService {
    private final ToolbarRegistry toolbarRegistry;
    private final Map<UUID, Toolbar> playerMap;

    public PlayerEquipService(ToolbarRegistry toolbarRegistry) {
        this(toolbarRegistry, new HashMap<>());
    }

    public PlayerEquipService(ToolbarRegistry toolbarRegistry, Map<UUID, Toolbar> playerMap) {
        this.toolbarRegistry = toolbarRegistry;
        this.playerMap = new HashMap<>(playerMap);
    }

    public boolean equip(Player player, String toolbarId) {
        Toolbar toolbar = toolbarRegistry.findToolbarBy(toolbarId);
        return equip(player, toolbar);
    }

    public boolean equip(Player player, Toolbar toolbar) {
        Objects.requireNonNull(player, "Player must be provided.");
        Objects.requireNonNull(toolbar, "Toolbar must be provided.");

        UUID playerId = player.getUniqueId();
        if (isEquippingToolbar(playerId)) {
            return false;
        }

        try {
            equipToolbar(player, toolbar);
            this.playerMap.put(playerId, toolbar);
        } catch (RuntimeException e) {
            throw new RuntimeException("Ran into an error while equipping a toolbar to a player", e);
        }
        return true;
    }

    public boolean unequip(Player player) {
        Objects.requireNonNull(player, "Player must be provided.");

        UUID playerId = player.getUniqueId();
        if (!isEquippingToolbar(playerId)) {
            return false;
        }
        this.playerMap.remove(playerId);
        player.getInventory().clear();
        return true;
    }

    public void unequipAll() {
        Iterator<Map.Entry<UUID, Toolbar>> iterator = this.playerMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, Toolbar> next = iterator.next();

            UUID playerId = next.getKey();
            Player player = Bukkit.getPlayer(playerId);
            if (player == null) {
                iterator.remove();
            }
            unequip(player);
        }
    }

    private void equipToolbar(Player player, Toolbar toolbar) {
        PlayerInventory inventory = player.getInventory();
        toolbar.getItemStacks().forEach((integer, itemStack) -> {
            if (integer == -1) {
                inventory.addItem(itemStack);
                return;
            }
            inventory.setItem(integer, itemStack);
        });
    }

    public boolean isEquippingToolbar(UUID playerId) {
        return this.playerMap.containsKey(playerId);
    }
}
