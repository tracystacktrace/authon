package net.tracyex0.authon.command;

import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;

import net.minecraft.src.server.packets.NetServerHandler;
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
            commandExecutor.displayChatMessage(AuthonServer.CONFIG.local_login_already);
            return;
        }

        if(!AuthonServer.getStorage().isPlayerPresent(commandExecutor.getPlayerName())) {
            commandExecutor.displayChatMessage(AuthonServer.CONFIG.local_not_registered);
            return;
        }

        if(args.length < 2) {
            commandExecutor.displayChatMessage("Usage: /login <password>");
            return;
        }

        PlayerContainer playerContainer = AuthonServer.getStorage().getPlayer(commandExecutor.getPlayerName());

        if(playerContainer == null) {
            commandExecutor.displayChatMessage(AuthonServer.CONFIG.local_db_unexpected);
            return;
        }

        if(AuthonServer.getEncryption().compareHash(args[1], playerContainer.getHash())) {
            commandExecutor.displayChatMessage(AuthonServer.CONFIG.local_login_success);
            auth.setAuthenticated(true);
            String ip = ((NetServerHandler)commandExecutor.getNetworkConnection()).netManager.getSocket().getInetAddress().getHostAddress();
            playerContainer.setIp(ip);
            AuthonServer.getStorage().updateIPAdress(playerContainer);
        }else {
            commandExecutor.displayChatMessage(AuthonServer.CONFIG.local_password_wrong);
            if(AuthonServer.CONFIG.instantKick) {
                commandExecutor.kick(AuthonServer.CONFIG.local_password_wrong);
            }
        }
    }
}
