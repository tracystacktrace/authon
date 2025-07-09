package net.tracystacktrace.authon.tools.security;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

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
        for (int b : bytes) {
            builder.append(Integer.toString((b & 0xFF) + 256, 16).substring(1));
        }
        return builder.toString();
    }

    public @Nullable String getHash(@Nullable String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        /* a salty salt approach */
        final Random random = new Random();
        random.setSeed(s.getBytes(StandardCharsets.US_ASCII)[0]);
        final StringBuilder salt = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            salt.append(random.nextInt(0xFFFFFF));
        }

        salt.append(s);
        return this.computeHash(salt.toString());
    }

    public boolean compareHash(@Nullable String supposed, @Nullable String hash) {
        if (supposed == null || hash == null || supposed.isEmpty() || hash.isEmpty()) {
            return false;
        }

        return hash.equals(getHash(supposed));
    }

    public static @NotNull PassEncryption getInstance() {
        try {
            return new PassEncryption();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 is not supported in this environment! Aborting!", e);
        }
    }
}
