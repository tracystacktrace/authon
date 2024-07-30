package net.tracyex0.authon.command;

import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;

import net.tracyex0.authon.AuthonServer;
import net.tracyex0.authon.storage.PlayerContainer;

public class CommandAdminAuthon extends CommandCompat {

    public CommandAdminAuthon() {
        super("authon", true);
    }

    public void onExecute(String[] args, NetworkPlayer commandExecutor) {
        if(!commandExecutor.isOperator()) {
            return;
        }

        if(args.length < 1) {
            commandExecutor.displayChatMessage("§cInfo: /authon register <username> <password>");
            commandExecutor.displayChatMessage("§cInfo: /authon changepwd <username> <password>");
            commandExecutor.displayChatMessage("§cInfo: /authon unregister <username>");
            return;
        }

        switch (args[0]) {
            case "register": {
                if(args.length < 3) {
                    commandExecutor.displayChatMessage("§cError: Invalid syntax! Use /authon register <username> <password>");
                    return;
                }
                String username = args[1];
                if(AuthonServer.STORAGE.isPlayerPresent(username)) {
                    commandExecutor.displayChatMessage("§cError: Player already registered!");
                    return;
                }
                if(!AuthonServer.isPasswordSuitable(args[2])) {
                    commandExecutor.displayChatMessage("§cError: Password is too short!");
                    return;
                }

                String hash = AuthonServer.ENCRYPTOR.getHash(args[2]);
                PlayerContainer container = new PlayerContainer(username, hash, "");

                if(AuthonServer.STORAGE.savePlayer(container)) {
                    commandExecutor.displayChatMessage("Successfully registered user!");
                }else {
                    commandExecutor.displayChatMessage("Errored while fucking");
                }

                return;
            }

            case "unregister": {
                if(args.length < 2) {
                    commandExecutor.displayChatMessage("§cError: Invalid syntax! Use /authon unregister <username>");
                    return;
                }
                String username = args[1];
                if(!AuthonServer.STORAGE.isPlayerPresent(username)) {
                    commandExecutor.displayChatMessage("§cError: Player bot found!");
                    return;
                }

                if(AuthonServer.STORAGE.deletePlayer(username)) {
                    commandExecutor.displayChatMessage("Successfully executed!");
                }else {
                    commandExecutor.displayChatMessage("Errored while fucking");
                }
                
                return;
            }

            case "changepwd": {
                if(args.length < 3) {
                    commandExecutor.displayChatMessage("§cError: Invalid syntax! Use /authon changepwd <username> <password>");
                    return;
                }
                String username = args[1];
                if(!AuthonServer.STORAGE.isPlayerPresent(username)) {
                    commandExecutor.displayChatMessage("§cError: Player bot found!");
                    return;
                }
                if(!AuthonServer.isPasswordSuitable(args[2])) {
                    commandExecutor.displayChatMessage("§cError: Password is too short!");
                    return;
                }

                String hash = AuthonServer.ENCRYPTOR.getHash(args[2]);
                PlayerContainer player = AuthonServer.STORAGE.getPlayer(username);
                player.setHash(hash);

                if(AuthonServer.STORAGE.updatePassword(player)) {
                    commandExecutor.displayChatMessage("Successfully changed pwd!");
                } else {
                    commandExecutor.displayChatMessage("Errored while fucking");
                }
                return;
            }

            default: return;
        }
    }
    
}
