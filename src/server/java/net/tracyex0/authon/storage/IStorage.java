package net.tracyex0.authon.storage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IStorage {
    boolean isPlayerPresent(@NotNull String username);

    @Nullable
    PlayerContainer getPlayer(@NotNull String username);

    boolean savePlayer(@NotNull PlayerContainer player);

    boolean deletePlayer(@NotNull String username);

    boolean updateIPAdress(@NotNull PlayerContainer player);

    boolean updatePassword(@NotNull PlayerContainer player);
}
