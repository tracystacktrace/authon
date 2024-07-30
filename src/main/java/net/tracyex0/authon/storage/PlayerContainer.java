package net.tracyex0.authon.storage;

import java.util.Objects;

public class PlayerContainer {
    private String username;
    private String hash;
    private String ip;

    public PlayerContainer(
        String username,
        String hash,
        String ip
    ) {
        this.username = username;
        this.hash = hash;
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public String getHash() {
        return hash;
    }

    public String getIp() {
        return ip;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.username, this.hash, this.ip);
    }

    @Override
    public boolean equals(Object obj) {
        if( !(obj instanceof PlayerContainer) ) {
            return false;
        }
        PlayerContainer another = (PlayerContainer) obj;
        return another.username.equals(this.username) && another.ip.equals(this.ip);
    }
}
