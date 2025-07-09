package net.tracystacktrace.authon.command;

import net.minecraft.common.command.Command;
import net.minecraft.common.command.ICommandListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.tracystacktrace.authon.AuthonServer;
import net.tracystacktrace.authon.tools.GameUtils;
import net.tracystacktrace.authon.tools.storage.PlayerContainer;

public class CommandAdminAuthon extends Command {

    public CommandAdminAuthon() {
        super("authon", true, false);
    }

    @Override
    public String commandSyntax() {
        return "§e/authon register|changepwd|unregister|version <username> <password>";
    }

    @Override
    public void printHelpInformation(ICommandListener commandExecutor) {
    }

    @Override
    public void onExecute(String[] args, ICommandListener commandExecutor) {
        if (args.length < 2) {
            commandExecutor.log(String.format(AuthonServer.CONFIG.local_command_usage, "§e/authon register <username> <password>"));
            commandExecutor.log(String.format(AuthonServer.CONFIG.local_command_usage, "§e/authon changepwd <username> <password>"));
            commandExecutor.log(String.format(AuthonServer.CONFIG.local_command_usage, "§e/authon unregister <username>"));
            commandExecutor.log(String.format(AuthonServer.CONFIG.local_command_usage, "§e/authon version"));
            return;
        }

        switch (args[1]) {
            case "register": {
                if (args.length < 4) {
                    commandExecutor.log(String.format(AuthonServer.CONFIG.local_command_invalid, "/authon register <username> <password>"));
                    return;
                }
                String username = args[2];
                if (AuthonServer.getStorage().isPlayerPresent(username)) {
                    commandExecutor.log(String.format(AuthonServer.CONFIG.local_op_register_already, username));
                    return;
                }
                if (!GameUtils.isPasswordSuitable(args[3])) {
                    commandExecutor.log(String.format(AuthonServer.CONFIG.local_password_short, AuthonServer.CONFIG.minPassLength));
                    return;
                }

                String hash = AuthonServer.getEncryption().getHash(args[3]);
                PlayerContainer container = new PlayerContainer(username, hash, "");

                if (AuthonServer.getStorage().savePlayer(container)) {
                    commandExecutor.log(String.format(AuthonServer.CONFIG.local_op_register_success, username));
                } else {
                    commandExecutor.log(AuthonServer.CONFIG.local_db_unexpected);
                }

                return;
            }

            case "unregister": {
                if (args.length < 3) {
                    commandExecutor.log(String.format(AuthonServer.CONFIG.local_command_invalid, "/authon unregister <username>"));
                    return;
                }
                String username = args[2];
                if (!AuthonServer.getStorage().isPlayerPresent(username)) {
                    commandExecutor.log(AuthonServer.CONFIG.local_op_player_lack);
                    return;
                }

                if (AuthonServer.getStorage().deletePlayer(username)) {
                    commandExecutor.log(String.format(AuthonServer.CONFIG.local_op_unregister_success, username));
                    EntityPlayerMP player = MinecraftServer.getInstance().configManager.getPlayerEntity(username);
                    if (player != null) {
                        player.playerNetServerHandler.kickPlayer(AuthonServer.CONFIG.local_bridge_kick_unregister);
                    }
                } else {
                    commandExecutor.log(AuthonServer.CONFIG.local_db_unexpected);
                }

                return;
            }

            case "changepwd": {
                if (args.length < 4) {
                    commandExecutor.log(String.format(AuthonServer.CONFIG.local_command_invalid, "/authon changepwd <username> <password>"));
                    return;
                }
                String username = args[2];
                if (!AuthonServer.getStorage().isPlayerPresent(username)) {
                    commandExecutor.log(AuthonServer.CONFIG.local_op_player_lack);
                    return;
                }
                if (!GameUtils.isPasswordSuitable(args[3])) {
                    commandExecutor.log(String.format(AuthonServer.CONFIG.local_password_short, AuthonServer.CONFIG.minPassLength));
                    return;
                }

                String hash = AuthonServer.getEncryption().getHash(args[3]);
                PlayerContainer player = AuthonServer.getStorage().getPlayer(username);

                if (player == null) {
                    commandExecutor.log(AuthonServer.CONFIG.local_db_unexpected);
                    return;
                }

                player.setHash(hash);

                if (AuthonServer.getStorage().updatePassword(player)) {
                    commandExecutor.log(String.format(AuthonServer.CONFIG.local_op_changepwd_success, username));
                } else {
                    commandExecutor.log(AuthonServer.CONFIG.local_db_unexpected);
                }
                return;
            }

            case "version": {
                commandExecutor.log(String.format("§eYou are running §6AuthOn §eversion %s", AuthonServer.getVersion()));
                commandExecutor.log("§eFor more information, follow the link:");
                commandExecutor.log("§ehttps://github.com/tracystacktrace/authon");
                return;
            }

            default: {
                return;
            }
        }
    }

}
