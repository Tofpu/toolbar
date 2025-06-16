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
        if (clickedItem == null) {
            return;
        }

        String toolbarId = ToolNBTUtil.getToolbarIdBy(clickedItem);
        api.logger().debug("Toolbar ID: " + toolbarId);
        final GenericToolbar<?> toolbar = api.toolbarRegistrationService().findToolbarBy(toolbarId);
        if (toolbar == null) {
            api.logger().debug("No toolbar found for toolbar ID: " + toolbarId);
            return;
        }

        String itemId = ToolNBTUtil.getToolIdBy(clickedItem);
        final Tool tool = toolbar.findItemBy(itemId);
        if (tool == null) {
            api.logger().debug("No tool found for item: " + clickedItem);
            return;
        }

        api.logger().debug("Triggering tool action for tool item: " + clickedItem);
        boolean triggered = tool.trigger(toolbar, event);
        api.logger().debug("Tool action triggered successfully? " + triggered);
        if (triggered && event instanceof Cancellable) {
            ((Cancellable) event).setCancelled(false);
        }
    }

    public abstract Class<E> type();
}
