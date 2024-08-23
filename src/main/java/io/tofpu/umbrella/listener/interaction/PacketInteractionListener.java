package io.tofpu.umbrella.listener.interaction;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.InteractionHand;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.protocol.world.BlockFace;
import com.github.retrooper.packetevents.protocol.world.states.type.StateType;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerBlockPlacement;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientUseItem;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerAcknowledgeBlockChanges;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import io.tofpu.umbrella.domain.item.UmbrellaItem;
import io.tofpu.umbrella.domain.service.UmbrellaService;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.tofpu.umbrella.listener.util.UmbrellaListenerUtil.getUmbrellaItem;
import static io.tofpu.umbrella.listener.util.UmbrellaListenerUtil.invalidItemDetected;

public class PacketInteractionListener implements PacketListener {
    private final Plugin plugin;
    private final UmbrellaService umbrellaService;

    public PacketInteractionListener(Plugin plugin, UmbrellaService umbrellaService) {
        this.plugin = plugin;
        this.umbrellaService = umbrellaService;
    }

    public void registerSelf() {
        PacketEvents.getAPI().getEventManager().registerListener(this, PacketListenerPriority.LOW);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.PLAYER_BLOCK_PLACEMENT) {
            handleBlockPlacement(event);
        } else if (event.getPacketType() == PacketType.Play.Client.USE_ITEM) {
            handleUseItem(event);
        }
    }

    private void handleUseItem(PacketReceiveEvent event) {
        WrapperPlayClientUseItem useItemPacket = new WrapperPlayClientUseItem(event);
        Player player = (Player) event.getPlayer();

        EquipmentSlot equipmentSlot = getEquipmentSlot(useItemPacket.getHand());
        ItemStack itemInHand = getHoldingItem(player, equipmentSlot);

        final UmbrellaItem umbrellaItem = getUmbrellaItem(itemInHand, umbrellaService);
        // if the umbrella item not were found, return
        if (umbrellaItem == null) {
            return;
        }

        Action action = Action.RIGHT_CLICK_AIR;

        event.setCancelled(true);
        org.bukkit.block.BlockFace blockFace = org.bukkit.block.BlockFace.DOWN;
        Bukkit.getScheduler().runTask(plugin, () -> umbrellaItem.trigger(new PlayerInteractEvent(player, action, itemInHand, null, blockFace, equipmentSlot)));
    }

    private EquipmentSlot getEquipmentSlot(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND;
    }

    private void handleBlockPlacement(PacketReceiveEvent event) {
        WrapperPlayClientPlayerBlockPlacement blockPlacement = new WrapperPlayClientPlayerBlockPlacement(event);

        User user = event.getUser();
        Player player = (Player) event.getPlayer();

        EquipmentSlot equipmentSlot = getEquipmentSlot(blockPlacement);
        if (equipmentSlot == null) {
            return;
        }

        ItemStack itemInHand = getHoldingItem(player, equipmentSlot);
        //noinspection ConstantValue
        if (itemInHand == null || itemInHand.getType().isAir()) {
            return;
        }

        boolean isHoldingABlock = itemInHand.getType().isBlock();

        final UmbrellaItem umbrellaItem = getUmbrellaItem(itemInHand, umbrellaService);
        // if the umbrella item not were found, return
        if (umbrellaItem == null) {
            return;
        }

        if (isHoldingABlock) {
            event.setCancelled(true);
            event.markForReEncode(true);
            user.sendPacketSilently(new WrapperPlayServerAcknowledgeBlockChanges(blockPlacement.getSequence()));
        }

        BlockFace blockFace = blockPlacement.getFace();
        Vector3i placementPosition = placementPosition(blockPlacement, blockFace);
        Block blockAt = getBlock(player, placementPosition);
        Action action = getAction(blockAt);

        org.bukkit.block.BlockFace bukkitBlockFace = bukkitBlockFace(blockFace);
        Bukkit.getScheduler().runTask(plugin, () -> {
            if (!umbrellaService.getUmbrellaRegistry()
                    .isInUmbrella(player.getUniqueId())) {
                invalidItemDetected(player, itemInHand, umbrellaService);
                return;
            }
            umbrellaItem.trigger(new PlayerInteractEvent(player, action, itemInHand, blockAt, bukkitBlockFace, equipmentSlot));
        });
    }

    private static Vector3i placementPosition(WrapperPlayClientPlayerBlockPlacement blockPlacement, BlockFace blockFace) {
        return blockPlacement.getBlockPosition().add(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
    }

    private static @NotNull Block getBlock(Player player, Vector3i placementPosition) {
        return player.getWorld().getBlockAt(toBukkitLocation(player, placementPosition));
    }

    private static @NotNull Action getAction(Block blockAt) {
        Action action;
        if (blockAt.getType().isAir()) {
            action = Action.RIGHT_CLICK_AIR;
        } else {
            action = Action.RIGHT_CLICK_BLOCK;
        }
        return action;
    }

    private static @Nullable EquipmentSlot getEquipmentSlot(WrapperPlayClientPlayerBlockPlacement blockPlacement) {
        EquipmentSlot equipmentSlot;
        if (blockPlacement.getHand() == InteractionHand.MAIN_HAND) {
            equipmentSlot = EquipmentSlot.HAND;
        } else if (blockPlacement.getHand() == InteractionHand.OFF_HAND) {
            equipmentSlot = EquipmentSlot.OFF_HAND;
        } else {
            return null;
        }
        return equipmentSlot;
    }

    private org.bukkit.block.BlockFace bukkitBlockFace(BlockFace blockFace) {
        return org.bukkit.block.BlockFace.valueOf(blockFace.name());
    }

    private static @NotNull Location toBukkitLocation(Player player, Vector3i blockPosition) {
        World world = player.getWorld();
        return new Location(world, blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
    }

    private static @NotNull ItemStack getHoldingItem(Player player, EquipmentSlot equipmentSlot) {
        PlayerInventory inventory = player.getInventory();
        if (equipmentSlot == EquipmentSlot.HAND) {
            return inventory.getItemInMainHand();
        } else {
            return inventory.getItemInOffHand();
        }
    }
}
