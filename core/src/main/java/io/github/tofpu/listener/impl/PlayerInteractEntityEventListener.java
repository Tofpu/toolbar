package io.github.tofpu.listener.impl;

import io.github.tofpu.ListenerWrapper;
import io.tofpu.toolbar.listener.ToolEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import static io.github.tofpu.util.EventListenerUtil.isNotMainHand;

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
