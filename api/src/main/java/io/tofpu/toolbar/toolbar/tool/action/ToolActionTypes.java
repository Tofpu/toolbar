package io.tofpu.toolbar.toolbar.tool.action;

import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ToolActionTypes {
    public interface PlayerInteract extends ToolAction<PlayerInteractEvent> {
        @Override
        default Class<PlayerInteractEvent> type() {
            return PlayerInteractEvent.class;
        }
    }

    public interface PlayerEntityInteract extends ToolAction<PlayerInteractEntityEvent> {
        @Override
        default Class<PlayerInteractEntityEvent> type() {
            return PlayerInteractEntityEvent.class;
        }
    }
}
