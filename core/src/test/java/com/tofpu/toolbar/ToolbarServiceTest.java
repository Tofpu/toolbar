package com.tofpu.toolbar;

import com.tofpu.toolbar.api.toolbar.ItemSlot;
import com.tofpu.toolbar.bootstrap.FullTestBoostrap;
import com.tofpu.toolbar.api.toolbar.Toolbar;
import com.tofpu.toolbar.api.toolbar.ToolbarSchema;
import com.tofpu.toolbar.api.toolbar.ToolbarService;
import com.tofpu.toolbar.api.toolbar.tool.Tool;
import com.tofpu.toolbar.api.toolbar.tool.action.ToolAction;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tofpu.toolbar.helper.ToolCreationHelper.tool;
import static com.tofpu.toolbar.helper.ToolCreationHelper.tools;
import static com.tofpu.toolbar.helper.ToolHelper.getItemOrThrow;
import static com.tofpu.toolbar.helper.ToolHelper.getToolOrThrow;
import static com.tofpu.toolbar.helper.ToolbarCreationHelper.toolbarSchema;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ToolbarServiceTest extends FullTestBoostrap {
    private ToolbarService toolbarService;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        toolbarService = api.toolbarService();
    }

    @Test
    void register_toolbar_directly_with_no_items_test() {
        Toolbar expectedToolbar = toolbarService.register(toolbarSchema("bar"));
        Toolbar actualToolbar = toolbarService.findToolbarBy("bar");
        assertNotNull(actualToolbar);
        assertEquals("bar", actualToolbar.id());
    }

    @Test
    void register_regular_toolbar_with_multiple_tools() {
        Toolbar toolbar = toolbarService.register(
                toolbarSchema(
                        "bar",
                        tools(
                                tool("first_tool", Material.DIAMOND),
                                tool("second_tool", Material.GOLD_ORE)
                        )
                )
        );

        toolbar = toolbarService.findToolbarBy("bar");
        assertNotNull(toolbar);

        assertEquals(2, toolbar.size());

        ItemStack itemAtIndex0 = getItemOrThrow(toolbar, 0);
        assertEquals(Material.DIAMOND, itemAtIndex0.getType());
        ItemStack itemAtIndex1 = getItemOrThrow(toolbar, 1);
        assertEquals(Material.GOLD_ORE, itemAtIndex1.getType());
    }

    @Test
    void register_custom_objects() {
        ToolbarSchema toolbarSchema = ToolbarSchema.newBuilder("guns_bar")
                .addItems(Tool.of(
                        "AR",
                        new ItemStack(Material.STICK),
                        ToolAction.of(
                                PlayerInteractEvent.class,
                                (owner, event) -> event.getPlayer().sendMessage("You clicked a gun! Nice!")
                        ),
                        ItemSlot.undefined()
                ))
                .build();
        toolbarService.register(toolbarSchema);
        Toolbar toolbar = toolbarService.findToolbarBy("guns_bar");
        assertNotNull(toolbar);

        Tool tool = getToolOrThrow(toolbar, "AR");
        assertNotNull(tool);
    }
}
