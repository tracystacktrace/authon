package net.tracyex0.authon.mixins;

import net.minecraft.common.command.ICommandListener;
import net.minecraft.common.util.logging.LogAgent;
import net.minecraft.server.command.ServerPlayerCommandHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.logging.Logger;

@Mixin(ServerPlayerCommandHandler.class)
public class MixinServerPlayerCommandHandler {
    @Redirect(method = "handleSlashCommand", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/common/util/logging/LogAgent;info(Ljava/lang/String;)V"))
    private void authon$stop_leaking(LogAgent instance, String s) {
        String command = s.split(":")[1].trim();
        if(command.startsWith("/login") || command.startsWith("/register") ||
                command.startsWith("/changepwd") || command.startsWith("/authon")) {
            return;
        }
        instance.info(s);
    }
}
