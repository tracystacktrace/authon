# AuthOn
AuthOn is a modification for FoxLoader (ReIndev) servers designed to implement an alternative authentification system.

The example of alternative auth systems in Bukkit world are AuthMe or OpeNLogin. AuthOn is exclusively developed for the FoxLoader (ReIndev) based platforms.

Uses H2 as a database system, seems to be pretty efficient and optimized solution.

**WARNING! This is a SERVER modification; therefore, it won't work on clients!**

## Installation

Simply get a release version of this mod and put it into `mods` folder.

You can get the latest release here

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
```

## Configuration

The mod's config file (`authon.config`) can be used to configure localization and messages. However, there are also two specific options:

```properties
# Amount of time (in seconds) to allow the player to login/register
waitingTime=30

# Instantly kick on first faulty attempt
instantKick=true
```

## TODO

- [ ] Fix the log leaking bug (happens when log saves commands history).
- [ ] Introduce an ability to connect to a distant MySQL or SQL servers.
- [ ] Make stuff compatible with ReIndev 2.9 (when it releases).
- [X] Meow? Mrow mrrrrp :3

## License

The mod is licensed under [LGPL-3.0](https://codeberg.org/tracystacktrace/authon/src/branch/main/LICENSE). Follow the license and you'll be ok!