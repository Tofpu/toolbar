package io.tofpu.toolbar;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import io.tofpu.toolbar.nbt.ItemNBTHandler;
import io.tofpu.toolbar.bootstrap.FullTestBoostrap;
import io.tofpu.toolbar.impl.PDCItemHandler;
import io.tofpu.toolbar.toolbar.Toolbar;
import io.tofpu.toolbar.toolbar.ToolbarService;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static io.tofpu.toolbar.helper.ToolHelper.getItemOrThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPluginTest extends FullTestBoostrap {
    public TestPluginTest() {
        super(MockBukkit.mock(), MockBukkit.loadSimple(TestPlugin.class, new Function<ItemStack, ItemNBTHandler>() {
            @Override
            public ItemNBTHandler apply(ItemStack itemStack) {
                return new PDCItemHandler(itemStack);
            }
        }));
    }

    @Test
    void name() {
        PlayerMock staffMember = server.addPlayer("staff-member");
        PlayerMock regularPlayer = server.addPlayer("regular-player");

        ToolbarService toolbarService = api.toolbarService();
        Toolbar toolbar = toolbarService.findToolbarBy("moderator-staff-bar");
        staffMember.setItemInHand(getItemOrThrow(toolbar, "ban"));

        PlayerInteractEntityEvent playerInteractEntityEvent = new PlayerInteractEntityEvent(staffMember, regularPlayer);
        server.getPluginManager().callEvent(playerInteractEntityEvent);

        assertTrue(regularPlayer.isBanned());
    }

    @Test
    void interact_test() {
        PlayerMock staffMember = server.addPlayer("staff-member");
        PlayerMock regularPlayer = server.addPlayer("regular-player");

        ToolbarService toolbarService = api.toolbarService();
        Toolbar toolbar = toolbarService.findToolbarBy("moderator-staff-bar");
        staffMember.setItemInHand(getItemOrThrow(toolbar, "ban"));

        PlayerInteractEntityEvent playerInteractEntityEvent = new PlayerInteractEntityEvent(staffMember, regularPlayer);
        server.getPluginManager().callEvent(playerInteractEntityEvent);

        assertTrue(regularPlayer.isBanned());
    }
}
