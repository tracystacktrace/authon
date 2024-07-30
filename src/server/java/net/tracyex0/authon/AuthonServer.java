package net.tracyex0.authon;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import com.fox2code.foxloader.loader.Mod;
import com.fox2code.foxloader.registry.CommandCompat;

import net.tracyex0.authon.command.CommandAdminAuthon;
import net.tracyex0.authon.misc.AuthonConfig;
import net.tracyex0.authon.security.PassEncryption;
import net.tracyex0.authon.storage.IStorage;
import net.tracyex0.authon.storage.impl.H2Database;

public class AuthonServer extends Mod {
    public static final AuthonConfig CONFIG = new AuthonConfig();
    public static final Logger LOGGER = Logger.getLogger("AuthOn");

    public static IStorage STORAGE;
    public static PassEncryption ENCRYPTOR;

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
    }


    public static boolean isPasswordSuitable(String s) {
        return !s.isEmpty() && s.length() >= 8;
    }
}
