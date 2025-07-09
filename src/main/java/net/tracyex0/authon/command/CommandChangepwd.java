package net.tracyex0.authon.command;

import net.minecraft.common.command.Command;
import net.minecraft.common.command.ICommandListener;
import net.minecraft.server.networking.NetServerHandler;
import net.tracyex0.authon.AuthonServer;
import net.tracyex0.authon.misc.GameUtils;
import net.tracyex0.authon.storage.PlayerContainer;

public class CommandChangepwd extends Command {

    public CommandChangepwd() {
        super("changepwd", false, false);
    }

    @Override
    public String commandSyntax() {
        return "§e/changepwd <old password> <new password>";
    }

    @Override
    public void printHelpInformation(ICommandListener commandExecutor) {
    }

    @Override
    public void onExecute(String[] args, ICommandListener commandExecutor) {
        if(!(commandExecutor instanceof NetServerHandler)) {
            commandExecutor.log("Use this from user side! lmaoaooamoamo");
            return;
        }

        if (args.length < 3) {
            commandExecutor.log(String.format(AuthonServer.CONFIG.local_command_usage, this.commandSyntax()));
            return;
        }

        PlayerContainer playerContainer = AuthonServer.getStorage().getPlayer(commandExecutor.getUsername());

        if (playerContainer == null) {
            commandExecutor.log(AuthonServer.CONFIG.local_db_unexpected);
            return;
        }

        boolean correctOld = AuthonServer.getEncryption().compareHash(
                args[1],
                playerContainer.getHash()
        );

        if (!correctOld) {
            commandExecutor.log(AuthonServer.CONFIG.local_password_wrong);
            return;
        }

        if (!GameUtils.isPasswordSuitable(args[2])) {
            commandExecutor.log(String.format(AuthonServer.CONFIG.local_password_short, AuthonServer.CONFIG.minPassLength));
            return;
        }

        playerContainer.setHash(AuthonServer.getEncryption().getHash(args[2]));

        if (AuthonServer.getStorage().updatePassword(playerContainer)) {
            commandExecutor.log(AuthonServer.CONFIG.local_changepwd_success);
        } else {
            commandExecutor.log(AuthonServer.CONFIG.local_db_unexpected);
        }
    }
}
