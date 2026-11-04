# Minecraft Server Plugin

This is a Paper Minecraft server plugin that provides essential server management tools and player quality-of-life features. It includes spawn point management, player healing and feeding commands, flight and god mode toggles, and a persistent per-player home system. The plugin is designed to be lightweight and easy to configure, making it perfect for small to medium-sized Minecraft servers.

## Install

1. Download the latest `plugin.jar` from the releases page.
2. Stop your Paper server.
3. Place the `plugin.jar` file into the `plugins` folder of your server directory.
4. Restart your Paper server.

## Use

### Spawn Management
- **`/setspawn`** - Sets the server spawn point to your current location (requires `enderserver.setspawn` permission).
- **`/spawn`** - Teleports you to the server spawn point.

### Player Commands
- **`/heal [player]`** - Heals you or another player to full health, hunger, and removes fire (requires `enderserver.heal` permission).
- **`/feed [player]`** - Restores your or another player's hunger to full (requires `enderserver.feed` permission).
- **`/god [player]`** - Toggles invulnerability for you or another player (requires `enderserver.god` permission).
- **`/fly [player]`** - Toggles flight mode for you or another player (requires `enderserver.fly` permission).

### Home System
- **`/sethome [name]`** - Sets a home at your current location. Without a name, it uses "home" (limit: 1 by default).
- **`/home [name]`** - Teleports you to one of your homes.
- **`/delhome [name]`** - Deletes one of your homes.
- **`/homes`** - Lists all your homes.

Permissions can be adjusted in the plugin's `plugin.yml` or from the server console.

---

More Minecraft plugin projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/c5c7960e-a7cd-48a2-bee8-3f4bc4b8e28e?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=c5c7960e-a7cd-48a2-bee8-3f4bc4b8e28e
