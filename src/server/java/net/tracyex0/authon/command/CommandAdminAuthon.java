package net.tracyex0.authon.command;

import com.fox2code.foxloader.registry.CommandCompat;
import com.fox2code.foxloader.registry.RegisteredCommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.tracyex0.authon.AuthonServer;
import net.tracyex0.authon.misc.GameUtils;
import net.tracyex0.authon.storage.PlayerContainer;

public class CommandAdminAuthon extends CommandCompat {

    public CommandAdminAuthon() {
        super("authon", true, false, NO_ALIASES, Boolean.TRUE);
    }

    @Override
    public String commandSyntax() {
        return "§e/authon register|changepwd|unregister <username> <password>";
    }

    @Override
    public void onExecute(String[] args, RegisteredCommandSender commandExecutor) {
        if (args.length < 2) {
            commandExecutor.displayChatMessage(String.format(AuthonServer.CONFIG.local_command_usage, "§e/authon register <username> <password>"));
            commandExecutor.displayChatMessage(String.format(AuthonServer.CONFIG.local_command_usage, "§e/authon changepwd <username> <password>"));
            commandExecutor.displayChatMessage(String.format(AuthonServer.CONFIG.local_command_usage, "§e/authon unregister <username>"));
            return;
        }

        switch (args[1]) {
            case "register": {
                if (args.length < 4) {
                    commandExecutor.displayChatMessage(String.format(AuthonServer.CONFIG.local_command_invalid, "/authon register <username> <password>"));
                    return;
                }
                String username = args[2];
                if (AuthonServer.getStorage().isPlayerPresent(username)) {
                    commandExecutor.displayChatMessage(String.format(AuthonServer.CONFIG.local_op_register_already, username));
                    return;
                }
                if (!GameUtils.isPasswordSuitable(args[3])) {
                    commandExecutor.displayChatMessage(AuthonServer.CONFIG.local_password_short);
                    return;
                }

                String hash = AuthonServer.getEncryption().getHash(args[3]);
                PlayerContainer container = new PlayerContainer(username, hash, "");

                if (AuthonServer.getStorage().savePlayer(container)) {
                    commandExecutor.displayChatMessage(String.format(AuthonServer.CONFIG.local_op_register_success, username));
                } else {
                    commandExecutor.displayChatMessage(AuthonServer.CONFIG.local_db_unexpected);
                }

                return;
            }

            case "unregister": {
                if (args.length < 3) {
                    commandExecutor.displayChatMessage(String.format(AuthonServer.CONFIG.local_command_invalid, "/authon unregister <username>"));
                    return;
                }
                String username = args[2];
                if (!AuthonServer.getStorage().isPlayerPresent(username)) {
                    commandExecutor.displayChatMessage(AuthonServer.CONFIG.local_op_player_lack);
                    return;
                }

                if (AuthonServer.getStorage().deletePlayer(username)) {
                    commandExecutor.displayChatMessage(String.format(AuthonServer.CONFIG.local_op_unregister_success, username));
                    EntityPlayerMP player = MinecraftServer.getInstance().configManager.getPlayerEntity(username);
                    if (player != null) {
                        player.kick(AuthonServer.CONFIG.local_bridge_kick_unregister);
                    }
                } else {
                    commandExecutor.displayChatMessage(AuthonServer.CONFIG.local_db_unexpected);
                }

                return;
            }

            case "changepwd": {
                if (args.length < 4) {
                    commandExecutor.displayChatMessage(String.format(AuthonServer.CONFIG.local_command_invalid, "/authon changepwd <username> <password>"));
                    return;
                }
                String username = args[2];
                if (!AuthonServer.getStorage().isPlayerPresent(username)) {
                    commandExecutor.displayChatMessage(AuthonServer.CONFIG.local_op_player_lack);
                    return;
                }
                if (!GameUtils.isPasswordSuitable(args[3])) {
                    commandExecutor.displayChatMessage(AuthonServer.CONFIG.local_password_short);
                    return;
                }

                String hash = AuthonServer.getEncryption().getHash(args[3]);
                PlayerContainer player = AuthonServer.getStorage().getPlayer(username);

                if(player == null) {
                    commandExecutor.displayChatMessage(AuthonServer.CONFIG.local_db_unexpected);
                    return;
                }

                player.setHash(hash);

                if (AuthonServer.getStorage().updatePassword(player)) {
                    commandExecutor.displayChatMessage(String.format(AuthonServer.CONFIG.local_op_changepwd_success, username));
                } else {
                    commandExecutor.displayChatMessage(AuthonServer.CONFIG.local_db_unexpected);
                }
                return;
            }

            default:
        }
    }

}
