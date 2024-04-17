package io.tofpu.umbrella;

import com.sun.istack.internal.NotNull;
import io.tofpu.umbrella.domain.nbt.BukkitNBTHandler;
import io.tofpu.umbrella.domain.nbt.ItemNBTHandler;
import io.tofpu.umbrella.domain.nbt.ModernPDCNBTHandler;
import io.tofpu.umbrella.domain.service.UmbrellaService;
import io.tofpu.umbrella.listener.UmbrellaListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Function;

public class UmbrellaAPI {
    private static UmbrellaAPI umbrellaAPI;

    private final Function<ItemStack, ItemNBTHandler> itemNBTHandlerFunction;
    private final UmbrellaService umbrellaService;
    private final JavaPlugin plugin;
    private final boolean modernVersion;

    public static UmbrellaAPI getInstance() {
        return UmbrellaAPI.umbrellaAPI;
    }

    @NotNull
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
        this.itemNBTHandlerFunction = itemStack -> determineSuitableNBTHandler(plugin, itemStack);
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

        new UmbrellaListener(plugin, umbrellaService);
    }

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
}
