package net.tracystacktrace.authon.misc;

import org.jetbrains.annotations.NotNull;

public interface IPlayerAuth {
    boolean isAuthenticated();

    void setAuthenticated(boolean b);

    @NotNull String getLoginUsername();
}
