package net.tracyex0.authon.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Be aware that this class utilizes only SHA-256 hashing algorithm
 */
public class PassEncryption {
    private final MessageDigest sha256eater;

    public PassEncryption() throws NoSuchAlgorithmException {
        this.sha256eater = MessageDigest.getInstance("SHA-256");
    }

    private @NotNull String computeHash(@NotNull String s) {
        sha256eater.reset();
        byte[] bytes = sha256eater.digest(s.getBytes(StandardCharsets.US_ASCII));
        StringBuilder builder = new StringBuilder();
		for(int b : bytes) {
            builder.append(Integer.toString((b & 0xFF) + 256, 16).substring(1));
        }
		return builder.toString();
    }

    public @Nullable String getHash(@NotNull String s) {
        if(s.isEmpty()) {
            return null;
        }

        /* a salty salt approach */
        Random random = new Random();
        random.setSeed(s.getBytes(StandardCharsets.US_ASCII)[0]);
        String salt = "";
        for(int i = 0; i < 4; i++) {
            salt += random.nextInt(0xFFFFFF);
        }

        salt += s;
        return computeHash(salt);
    }

    public boolean compareHash(
        @Nullable String supposed,
        @Nullable String hash
    ) {
        if(supposed == null || hash == null || supposed.isEmpty() || hash.isEmpty()) {
            return false;
        }

        return hash.equals(getHash(supposed));
    }

}
