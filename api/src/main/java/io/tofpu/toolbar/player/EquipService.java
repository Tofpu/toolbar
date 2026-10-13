package io.tofpu.toolbar.player;

import io.tofpu.toolbar.toolbar.Toolbar;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface EquipService {
    boolean equip(Player player, Toolbar toolbar);

    default boolean unequip(@NotNull Player player) {
        return unequip(player.getUniqueId());
    }

    boolean unequip(@NotNull UUID playerId);

    void unequipAll();

    boolean isEquippingToolbar(UUID playerId);
}
