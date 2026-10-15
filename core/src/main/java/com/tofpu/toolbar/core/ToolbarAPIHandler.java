package com.tofpu.toolbar.core;

import com.tofpu.toolbar.annotationprocessing.GeneratedListener;
import com.tofpu.toolbar.core.listener.ListenerRegistry;
import com.tofpu.toolbar.core.listener.ListenerServiceImpl;
import com.tofpu.toolbar.core.nbt.BukkitNBTHandler;
import com.tofpu.toolbar.core.toolbar.ToolbarRegistry;
import com.tofpu.toolbar.core.toolbar.tool.ToolProcessorImpl;
import com.tofpu.toolbar.api.nbt.ItemNBTHandler;
import com.tofpu.toolbar.core.nbt.ModernPDCNBTHandler;
import com.tofpu.toolbar.core.player.EquipServiceImpl;
import com.tofpu.toolbar.annotationprocessing.processor.ListenerProcessor;
import com.tofpu.toolbar.core.toolbar.ToolbarServiceImpl;
import com.tofpu.toolbar.api.ToolbarAPI;
import com.tofpu.toolbar.api.listener.ListenerService;
import com.tofpu.toolbar.api.player.EquipService;
import com.tofpu.toolbar.api.toolbar.ToolbarService;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.function.Function;

class ToolbarAPIHandler {
    private static final Logger log = LoggerFactory.getLogger(ToolbarAPIHandler.class);
    private final Function<ItemStack, ItemNBTHandler> itemNBTHandlerFunction;
    private final ToolbarService toolbarService;
    private final EquipService equipService;
    private final JavaPlugin plugin;

    private final ListenerService listenerService;

    @NotNull
    static ItemNBTHandler determineSuitableNBTHandler(JavaPlugin plugin, ItemStack itemStack) {
        final String version = plugin.getServer()
                .getBukkitVersion()
                .split("-")[0];
        final String[] versionArgs =
                version.split("\\.");

        final int major = Integer.parseInt(versionArgs[0]);
        final int minor = Integer.parseInt(versionArgs[1]);
        int patch = 0;
        if (major == 1 && versionArgs.length > 2) {
            patch = Integer.parseInt(versionArgs[2]);
        }

        // PersistentDataContainer (shorten to pdc) was introduced in Spigot 1.14.1 release,
        // providing developers with a native way to write custom data to objects. This means that
        // we no longer have to consistently update our third-party NBT dependency to support the
        // latest versions of Minecraft
        if (major > 1 || (minor == 14 && patch > 1) || minor > 14) {
            return new ModernPDCNBTHandler(itemStack);
        }
        return new BukkitNBTHandler(itemStack);
    }

    public ToolbarAPIHandler(final JavaPlugin plugin, final Function<ItemStack, ItemNBTHandler> itemNBTHandlerFunction) {
        this.itemNBTHandlerFunction = itemNBTHandlerFunction;
        this.toolbarService = new ToolbarServiceImpl(new ToolbarRegistry(), new ToolProcessorImpl());
        this.equipService = new EquipServiceImpl();
        this.plugin = plugin;
        this.listenerService = new ListenerServiceImpl(new EventToolActivator(toolbarService));
    }

    public void enable(ToolbarAPI api) {
        new ToolbarAPIListener(plugin, api);
        ListenerRegistry.loadAndCopy().forEach(listenerService::registerListener);

        registerDynamicListener();
    }

    private void registerDynamicListener() {
        Set<Class<?>> typesAnnotatedWith = new Reflections(ListenerProcessor.PACKAGE_NAME).
                getTypesAnnotatedWith(GeneratedListener.class);

        if (typesAnnotatedWith.isEmpty()) {
            plugin.getLogger().warning("No dynamically generated listeners found. " +
                    "Make sure you have the annotation processor configured correctly.");
            return;
        }

        typesAnnotatedWith.forEach(clazz -> {
            plugin.getLogger().info("Registering dynamically generated listener: " + clazz.getName());
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
        equipService.unequipAll();
    }

    public JavaPlugin plugin() {
        return plugin;
    }

    public <T extends Event> void notListeningWarn(Class<T> clazz) {
        plugin.getLogger().warning(String.format("Be warned that any tools listening to %s-based event won't be triggered by default.", clazz.getSimpleName()));
    }

    public ItemNBTHandler handleItemNBT(ItemStack itemStack) {
        return itemNBTHandlerFunction.apply(itemStack);
    }

    public EquipService getPlayerEquipService() {
        return equipService;
    }

    public ToolbarService getToolbarRegistrationService() {
        return toolbarService;
    }

    public ListenerService listenerService() {
        return listenerService;
    }

}
