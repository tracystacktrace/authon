package net.tracystacktrace.authon.tools;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.tracystacktrace.authon.AuthonServer;
import net.tracystacktrace.authon.tools.storage.PlayerContainer;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class GameUtils {

    public static boolean isPasswordSuitable(@NotNull String s) {
        return s.length() >= AuthonServer.CONFIG.minPassLength;
    }

    public static boolean isAuthCommand(@NotNull String command) {
        return command.startsWith("/login") || command.startsWith("/register") || command.startsWith("/changepwd") || command.startsWith("/authon");
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
        final String username_constant = player.username;

        //push waiting async
        AuthonServer.TIMEOUT_POOL.schedule(() -> {
            final EntityPlayerMP scheduleEntity = MinecraftServer.getInstance().configManager.getPlayerEntity(username_constant);
            if (scheduleEntity == null) {
                return;
            }
            if (!((IPlayerAuth) scheduleEntity).isAuthenticated()) {
                scheduleEntity.playerNetServerHandler.kickPlayer(AuthonServer.CONFIG.local_auth_kick);
            }
        }, AuthonServer.CONFIG.waitingTime, TimeUnit.SECONDS);
    }

    public static @NotNull String getIPAddress(@NotNull EntityPlayerMP player) {
        return player.playerNetServerHandler.netManager.getSocket().getInetAddress().getHostAddress();
    }

    public static boolean checkSession(@NotNull EntityPlayerMP player) {
        final PlayerContainer container = AuthonServer.getStorage().getPlayer(player.username);
        if (container == null) {
            return false;
        }
        return getIPAddress(player).equals(container.getIp());
    }
}
