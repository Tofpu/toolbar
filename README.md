# Toolbar
A Minecraft library that facilitates the creation of bundled items for player's hotbar.

> **WARNING**: The project is still under active development. Expect breaking changes to occur.

## Usage
```java
    private final ToolbarAPI toolbarAPI = new ToolbarAPI(this);

    @Override
    public void onEnable() {
        toolbarAPI.enable();
        registerToolbars();
    }

    private void registerToolbars() {
        // a list of staff tools, consisting of a kick and ban based tools
        List<StaffTool> staffTools = Arrays.asList(
            new StaffKickTool(),
            new StaffBanTool()
        );
        // constructs a staff-based toolbar consisting of the staff tools above
        StaffToolbar staffToolbar = new StaffToolbar("moderator-staff-bar", staffTools.toArray(new StaffTool[0]));
        // registers the staff-based toolbar to the registry for later use
        toolbarAPI.registerToolbar(staffToolbar);
        System.out.println("registerToolbars() called");
    }

    public void equipModTools(Player player) {
        // equips a toolbar that goes with the 
        // identifier "moderator-staff-bar" to the given player
        toolbarAPI.equip("moderator-staff-bar", player);
    }

    @Override
    public void onDisable() {
        if (toolbarAPI != null) {
            toolbarAPI.disable();
        }
    }
```
