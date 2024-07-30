package net.tracyex0.authon.command;

import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import net.tracyex0.authon.AuthonServer;
import net.tracyex0.authon.misc.GameUtils;
import net.tracyex0.authon.misc.IPlayerAuth;
import net.tracyex0.authon.storage.PlayerContainer;

public class CommandRegister extends CommandCompat {

    public CommandRegister() {
        super("register", false);
    }

    @Override
    public String commandSyntax() {
        return "§e/register <password>";
    }

    @Override
    public void onExecute(String[] args, NetworkPlayer commandExecutor) {
        IPlayerAuth auth = (IPlayerAuth) commandExecutor;
        if (auth.isAuthenticated()) {
            commandExecutor.displayChatMessage("Error: Already logged in!");
            return;
        }

        if (AuthonServer.getStorage().isPlayerPresent(commandExecutor.getPlayerName())) {
            commandExecutor.displayChatMessage("Error: Already registered!");
            return;
        }

        if (args.length < 2) {
            commandExecutor.displayChatMessage("Usage: /register <password>");
            return;
        }

        String password = args[1];

        if (!GameUtils.isPasswordSuitable(password)) {
            commandExecutor.displayChatMessage(AuthonServer.CONFIG.local_password_short);
            return;
        }

        String hash = AuthonServer.getEncryption().getHash(password);
        String ip = GameUtils.getIPAddress(commandExecutor);

        PlayerContainer player = new PlayerContainer(commandExecutor.getPlayerName(), hash, ip);

        if (AuthonServer.getStorage().savePlayer(player)) {
            commandExecutor.displayChatMessage("Successfully registered! Welcome to the server!");
            auth.setAuthenticated(true);
        } else {
            commandExecutor.displayChatMessage("Error while registering!");
        }
        return;
    }

}
