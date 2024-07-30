package net.tracyex0.authon;

import com.fox2code.foxloader.loader.Mod;
import com.fox2code.foxloader.registry.CommandCompat;
import net.tracyex0.authon.command.CommandAdminAuthon;
import net.tracyex0.authon.command.CommandChangepwd;
import net.tracyex0.authon.command.CommandLogin;
import net.tracyex0.authon.command.CommandRegister;
import net.tracyex0.authon.misc.AdvancedConfigProcessor;
import net.tracyex0.authon.misc.AuthonConfig;
import net.tracyex0.authon.security.PassEncryption;
import net.tracyex0.authon.storage.IStorage;
import net.tracyex0.authon.storage.impl.H2Database;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AuthonServer extends Mod {
    public static final AuthonConfig CONFIG = new AuthonConfig();
    public static final ScheduledExecutorService TIMEOUT_POOL = Executors.newScheduledThreadPool(8);
    private static IStorage STORAGE;
    private static PassEncryption ENCRYPTOR;

    public static IStorage getStorage() {
        return STORAGE;
    }

    public static PassEncryption getEncryption() {
        return ENCRYPTOR;
    }

    @Override
    public void onPreInit() {
        setConfigObject(CONFIG);
        AdvancedConfigProcessor.processConfig(CONFIG, new File("authon.config"));

        STORAGE = new H2Database();

        try {
            ENCRYPTOR = new PassEncryption();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 is not supported in this environment! Aborting!", e);
        }

        CommandCompat.registerCommand(new CommandAdminAuthon());
        CommandCompat.registerCommand(new CommandRegister());
        CommandCompat.registerCommand(new CommandLogin());
        CommandCompat.registerCommand(new CommandChangepwd());
    }
}