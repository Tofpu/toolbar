package com.tofpu.toolbar.core.listener.impl;

import com.tofpu.toolbar.api.listener.ListenerWrapper;
import com.tofpu.toolbar.api.listener.ToolEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import static com.tofpu.toolbar.core.util.EventListenerUtil.isNotMainHand;

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
