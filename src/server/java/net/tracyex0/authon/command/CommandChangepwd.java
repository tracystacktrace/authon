package net.tracyex0.authon.command;

import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import net.tracyex0.authon.AuthonServer;
import net.tracyex0.authon.misc.GameUtils;
import net.tracyex0.authon.storage.PlayerContainer;

public class CommandChangepwd extends CommandCompat {
    public CommandChangepwd() {
        super("changepwd", false);
    }

    @Override
    public String commandSyntax() {
        return "§e/changepwd <old password> <new password>";
    }

    @Override
    public void onExecute(String[] args, NetworkPlayer commandExecutor) {
        if (args.length < 3) {
            commandExecutor.displayChatMessage(String.format(AuthonServer.CONFIG.local_command_usage, this.commandSyntax()));
            return;
        }

        PlayerContainer playerContainer = AuthonServer.getStorage().getPlayer(commandExecutor.getPlayerName());

        if (playerContainer == null) {
            commandExecutor.displayChatMessage(AuthonServer.CONFIG.local_db_unexpected);
            return;
        }

        boolean correctOld = AuthonServer.getEncryption().compareHash(
                args[1],
                playerContainer.getHash()
        );

        if (!correctOld) {
            commandExecutor.displayChatMessage(AuthonServer.CONFIG.local_password_wrong);
            return;
        }

        if (!GameUtils.isPasswordSuitable(args[2])) {
            commandExecutor.displayChatMessage(String.format(AuthonServer.CONFIG.local_password_short, AuthonServer.CONFIG.minPassLength));
            return;
        }

        playerContainer.setHash(AuthonServer.getEncryption().getHash(args[2]));

        if (AuthonServer.getStorage().updatePassword(playerContainer)) {
            commandExecutor.displayChatMessage(AuthonServer.CONFIG.local_changepwd_success);
        } else {
            commandExecutor.displayChatMessage(AuthonServer.CONFIG.local_db_unexpected);
        }
    }
}
