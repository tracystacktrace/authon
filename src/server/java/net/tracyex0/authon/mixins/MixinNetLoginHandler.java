package net.tracyex0.authon.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.minecraft.src.server.packets.Packet1Login;
import net.tracyex0.authon.AuthonServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.src.server.packets.NetLoginHandler;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetLoginHandler.class)
public class MixinNetLoginHandler {
    @Inject(method = "doLogin", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/src/game/entity/player/EntityPlayerMP;func_20057_k()V",
            shift = At.Shift.AFTER
    ))
    private void authon$input_message(
            Packet1Login packet1Login,
            CallbackInfo ci,
            @Local EntityPlayerMP player
    ) {
        AuthonServer.initPlayerAuth(player);
    }
}
