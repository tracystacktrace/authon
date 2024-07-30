package net.tracyex0.authon;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;

import com.fox2code.foxloader.loader.Mod;
import com.fox2code.foxloader.registry.CommandCompat;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.tracyex0.authon.command.CommandAdminAuthon;
import net.tracyex0.authon.command.CommandLogin;
import net.tracyex0.authon.command.CommandRegister;
import net.tracyex0.authon.misc.AuthonConfig;
import net.tracyex0.authon.security.PassEncryption;
import net.tracyex0.authon.storage.IStorage;
import net.tracyex0.authon.storage.impl.H2Database;

public class AuthonServer extends Mod {
    public static final AuthonConfig CONFIG = new AuthonConfig();
    public static final Logger LOGGER = Logger.getLogger("AuthOn");

    private static IStorage STORAGE;
    private static PassEncryption ENCRYPTOR;

    private static ScheduledExecutorService SHITTY_EXECUTOR = Executors.newScheduledThreadPool(8);

    @Override
    public void onPreInit() {
        setConfigObject(CONFIG);

        STORAGE = new H2Database();

        try {
            ENCRYPTOR = new PassEncryption();
        } catch(NoSuchAlgorithmException e) {
            throw new RuntimeException("eww", e);
        }


        CommandCompat.registerCommand(new CommandAdminAuthon());
        CommandCompat.registerCommand(new CommandRegister());
        CommandCompat.registerCommand(new CommandLogin());
    }

    public static IStorage getStorage() {
        return STORAGE;
    }

    public static PassEncryption getEncryption() {
        return ENCRYPTOR;
    }

    public static boolean isPasswordSuitable(@NotNull String s) {
        return s.length() >= 8;
    }

    public static void initPlayerAuth(@NotNull EntityPlayerMP player) {
        if(AuthonServer.getStorage().isPlayerPresent(player.username)) {
            player.displayChatMessage("Please authorize in this server!");
        }else {
            player.displayChatMessage("Please register in this server!");
        }

        SHITTY_EXECUTOR.schedule(() -> {
            final String usnm_const = player.username;
            EntityPlayerMP player1 = MinecraftServer.getInstance().configManager.getPlayerEntity(usnm_const);
            if(player1 == null) {
                return;
            }
            player1.kick("Too much time!");
        }, 30, TimeUnit.SECONDS);
    }
}
