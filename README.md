# Toolbar
**Toolbar** is a Java utility library designed to facilitate the creation and management of bundled hotbar item sets for players. Originally developed to power [SpeedBridge2](https://github.com/Tofpu/SpeedBridge2), it allows developers to easily introduce custom, synchronized hotbar layouts into their plugins.

For more information, visit our [documentation site](https://docs.tofpu.com/libraries/toolbar/overview/) for a comprehensive information about this library.

## Key Features

- **Cross-Version Support:** Compatible with Minecraft `1.8` up through modern versions (`1.20+`).
- **Future-Proof Architecture:** Employs an abstraction design that automatically handles future versions of Minecraft without requiring constant library updates.
- **Transparent Persistence:** Automatically bridges data storage implementations—leveraging Paper's [Persistent Data Container (PDC) API](https://docs.papermc.io/paper/dev/pdc/) on modern versions (`>1.14.1`) while seamlessly falling back to the [Item-NBT-API](https://github.com/tr7zw/Item-NBT-API) on legacy versions to track toolbar ownership.


## Usage

The example below demonstrates defining a moderation toolbar schema, registering it to the service, and equipping it to a player:

```java
import com.tofpu.toolbar.api.ToolbarAPI;
import com.tofpu.toolbar.api.toolbar.ToolbarService;
import com.tofpu.toolbar.api.toolbar.ToolbarSchema;
import com.tofpu.toolbar.api.toolbar.Toolbar;
import org.bukkit.entity.Player;

public class ToolbarManager {

    private final ToolbarAPI toolbarAPI = ToolbarAPI.getInstance();
    private final ToolbarItemFactory itemFactory; // From API consumer, not the library

    public ToolbarManager(ToolbarItemFactory itemFactory) {
        this.itemFactory = itemFactory;
    }

    private void registerToolbars() {
        ToolbarService toolbarService = toolbarAPI.toolbarService();

        // Construct a definition/schema for a moderator toolbar
        ToolbarSchema toolbarSchema = ToolbarSchema.newBuilder("moderator-staff-bar")
                // Add your custom staff tool items
                .addItems(itemFactory.createKickTool(), itemFactory.createBanTool())
                .build();

        // Register the toolbar schema for runtime usage
        toolbarService.register(toolbarSchema);
    }

    public void equipModTools(Player player) {
        // Retrieve the registered moderator toolbar
        Toolbar toolbar = toolbarAPI.toolbarService().findToolbarBy("moderator-staff-bar");

        // Equip the toolbar's items to the player's hotbar
        toolbarAPI.equipService().equip(player, toolbar);
    }
}
```

## Installation

Add `toolbar-core` to your project's build file using your preferred build system:

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.tofpu.toolbar</groupId>
        <artifactId>toolbar-core</artifactId>
        <version>VERSION</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

### Gradle (Groovy)
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.tofpu.toolbar:toolbar-core:VERSION'
}
```

### Gradle (Kotlin)

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.tofpu.toolbar:toolbar-core:VERSION")
}
```
