package io.tofpu.toolbar;

import io.github.tofpu.GeneratedListener;
import io.tofpu.toolbar.listener.ListenerRegistry;
import io.tofpu.toolbar.listener.ListenerService;
import io.tofpu.toolbar.nbt.BukkitNBTHandler;
import io.tofpu.toolbar.nbt.ItemNBTHandler;
import io.tofpu.toolbar.nbt.ModernPDCNBTHandler;
import io.tofpu.toolbar.player.PlayerEquipService;
import io.tofpu.toolbar.toolbar.ToolbarService;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.function.Function;

public class ToolbarAPI {
    private static ToolbarAPI toolbarAPI;

    private final Function<ItemStack, ItemNBTHandler> itemNBTHandlerFunction;
    private final ToolbarService toolbarService;
    private final PlayerEquipService playerEquipService;
    private final JavaPlugin plugin;
    private final boolean modernVersion;

    private final ListenerService listenerService;

    public ToolbarAPI(final JavaPlugin plugin) {
        this(plugin, itemStack -> {
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
        });
    }

    public ToolbarAPI(final JavaPlugin plugin, final Function<ItemStack, ItemNBTHandler> itemNBTHandlerFunction) {
        this.itemNBTHandlerFunction = itemNBTHandlerFunction;
        this.toolbarService = new ToolbarService();
        this.playerEquipService = new PlayerEquipService();
        this.plugin = plugin;

        final String[] versionArgs = plugin.getServer()
                .getBukkitVersion()
                .split("-")[0].split("\\.");

        final String formattedVersion = versionArgs[0] + "." + versionArgs[1];

        final double version = Double.parseDouble(formattedVersion);
        this.modernVersion = version >= 1.9;
        this.listenerService = new ListenerService();
    }

    public static ToolbarAPI getInstance() {
        return ToolbarAPI.toolbarAPI;
    }

    public void enable() {
        ToolbarAPI.toolbarAPI = this;

        new ToolbarAPIListener(plugin, this);
        ListenerRegistry.loadAndCopy().forEach(listenerService::registerListener);

        registerDynamicListener();
    }

    private void registerDynamicListener() {
        String packageName = this.getClass().getPackage().getName();
        Set<Class<?>> typesAnnotatedWith = new Reflections(packageName).
                getTypesAnnotatedWith(GeneratedListener.class);

        typesAnnotatedWith.forEach(clazz -> {
            try {
                plugin.getServer().getPluginManager().registerEvents((Listener) clazz.getDeclaredConstructor().newInstance(), plugin);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException("Failed to register this dynamically generated listener", e);
            }
        });
    }

    public void disable() {
        // nothing to disable
        playerEquipService.unequipAll();
        ToolbarAPI.toolbarAPI = null;
    }

    public <T extends Event> void notListeningWarn(Class<T> clazz) {
        plugin.getLogger().warning(String.format("Be warned that any tools listening to %s-based event won't be triggered by default.", clazz.getSimpleName()));
    }

    public boolean isInModernVersion() {
        return modernVersion;
    }

    public ItemNBTHandler handleItemNBT(ItemStack itemStack) {
        return itemNBTHandlerFunction.apply(itemStack);
    }

    public PlayerEquipService getPlayerEquipService() {
        return playerEquipService;
    }

    public ToolbarService getToolbarService() {
        return toolbarService;
    }

    public ListenerService listenerService() {
        return listenerService;
    }
}
