package io.tofpu.toolbar.listener.impl;

import io.tofpu.toolbar.ToolbarAPI;
import io.tofpu.toolbar.listener.ListenerAdapter;
import io.github.tofpu.ListenerWrapper;
import org.bukkit.event.player.PlayerInteractEvent;

@ListenerWrapper(type = PlayerInteractEvent.class)
public class PlayerInteractEventAdapter extends ListenerAdapter<PlayerInteractEvent> {
    public PlayerInteractEventAdapter(ToolbarAPI api) {
        super(api);
    }

    @Override
    protected void handle(PlayerInteractEvent event) {
        if (ToolbarAPI.getInstance()
                .isInModernVersion() && isNotMainHand(event.getHand())) {
            return;
        }
        trigger(event, event.getItem());
    }

    @Override
    public Class<PlayerInteractEvent> type() {
        return PlayerInteractEvent.class;
    }
}
