package com.tofpu.toolbar.core.listener.impl;

import com.tofpu.toolbar.api.listener.ListenerWrapper;
import com.tofpu.toolbar.api.listener.ToolEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import static com.tofpu.toolbar.core.util.EventListenerUtil.isNotMainHand;

@ListenerWrapper(type = PlayerInteractEntityEvent.class)
public class PlayerInteractEntityEventListener implements ToolEventListener<PlayerInteractEntityEvent> {

    @Override
    public void onTrigger(PlayerInteractEntityEvent event, ToolActivator<Event> toolActivator) {
        if (isNotMainHand(event.getHand())) {
            return;
        }
        //noinspection deprecation
        toolActivator.activate(event, event.getPlayer().getItemInHand());
    }

    @Override
    public Class<PlayerInteractEntityEvent> type() {
        return PlayerInteractEntityEvent.class;
    }
}
