# Faster Villager Trading

A client-side Fabric mod for Minecraft 26.2. Shift-click a villager trade to execute it using items from your inventory and place the result in your inventory.

## Requirements

- Minecraft Java Edition 26.2
- Fabric Loader 0.20.1 or newer
- Java Development Kit 25 to build and run Minecraft 26.2. This project uses a portable JDK and does not change other Java installations.

## Build and run

On Windows, run `gradlew.bat build` to build the mod and `gradlew.bat runClient` to launch a development client. The built mod jar is in `build/libs/`.

Release versions use `<mod-version>+mc<Minecraft-version>-<loader>`, for example `1.0.2+mc26.2-fabric`. The jar filename includes this version, such as `faster-villager-trading-1.0.2+mc26.2-fabric.jar`.

Open the project folder in IntelliJ IDEA or VS Code with the Java extensions installed. Gradle will download the required Minecraft and Fabric dependencies on the first build.

## Behavior

Hold Shift and click a trade offer to execute one trade. The mod checks that the full cost is available first, then uses Minecraft's native trade handling and quick-moves the result into your inventory. If the full cost is unavailable, no items are moved. Regular clicks continue to select offers normally.