package net.tracyex0.authon;

import com.fox2code.foxloader.loader.Mod;

public class AuthonClientCrasher extends Mod {
    @Override
    public void onInit() {
        throw new RuntimeException("AuthOn is designed to function on servers! Please remove it from your minecraft client.");
    }
}
