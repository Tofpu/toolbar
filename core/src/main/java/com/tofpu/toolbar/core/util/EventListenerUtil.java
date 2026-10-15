package com.tofpu.toolbar.core.util;

import com.tofpu.toolbar.api.ToolbarAPI;
import org.bukkit.inventory.EquipmentSlot;

public class EventListenerUtil {
    private EventListenerUtil() {
        /* This utility class should not be instantiated */
    }

    public static boolean isNotMainHand(EquipmentSlot hand) {
        return ToolbarAPI.getInstance().platformMetadata().supportsDualWielding() && hand != EquipmentSlot.HAND;
    }
}
