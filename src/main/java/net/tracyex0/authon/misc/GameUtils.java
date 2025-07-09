package net.tracyex0.authon.misc;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.tracyex0.authon.AuthonServer;
import net.tracyex0.authon.storage.PlayerContainer;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class GameUtils {

    public static boolean isPasswordSuitable(@NotNull String s) {
        return s.length() >= AuthonServer.CONFIG.minPassLength;
    }

    public static void informPlayer(@NotNull EntityPlayerMP player) {
        player.addChatMessage(
                AuthonServer.getStorage().isPlayerPresent(player.username) ?
                        AuthonServer.CONFIG.local_login_notification :
                        AuthonServer.CONFIG.local_register_notification
        );
    }

    public static void initPlayerAuth(@NotNull EntityPlayerMP player) {
        informPlayer(player);
        AuthonServer.TIMEOUT_POOL.schedule(() -> {
            final String usnm_const = player.username;
            EntityPlayerMP player1 = MinecraftServer.getInstance().configManager.getPlayerEntity(usnm_const);
            if (player1 == null) {
                return;
            }
            if (!((IPlayerAuth) player1).isAuthenticated()) {
                player1.playerNetServerHandler.kickPlayer(AuthonServer.CONFIG.local_auth_kick);
            }
        }, AuthonServer.CONFIG.waitingTime, TimeUnit.SECONDS);
    }

    public static @NotNull String getIPAddress(@NotNull EntityPlayerMP player) {
        return player.playerNetServerHandler.netManager.getSocket().getInetAddress().getHostAddress();
    }

    public static boolean checkSession(@NotNull EntityPlayerMP player) {
        PlayerContainer playerContainer = AuthonServer.getStorage().getPlayer(player.username);
        if(playerContainer == null) {
            return false;
        }
        return getIPAddress(player).equals(playerContainer.getIp());
    }
}
