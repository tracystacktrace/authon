package net.tracystacktrace.authon.tools;

import net.minecraft.common.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ScheduledFuture;

public interface IPlayerAuth {
    boolean isAuthenticated();

    void setAuthenticated(boolean value);

    void setTimeout(ScheduledFuture<?> timeout);

    void cancelTimeout();

    @NotNull String getLoginUsername();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    static boolean isAuthenticated(EntityPlayer player) {
        return ((IPlayerAuth) player).isAuthenticated();
    }
}
