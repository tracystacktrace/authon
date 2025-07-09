package net.tracystacktrace.authon.command;

import net.minecraft.common.command.Command;
import net.minecraft.common.command.ICommandListener;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.networking.NetServerHandler;
import net.tracystacktrace.authon.AuthonServer;
import net.tracystacktrace.authon.misc.GameUtils;
import net.tracystacktrace.authon.misc.IPlayerAuth;
import net.tracystacktrace.authon.storage.PlayerContainer;

public class CommandLogin extends Command {

    public CommandLogin() {
        super("login", false, false);
    }

    @Override
    public String commandSyntax() {
        return "§e/login <password>";
    }

    @Override
    public void printHelpInformation(ICommandListener commandExecutor) {
    }

    @Override
    public void onExecute(String[] args, ICommandListener commandExecutor) {
        if (!(commandExecutor instanceof NetServerHandler)) {
            commandExecutor.log("This command is not designed to be used from non-player instance!");
            return;
        }

        IPlayerAuth auth = (IPlayerAuth) ((NetServerHandler) commandExecutor).getEntityPlayer();

        if (auth.isAuthenticated()) {
            commandExecutor.log(AuthonServer.CONFIG.local_login_already);
            return;
        }

        if (!AuthonServer.getStorage().isPlayerPresent(commandExecutor.getUsername())) {
            commandExecutor.log(AuthonServer.CONFIG.local_not_registered);
            return;
        }

        if (args.length < 2) {
            commandExecutor.log(String.format(AuthonServer.CONFIG.local_command_usage, this.commandSyntax()));
            return;
        }

        PlayerContainer playerContainer = AuthonServer.getStorage().getPlayer(commandExecutor.getUsername());

        if (playerContainer == null) {
            commandExecutor.log(AuthonServer.CONFIG.local_db_unexpected);
            return;
        }

        if (AuthonServer.getEncryption().compareHash(args[1], playerContainer.getHash())) {
            commandExecutor.log(AuthonServer.CONFIG.local_login_success);
            auth.setAuthenticated(true);
            playerContainer.setIp(GameUtils.getIPAddress((EntityPlayerMP) ((NetServerHandler) commandExecutor).getEntityPlayer()));
            AuthonServer.getStorage().updateIPAddress(playerContainer);
        } else {
            commandExecutor.log(AuthonServer.CONFIG.local_password_wrong);
            if (AuthonServer.CONFIG.instantKick) {
                ((NetServerHandler) commandExecutor).kickPlayer(AuthonServer.CONFIG.local_password_wrong);
            }
        }
    }
}
