package net.tracyex0.authon.command;

import com.fox2code.foxloader.registry.CommandCompat;
import com.fox2code.foxloader.registry.RegisteredCommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.tracyex0.authon.AuthonServer;
import net.tracyex0.authon.storage.PlayerContainer;

public class CommandAdminAuthon extends CommandCompat {

    public CommandAdminAuthon() {
        super("authon", true, false, NO_ALIASES, Boolean.TRUE);
    }

    public void onExecute(String[] args, RegisteredCommandSender commandExecutor) {
        if(args.length < 2) {
            commandExecutor.displayChatMessage("§cInfo: /authon register <username> <password>");
            commandExecutor.displayChatMessage("§cInfo: /authon changepwd <username> <password>");
            commandExecutor.displayChatMessage("§cInfo: /authon unregister <username>");
            return;
        }

        switch (args[1]) {
            case "register": {
                if(args.length < 4) {
                    commandExecutor.displayChatMessage("§cError: Invalid syntax! Use /authon register <username> <password>");
                    return;
                }
                String username = args[2];
                if(AuthonServer.getStorage().isPlayerPresent(username)) {
                    commandExecutor.displayChatMessage("§cError: Player already registered!");
                    return;
                }
                if(!AuthonServer.isPasswordSuitable(args[3])) {
                    commandExecutor.displayChatMessage("§cError: Password is too short!");
                    return;
                }

                String hash = AuthonServer.getEncryption().getHash(args[3]);
                PlayerContainer container = new PlayerContainer(username, hash, "");

                if(AuthonServer.getStorage().savePlayer(container)) {
                    commandExecutor.displayChatMessage("Successfully registered user!");
                }else {
                    commandExecutor.displayChatMessage("Errored while fucking");
                }

                return;
            }

            case "unregister": {
                if(args.length < 3) {
                    commandExecutor.displayChatMessage("§cError: Invalid syntax! Use /authon unregister <username>");
                    return;
                }
                String username = args[2];
                if(!AuthonServer.getStorage().isPlayerPresent(username)) {
                    commandExecutor.displayChatMessage("§cError: Player bot found!");
                    return;
                }

                if(AuthonServer.getStorage().deletePlayer(username)) {
                    commandExecutor.displayChatMessage("Successfully executed!");
                    EntityPlayerMP player = MinecraftServer.getInstance().configManager.getPlayerEntity(username);
                    if(player != null) {
                        player.kick("Your unregistered!");
                    }
                }else {
                    commandExecutor.displayChatMessage("Errored while fucking");
                }

                return;
            }

            case "changepwd": {
                if(args.length < 4) {
                    commandExecutor.displayChatMessage("§cError: Invalid syntax! Use /authon changepwd <username> <password>");
                    return;
                }
                String username = args[2];
                if(!AuthonServer.getStorage().isPlayerPresent(username)) {
                    commandExecutor.displayChatMessage("§cError: Player bot found!");
                    return;
                }
                if(!AuthonServer.isPasswordSuitable(args[3])) {
                    commandExecutor.displayChatMessage("§cError: Password is too short!");
                    return;
                }

                String hash = AuthonServer.getEncryption().getHash(args[3]);
                PlayerContainer player = AuthonServer.getStorage().getPlayer(username);
                player.setHash(hash);

                if(AuthonServer.getStorage().updatePassword(player)) {
                    commandExecutor.displayChatMessage("Successfully changed pwd!");
                } else {
                    commandExecutor.displayChatMessage("Errored while fucking");
                }
                return;
            }

            default:
        }
    }
    
}
