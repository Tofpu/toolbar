# Toolbar
A Minecraft library that facilitates the creation of bundled items for player's hotbar.

> **WARNING**: The project is still under active development. Expect breaking changes to occur.

## Usage
```java
private final ToolbarAPI toolbarAPI = new ToolbarAPI(this);

@Override
public void onEnable {
    this.toolbarAPI.enable();
    registerToolbars();
}

@Override
public void onDisable() {
    if (toolbarAPI != null) {
        toolbarAPI.disable();
    }
}

private void registerToolbars() {
    ToolbarService toolbarService = toolbarAPI.getToolbarService();
    // a list of staff tools, consisting of a kick and ban based tools
    List<StaffTool> staffTools = Arrays.asList(
            new StaffKickTool(ItemSlot.atIndex(0)),
            new StaffBanTool(ItemSlot.atIndex(1))
    );
    // constructs a staff-based toolbar consisting of the staff tools above
    StaffToolbar staffToolbar = new StaffToolbar("moderator-staff-bar", staffTools);
    // registers the staff-based toolbar to the registry for later use
    toolbarService.register(staffToolbar);
}

public void equipModTools(Player player) {
    ToolbarService toolbarService = toolbarAPI.getToolbarService();
    // retrieves the moderator staff toolbar we registered earlier
    StaffToolbar toolbar = toolbarService.findToolbarBy("moderator-staff-bar");
    // equips the toolbar's items to the player
    toolbarAPI.getPlayerEquipService().equip(player, toolbar);
}
```
