package net.tracystacktrace.authon.tools;

import net.minecraft.common.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

public interface IPlayerAuth {
    boolean isAuthenticated();

    void setAuthenticated(boolean b);

    @NotNull String getLoginUsername();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    static boolean isAuthenticated(EntityPlayer player) {
        return ((IPlayerAuth) player).isAuthenticated();
    }
}
