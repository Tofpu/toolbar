package io.github.tofpu.listener.impl;

import io.github.tofpu.ListenerWrapper;
import io.tofpu.toolbar.listener.ToolEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import static io.github.tofpu.util.EventListenerUtil.isNotMainHand;

@ListenerWrapper(type = PlayerInteractEvent.class)
public class PlayerInteractEventListener implements ToolEventListener<PlayerInteractEvent> {

    @Override
    public void onTrigger(PlayerInteractEvent event, ToolActivator<Event> toolActivator) {
        if (isNotMainHand(event.getHand())) {
            return;
        }
        toolActivator.activate(event, event.getItem());
    }

    @Override
    public Class<PlayerInteractEvent> type() {
        return PlayerInteractEvent.class;
    }
}
