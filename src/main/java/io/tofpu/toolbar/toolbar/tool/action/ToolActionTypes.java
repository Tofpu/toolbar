package io.tofpu.toolbar.toolbar.tool.action;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashSet;
import java.util.Set;

public class ToolActionTypes {
    private static final Set<Class<? extends Event>> REGISTERED_EVENTS = new HashSet<>();

    static {
        REGISTERED_EVENTS.add(PlayerInteractEvent.class);
        REGISTERED_EVENTS.add(PlayerInteractEntityEvent.class);
    }

    public static boolean isListening(Class<? extends Event> eventClass) {
        return REGISTERED_EVENTS.contains(eventClass);
    }

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
