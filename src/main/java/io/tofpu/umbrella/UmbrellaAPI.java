package io.tofpu.umbrella;

import io.tofpu.umbrella.domain.nbt.BukkitNBTHandler;
import io.tofpu.umbrella.domain.nbt.ItemNBTHandler;
import io.tofpu.umbrella.domain.nbt.ModernPDCNBTHandler;
import io.tofpu.umbrella.domain.service.UmbrellaService;
import io.tofpu.umbrella.listener.ConnectionListener;
import io.tofpu.umbrella.listener.ProtectionListener;
import io.tofpu.umbrella.listener.interaction.BukkitInteractionListener;
import io.tofpu.umbrella.listener.interaction.PacketInteractionListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Function;

public class UmbrellaAPI {
    private static UmbrellaAPI umbrellaAPI;

    private final Function<ItemStack, ItemNBTHandler> itemNBTHandlerFunction;
    private final UmbrellaService umbrellaService;
    private final JavaPlugin plugin;
    private final boolean modernVersion;

    private final InteractionListenerType interactionListenerType;

    public static UmbrellaAPI getInstance() {
        return UmbrellaAPI.umbrellaAPI;
    }

    static ItemNBTHandler determineSuitableNBTHandler(JavaPlugin plugin, ItemStack itemStack) {
        final String version = plugin.getServer()
                .getBukkitVersion()
                .split("-")[0];
        final String[] versionArgs =
                version.split("\\.");

        final int minor = Integer.parseInt(versionArgs[1]);
        int patch = 0;
        if (versionArgs.length > 2) {
            patch = Integer.parseInt(versionArgs[2]);
        }

        // PersistentDataContainer (shorten to pdc) was introduced in Spigot 1.14.1 release,
        // providing developers with a native way to write custom data to objects. This means that
        // we no longer have to consistently update our third-party NBT dependency to support the
        // latest versions of Minecraft
        if (minor == 14 && patch > 1 || minor > 14) {
            return new ModernPDCNBTHandler(itemStack);
        }
        return new BukkitNBTHandler(itemStack);
    }

    public UmbrellaAPI(final JavaPlugin plugin) {
        this(plugin, InteractionListenerType.BUKKIT);
    }

    public UmbrellaAPI(final JavaPlugin plugin, final InteractionListenerType interactionListenerType) {
        this.itemNBTHandlerFunction = itemStack -> determineSuitableNBTHandler(plugin, itemStack);
        this.interactionListenerType = interactionListenerType;
        this.umbrellaService = new UmbrellaService();
        this.plugin = plugin;

        final String[] versionArgs = plugin.getServer()
                .getBukkitVersion()
                .split("-")[0].split("\\.");

        final String formattedVersion = versionArgs[0] + "." + versionArgs[1];

        final double version = Double.parseDouble(formattedVersion);
        this.modernVersion = version >= 1.9;
    }

    public void enable() {
        UmbrellaAPI.umbrellaAPI = this;

        new ConnectionListener().registerSelf(plugin);
        new ProtectionListener(umbrellaService).registerSelf(plugin);

//        InteractionListenerType selectedInteractionListenerType = determineSuitableInteractionListenerType();
        if (interactionListenerType == InteractionListenerType.BUKKIT) {
            plugin.getLogger().info("Registering bukkit-based block interaction listener");
            new BukkitInteractionListener(plugin, umbrellaService);
        } else if (interactionListenerType == InteractionListenerType.PACKET_EVENTS) {
            if (!plugin.getServer().getPluginManager().isPluginEnabled("PacketEvents")) {
                throw new IllegalStateException("PacketEvents dependency is required to load packet-based block interaction listener!");
            }
            plugin.getLogger().info("Registering packet-based block interaction listener");
            new PacketInteractionListener(plugin, umbrellaService).registerSelf();
        }
    }

//    private InteractionListenerType determineSuitableInteractionListenerType() {
//        InteractionListenerType selectedInteractionListenerType = this.interactionListenerType;
//        if (selectedInteractionListenerType == InteractionListenerType.PACKET_EVENTS) {
//            if (!plugin.getServer().getPluginManager().isPluginEnabled("PacketEvents")) {
//                plugin.getLogger().info("No PacketEvents instance found, reverting back to bukkit-based block interaction listener");
//                selectedInteractionListenerType = InteractionListenerType.BUKKIT;
//            }
//        }
//        return selectedInteractionListenerType;
//    }

    public void disable() {
        // nothing to disable
        UmbrellaAPI.umbrellaAPI = null;

        getUmbrellaService()
                .getUmbrellaHandler()
                .inactivateAll();
    }

    public ItemNBTHandler handleItemNBT(ItemStack itemStack) {
        return itemNBTHandlerFunction.apply(itemStack);
    }

    public boolean isInModernVersion() {
        return modernVersion;
    }

    public UmbrellaService getUmbrellaService() {
        return umbrellaService;
    }

    public InteractionListenerType interactionListenerType() {
        return interactionListenerType;
    }
}
