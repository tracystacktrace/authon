package net.tracyex0.authon.misc;

import org.jetbrains.annotations.NotNull;

public interface IPlayerAuth {
    boolean isAuthenticated();

    void setAuthenticated(boolean b);

    @NotNull String getLoginUsername();
}
