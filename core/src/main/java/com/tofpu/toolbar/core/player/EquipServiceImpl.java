package com.tofpu.toolbar.core.player;

import com.tofpu.toolbar.api.player.EquipService;
import com.tofpu.toolbar.api.toolbar.Toolbar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class EquipServiceImpl implements EquipService {
    private final Map<UUID, Toolbar> playerMap;

    public EquipServiceImpl() {
        this(new HashMap<>());
    }

    public EquipServiceImpl(Map<UUID, Toolbar> playerMap) {
        this.playerMap = new HashMap<>(playerMap);
    }

    @Override
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

    @Override
    public boolean unequip(@NotNull UUID playerId) {
        if (!isEquippingToolbar(playerId)) {
            return false;
        }

        this.playerMap.remove(playerId);
        Player player = Bukkit.getPlayer(playerId);
        if (player != null) {
            player.getInventory().clear();
        }
        return true;
    }

    @Override
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
        toolbar.itemsWithSlots().forEach((slot, itemStack) -> {
            if (slot.isUndefined()) {
                inventory.addItem(itemStack);
                return;
            }
            inventory.setItem(slot.asIndex(), itemStack);
        });
    }

    @Override
    public boolean isEquippingToolbar(UUID playerId) {
        return this.playerMap.containsKey(playerId);
    }
}
