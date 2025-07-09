package net.tracyex0.authon;

import com.fox2code.foxloader.launcher.FoxLauncher;
import com.fox2code.foxloader.loader.Mod;
import com.fox2code.foxloader.registry.CommandRegistry;
import net.tracyex0.authon.command.CommandAdminAuthon;
import net.tracyex0.authon.command.CommandChangepwd;
import net.tracyex0.authon.command.CommandLogin;
import net.tracyex0.authon.command.CommandRegister;
import net.tracyex0.authon.misc.AuthonConfig;
import net.tracyex0.authon.security.PassEncryption;
import net.tracyex0.authon.storage.IStorage;
import net.tracyex0.authon.storage.impl.H2Database;

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
        if(!FoxLauncher.isServer()) {
            throw new RuntimeException("The mod is only supported on servers! Do not install it on client-side!");
        }

        this.setConfigObject(CONFIG);

        STORAGE = new H2Database();

        try {
            ENCRYPTOR = new PassEncryption();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 is not supported in this environment! Aborting!", e);
        }

        CommandRegistry.registerCommand(new CommandAdminAuthon());
        CommandRegistry.registerCommand(new CommandRegister());
        CommandRegistry.registerCommand(new CommandLogin());
        CommandRegistry.registerCommand(new CommandChangepwd());
    }
}