package io.tofpu.toolbar.listener;

import io.tofpu.toolbar.ToolNBTUtil;
import io.tofpu.toolbar.ToolbarAPI;
import io.tofpu.toolbar.toolbar.GenericToolbar;
import io.tofpu.toolbar.toolbar.tool.Tool;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public abstract class ListenerAdapter<E extends Event> {
    protected final ToolbarAPI api;

    public ListenerAdapter(ToolbarAPI api) {
        this.api = api;
    }

    protected abstract void handle(E event);

    protected boolean isNotMainHand(EquipmentSlot hand) {
        return ToolbarAPI.getInstance()
                .isInModernVersion() && hand != EquipmentSlot.HAND;
    }

    protected void trigger(Event event, ItemStack clickedItem) {
        if (clickedItem == null) return;

        final GenericToolbar<?> toolbar = api.getToolbarService().findToolbarBy(ToolNBTUtil.getToolbarIdBy(clickedItem));
        if (toolbar == null) return;

        final Tool tool = toolbar.findItemBy(ToolNBTUtil.getToolIdBy(clickedItem));
        if (tool == null) return;

        if (event instanceof Cancellable) {
            ((Cancellable) event).setCancelled(true);
        }
        tool.trigger(toolbar, event);
    }

    public abstract Class<E> type();
}
