package net.tracyex0.authon.command;

import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;

import net.tracyex0.authon.AuthonServer;
import net.tracyex0.authon.misc.IPlayerAuth;
import net.tracyex0.authon.storage.PlayerContainer;

public class CommandLogin extends CommandCompat {

    public CommandLogin() {
        super("login", false);
    }
    
    public void onExecute(String[] args, NetworkPlayer commandExecutor) {
        IPlayerAuth auth = (IPlayerAuth) commandExecutor;
        if(auth.isAuthenticated()) {
            commandExecutor.displayChatMessage("Error: Already logged in!");
            return;
        }

        if(!AuthonServer.getStorage().isPlayerPresent(commandExecutor.getPlayerName())) {
            commandExecutor.displayChatMessage("Error: your not registered");
            return;
        }

        if(args.length < 1) {
            commandExecutor.displayChatMessage("Usage: /login <password>");
            return;
        }

        PlayerContainer playerContainer = AuthonServer.getStorage().getPlayer(commandExecutor.getPlayerName());

        if(playerContainer == null) {
            commandExecutor.displayChatMessage("Errored while getting your db data");
            return;
        }

        if(AuthonServer.getEncryption().compareHash(args[0], playerContainer.getHash())) {
            commandExecutor.displayChatMessage("Succesffully logged in!");
            auth.setAuthenticated(true);
        }else {
            commandExecutor.displayChatMessage("Error: Wrong password!");
        }
    }
}
