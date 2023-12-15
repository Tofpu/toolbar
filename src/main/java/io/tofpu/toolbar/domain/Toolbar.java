package io.tofpu.toolbar.domain;

import io.tofpu.toolbar.domain.item.Tool;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public class Toolbar {
    private final String identifier;
    private final Map<String, Tool> itemMap;
    private final ToolbarRegistry registry;

    public Toolbar(final ToolbarRegistry registry, final String identifier) {
        // preventing class initialization outside the scoped project
        this(registry, identifier, new LinkedList<>());
    }

    public Toolbar(final ToolbarRegistry registry, final String identifier, final Collection<Tool> tools) {
        // preventing class initialization outside the scoped project
        this.identifier = identifier;
        this.itemMap = new HashMap<>();

        for (final Tool tool : tools) {
            this.itemMap.put(tool.getItemIdentifier(), tool);
        }

        this.registry = registry;
    }

    public void addItem(final Tool... tools) {
        for (final Tool tool : tools) {
            this.itemMap.put(tool.getItemIdentifier(), tool);
        }
    }

    public Tool findItemBy(final String identifier) {
        return itemMap.get(identifier);
    }

    public boolean activate(final Player target) {
        if (isActivated(target.getUniqueId())) {
            return false;
        }
        registry.register(target.getUniqueId(), this);

        applyItems(target);

        return true;
    }

    public boolean inactivate(final Player target) {
        if (target == null || !isActivated(target.getUniqueId())) {
            return false;
        }
        registry.invalidate(target.getUniqueId());

        clearItems(target);

        return true;
    }

    private void applyItems(final Player target) {
        final PlayerInventory inventory = target.getInventory();
        inventory.clear();

        for (final Tool tool : itemMap.values()) {
            if (tool.getInventoryIndex() == -1) {
                inventory.addItem(tool.getCopyOfItem());
                continue;
            }

            inventory.setItem(tool.getInventoryIndex(), tool.getCopyOfItem());
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
        return registry.hasEquippedToolbar(playerUid);
    }

    public String getIdentifier() {
        return identifier;
    }

    public Map<String, Tool> getCopyItemMap() {
        return Collections.unmodifiableMap(this.itemMap);
    }
}
