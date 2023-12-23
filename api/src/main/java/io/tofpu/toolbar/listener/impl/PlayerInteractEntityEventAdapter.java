package io.tofpu.toolbar.listener.impl;

import io.tofpu.toolbar.ToolbarAPI;
import io.tofpu.toolbar.listener.ListenerAdapter;
import io.github.tofpu.ListenerWrapper;
import org.bukkit.event.player.PlayerInteractEntityEvent;

@ListenerWrapper(type = PlayerInteractEntityEvent.class)
public class PlayerInteractEntityEventAdapter extends ListenerAdapter<PlayerInteractEntityEvent> {
    public PlayerInteractEntityEventAdapter(ToolbarAPI api) {
        super(api);
    }

    @Override
    protected void handle(PlayerInteractEntityEvent event) {
        if (isNotMainHand(event.getHand())) {
            return;
        }
        //noinspection deprecation
        trigger(event, event.getPlayer().getItemInHand());
    }

    @Override
    public Class<PlayerInteractEntityEvent> type() {
        return PlayerInteractEntityEvent.class;
    }
}
