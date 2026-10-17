package com.tofpu.toolbar;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.tofpu.toolbar.api.nbt.ItemNBTHandler;
import com.tofpu.toolbar.bootstrap.FullTestBoostrap;
import com.tofpu.toolbar.impl.PDCItemHandler;
import com.tofpu.toolbar.api.toolbar.Toolbar;
import com.tofpu.toolbar.api.toolbar.ToolbarService;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static com.tofpu.toolbar.helper.ToolHelper.getItemOrThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPluginTest extends FullTestBoostrap {
    public TestPluginTest() {
        super(MockBukkit.mock(), MockBukkit.loadSimple(TestPlugin.class, new Function<ItemStack, ItemNBTHandler>() {
            @Override
            public ItemNBTHandler apply(ItemStack itemStack) {
                return new PDCItemHandler(itemStack);
            }
        }));
        setLoadAndInitializeToolbarAPI(false);
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
