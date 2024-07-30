package net.tracyex0.authon.command;

import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;

import net.minecraft.src.server.packets.NetServerHandler;
import net.tracyex0.authon.AuthonServer;
import net.tracyex0.authon.misc.IPlayerAuth;
import net.tracyex0.authon.storage.PlayerContainer;

public class CommandRegister extends CommandCompat {

    public CommandRegister() {
        super("register", false);
    }

    public void onExecute(String[] args, NetworkPlayer commandExecutor) {
        IPlayerAuth auth = (IPlayerAuth) commandExecutor;
        if(auth.isAuthenticated()) {
            commandExecutor.displayChatMessage("Error: Already logged in!");
            return;
        }

        if(AuthonServer.getStorage().isPlayerPresent(commandExecutor.getPlayerName())) {
            commandExecutor.displayChatMessage("Error: Already registered!");
            return;
        }

        if(args.length < 1) {
            commandExecutor.displayChatMessage("Usage: /register <password>");
            return;
        }

        String password = args[0];

        if(!AuthonServer.isPasswordSuitable(password)) {
            commandExecutor.displayChatMessage("Error: Password is too short!");
            return;
        }

        String hash = AuthonServer.getEncryption().getHash(password);
        String ip = ((NetServerHandler)commandExecutor.getNetworkConnection()).netManager.getSocket().getInetAddress().getHostAddress();

        PlayerContainer player = new PlayerContainer(password, hash, ip);

        if(AuthonServer.getStorage().savePlayer(player)) {
            commandExecutor.displayChatMessage("Successfully registered! Welcome to the server!");
            auth.setAuthenticated(true);
        }else {
            commandExecutor.displayChatMessage("Error while registering!");
        }
        return;
    }
    
}
