package net.tracystacktrace.authon;

import com.fox2code.foxloader.launcher.FoxLauncher;
import com.fox2code.foxloader.loader.Mod;
import com.fox2code.foxloader.registry.CommandRegistry;
import net.tracystacktrace.authon.command.CommandAdminAuthon;
import net.tracystacktrace.authon.command.CommandChangepwd;
import net.tracystacktrace.authon.command.CommandLogin;
import net.tracystacktrace.authon.command.CommandRegister;
import net.tracystacktrace.authon.config.AuthonConfig;
import net.tracystacktrace.authon.tools.security.PassEncryption;
import net.tracystacktrace.authon.tools.storage.IStorage;
import net.tracystacktrace.authon.tools.storage.impl.H2Database;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AuthonServer extends Mod {
    public static final AuthonConfig CONFIG = new AuthonConfig();
    public static final ScheduledExecutorService TIMEOUT_POOL = Executors.newScheduledThreadPool(16);
    private static IStorage STORAGE;
    private static PassEncryption ENCRYPTOR;
    private static String VERSION;

    public static @NotNull IStorage getStorage() {
        return STORAGE;
    }

    public static @NotNull PassEncryption getEncryption() {
        return ENCRYPTOR;
    }

    public static @NotNull String getVersion() {
        return VERSION;
    }

    @Override
    public void onPreInit() {
        if (!FoxLauncher.isServer()) {
            throw new RuntimeException("The mod is only supported on servers! Do not install it on client side!");
        }

        this.setConfigObject(CONFIG);

        VERSION = this.getModContainer().getModInfo().version;
        STORAGE = new H2Database();
        ENCRYPTOR = PassEncryption.getInstance();

        //register commands
        CommandRegistry.registerCommand(new CommandAdminAuthon());
        CommandRegistry.registerCommand(new CommandRegister());
        CommandRegistry.registerCommand(new CommandLogin());
        CommandRegistry.registerCommand(new CommandChangepwd());
    }
}