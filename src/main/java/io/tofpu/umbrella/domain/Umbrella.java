package io.tofpu.umbrella.domain;

import io.tofpu.umbrella.domain.item.UmbrellaItem;
import io.tofpu.umbrella.domain.registry.UmbrellaRegistry;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public class Umbrella {
    private final String identifier;
    private final List<UmbrellaItem> umbrellaItems;
    private final UmbrellaRegistry registry;

    public Umbrella(final String identifier, final UmbrellaRegistry registry) {
        // preventing class initialization outside the scoped project
        this(identifier, new ArrayList<>(), registry);
    }

    public Umbrella(final String identifier, final Collection<UmbrellaItem> umbrellaItems, final UmbrellaRegistry registry) {
        // preventing class initialization outside the scoped project
        this.identifier = identifier;
        this.umbrellaItems = new ArrayList<>(umbrellaItems);
        this.registry = registry;
    }

    public boolean activate(final Player target) {
        if (isActivated(target.getUniqueId())) {
            return false;
        }
        registry.register(target.getUniqueId(), this);

        applyItems(target);
        // send message here

        return true;
    }

    public boolean inactivate(final Player target) {
        if (target == null || !isActivated(target.getUniqueId())) {
            return false;
        }
        registry.invalidate(target.getUniqueId());

        clearItems(target);
        // send message here

        return true;
    }

    private void applyItems(final Player target) {
        final PlayerInventory inventory = target.getInventory();
        inventory.clear();

        for (final UmbrellaItem umbrellaItem : umbrellaItems) {
            inventory.addItem(umbrellaItem.getCopyOfItem());
        }
    }

    private void clearItems(final Player target) {
        if (!target.isOnline()) {
            return;
        }

        final PlayerInventory inventory = target.getInventory();
        inventory.clear();

        // anything else?
    }

    public boolean isActivated(final UUID playerUid) {
        return registry.isInUmbrella(playerUid);
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<UmbrellaItem> getCopyOfItems() {
        return Collections.unmodifiableList(umbrellaItems);
    }
}
