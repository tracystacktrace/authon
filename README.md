# AuthOn

<img src="https://github.com/tracystacktrace/authon/raw/main/src/main/resources/assets/authon/icon.png" alt="Mod Icon" width=200% />

[![Running on - FoxLoader](https://img.shields.io/badge/Running_on-FoxLoader-orange)](https://github.com/Fox2Code/FoxLoader) [![GitHub release](https://img.shields.io/github/release/tracystacktrace/authon?include_prereleases=&sort=semver&color=purple)](https://github.com/tracystacktrace/authon/releases/)

[Russian README.md | Русскоязычное описание](https://github.com/tracystacktrace/authon/blob/main/README-RU.md)

AuthOn is a modification for FoxLoader (ReIndev) servers designed to implement an alternative authentication system.

Examples of alternative auth systems in Bukkit world are `AuthMe` and `OpeNLogin`.
AuthOn is exclusively developed for the FoxLoader (ReIndev) servers, and is written on FoxLoader.

Uses H2 as a database system, a small runtime database system that will do dirty job of sql-ing for you.

**WARNING! This is a SERVER modification; therefore, it won't work on clients!**

## Commands

```
# Player commands:

# Allows a player to register, only once
/register <password>

# Allows a player to log in, only once per session
/login <password>

# Allows a player to change their password
/changepwd <old password> <new password>
```

```
# OP commands:

# Allows an OP to register a player
/authon register <username> <password>

# Allows an OP to unregister a player
/authon unregister <username>

# Allows an OP to change a player's password
/authon changepwd <username> <new password>

# Shows current mod version
/authon version
```

## Configuration

The mod's config file (`authon.cfg`) can be used to configure localization & messages, but also:

```json5
{
  // Amount of time (in seconds) to allow the player to login/register
  "waitingTime": 30,

  // Instantly kick on first faulty attempt
  "instantKick": true,

  // Allows IP sessions in a server
  "allowsSessions": false,

  // Hide inventory content before auth is completed
  "hideInventoryContent": true,
  
  // Locate player to underworld coordinates (limbo) before auth is completed
  "hideCoordinates": true
}
```

## Installation

Ensure you have [FoxLoader](https://github.com/Fox2Code/FoxLoader) installed. Click at the link and follow instructions (`Installation` section). Or in a nutshell, grab `*-mmc.zip` file and export it to MultiMC/PrismLauncher.

Simply download a `.jar` file and put it inside `mods` folder. That's all.

Want to compile by yourself? Just download the sources and run the following command:
```shell
./gradlew build
```

The output file will be located in `build/libs` folder.

## Implemented/TODO

- [X] Fix the log leaking bug (happens when log saves commands history)
- [X] Make sessions a toggleable feature
- [ ] Introduce an ability to connect to a remote MySQL or SQL servers
- [ ] Make stuff compatible with ReIndev 2.9 (when it releases)
- [X] Meow? Mrow mrrrrp :3

## License

The mod is licensed under [LGPL-3.0-or-later](https://github.com/tracystacktrace/authon/blob/main/LICENSE).

The embedded H2 library is licensed under [Mozilla Public License, version 2.0](https://github.com/h2database/h2database/blob/master/LICENSE.txt).