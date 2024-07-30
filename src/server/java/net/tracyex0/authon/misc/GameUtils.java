package net.tracyex0.authon.misc;

import com.fox2code.foxloader.network.NetworkPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.minecraft.src.server.packets.NetServerHandler;
import net.tracyex0.authon.AuthonServer;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class GameUtils {
    /**
     * @deprecated May be changed
     */
    @Deprecated
    public static boolean isPasswordSuitable(@NotNull String s) {
        return s.length() >= 8;
    }

    public static void informPlayer(@NotNull EntityPlayerMP player) {
        player.displayChatMessage(
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
                player1.kick(AuthonServer.CONFIG.local_auth_kick);
            }
        }, AuthonServer.CONFIG.waitingTime, TimeUnit.SECONDS);
    }

    public static @NotNull String getIPAddress(@NotNull NetworkPlayer player) {
        return ((NetServerHandler) player.getNetworkConnection()).netManager.getSocket().getInetAddress().getHostAddress();
    }
}
