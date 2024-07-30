package net.tracyex0.authon.misc;

public interface IPlayerAuth {
    void setAuthenticated(boolean b);

    boolean isAuthenticated();

    void setChatTimeout(int i);

    boolean hasTimeCome();
}
