package io.github.tofpu;

import io.tofpu.toolbar.listener.ToolEventListener;
import io.tofpu.toolbar.toolbar.Toolbar;
import io.tofpu.toolbar.toolbar.ToolbarService;
import io.tofpu.toolbar.toolbar.tool.Tool;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

class EventToolActivator implements ToolEventListener.ToolActivator<Event> {
    private static final Logger log = LoggerFactory.getLogger(EventToolActivator.class);
    private final ToolbarService toolbarService;

    public EventToolActivator(ToolbarService toolbarService) {
        this.toolbarService = toolbarService;
    }

    @Override
    public void activate(Event event, ItemStack item) {
        if (item == null) {
            return;
        }

        String toolbarId = ToolNBTUtil.getToolbarIdBy(item);
        log.debug("Toolbar ID: {}", toolbarId);

        final Toolbar toolbar = toolbarService.findToolbarBy(toolbarId);
        if (toolbar == null) {
            log.debug("No toolbar found for toolbar ID: {}", toolbarId);
            return;
        }

        String itemId = ToolNBTUtil.getToolIdBy(item);
        final Optional<Tool> toolOptional = toolbar.tool(itemId);
        if (!toolOptional.isPresent()) {
            log.debug("No tool found for item: {}", item);
            return;
        }

        Tool tool = toolOptional.get();
        log.debug("Triggering tool action for tool item: {}", item);

        boolean triggered = tool.trigger(toolbar, event);
        log.debug("Tool action triggered successfully? {}", triggered);
        if (triggered && event instanceof Cancellable) {
            ((Cancellable) event).setCancelled(false);
        }
    }
}
