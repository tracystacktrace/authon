package net.tracyex0.authon.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.minecraft.src.server.packets.NetLoginHandler;
import net.minecraft.src.server.packets.NetServerHandler;
import net.minecraft.src.server.packets.Packet1Login;
import net.tracyex0.authon.AuthonServer;
import net.tracyex0.authon.misc.GameUtils;
import net.tracyex0.authon.misc.IPlayerAuth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetLoginHandler.class)
public class MixinNetLoginHandler {
    @Inject(method = "doLogin", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/src/game/entity/player/EntityPlayerMP;func_20057_k()V",
            shift = At.Shift.AFTER
    ))
    private void authon$init_auth_chat(
            Packet1Login packet1Login,
            CallbackInfo ci,
            @Local EntityPlayerMP player,
            @Local NetServerHandler netServerHandler
    ) {
        if(GameUtils.checkSession(player)) {
            player.displayChatMessage(AuthonServer.CONFIG.local_session_success);
            ((IPlayerAuth)player).setAuthenticated(true);
        }else {
            GameUtils.initPlayerAuth(player);
        }
    }

    @Inject(method = "handleErrorMessage", at = @At("TAIL"))
    private void authon$make_sure(String arg1, Object[] args, CallbackInfo ci) {
        //TODO: Measurements to safe player from cache
    }
}
