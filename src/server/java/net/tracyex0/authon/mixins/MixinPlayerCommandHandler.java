package net.tracyex0.authon.mixins;

import net.minecraft.mitask.PlayerCommandHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.logging.Logger;

@Mixin(PlayerCommandHandler.class)
public class MixinPlayerCommandHandler {
    @Redirect(method = "handleSlashCommand", at = @At(
            value = "INVOKE",
            target = "Ljava/util/logging/Logger;info(Ljava/lang/String;)V"))
    private void authon$stop_leaking(Logger instance, String s) {
        String command = s.split(":")[1].trim();
        if(
                command.startsWith("/login") || command.startsWith("/register") || command.startsWith("/changepwd") || command.startsWith("/authon")
        ) {
            return;
        }
        instance.info(s);
    }
}
