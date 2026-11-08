# Faster Villager Trading

A client-side Fabric mod for Minecraft 26.2. Shift-click a villager trade to move matching items from your inventory into the trade input slots. The trade itself is not executed automatically.

## Requirements

- Minecraft Java Edition 26.2
- Fabric Loader 0.20.1 or newer
- Java Development Kit 25 to build and run Minecraft 26.2. This project uses a portable JDK and does not change other Java installations.

## Build and run

On Windows, run `gradlew.bat build` to build the mod and `gradlew.bat runClient` to launch a development client. The built mod jar is in `build/libs/`.

Open the project folder in IntelliJ IDEA or VS Code with the Java extensions installed. Gradle will download the required Minecraft and Fabric dependencies on the first build.

## Behavior

Hold Shift and click a trade offer to fill its inputs using Minecraft 26.2's native item-transfer behavior. A regular click selects the offer without moving items. Complete the trade by clicking its output as usual.