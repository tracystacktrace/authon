package net.tracystacktrace.authon.command;

import net.minecraft.common.command.Command;
import net.minecraft.common.command.ICommandListener;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.networking.NetServerHandler;
import net.tracystacktrace.authon.AuthonServer;
import net.tracystacktrace.authon.misc.GameUtils;
import net.tracystacktrace.authon.misc.IPlayerAuth;
import net.tracystacktrace.authon.storage.PlayerContainer;

public class CommandRegister extends Command {

    public CommandRegister() {
        super("register", false, false);
    }

    @Override
    public void printHelpInformation(ICommandListener commandExecutor) {
    }

    @Override
    public String commandSyntax() {
        return "§e/register <password>";
    }

    @Override
    public void onExecute(String[] args, ICommandListener commandExecutor) {
        if (!(commandExecutor instanceof NetServerHandler)) {
            commandExecutor.log("Only users can access this shit!");
            return;
        }

        final IPlayerAuth auth = (IPlayerAuth) ((NetServerHandler) commandExecutor).getEntityPlayer();

        if (auth.isAuthenticated()) {
            commandExecutor.log(AuthonServer.CONFIG.local_login_already);
            return;
        }

        if (AuthonServer.getStorage().isPlayerPresent(auth.getLoginUsername())) {
            commandExecutor.log(AuthonServer.CONFIG.local_register_already);
            return;
        }

        if (args.length < 2) {
            commandExecutor.log(String.format(AuthonServer.CONFIG.local_command_usage, this.commandSyntax()));
            return;
        }

        final String password = args[1];

        if (!GameUtils.isPasswordSuitable(password)) {
            commandExecutor.log(String.format(AuthonServer.CONFIG.local_password_short, AuthonServer.CONFIG.minPassLength));
            return;
        }

        String hash = AuthonServer.getEncryption().getHash(password);
        String ip = GameUtils.getIPAddress((EntityPlayerMP) ((NetServerHandler) commandExecutor).getEntityPlayer());

        PlayerContainer player = new PlayerContainer(auth.getLoginUsername(), hash, ip);

        if (AuthonServer.getStorage().savePlayer(player)) {
            commandExecutor.log(AuthonServer.CONFIG.local_register_success);
            auth.setAuthenticated(true);
        } else {
            commandExecutor.log(AuthonServer.CONFIG.local_db_unexpected);
        }
    }

}
