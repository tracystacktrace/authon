package net.tracystacktrace.authon.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.networking.NetLoginHandler;
import net.tracystacktrace.authon.AuthonServer;
import net.tracystacktrace.authon.tools.GameUtils;
import net.tracystacktrace.authon.tools.IPlayerAuth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * In this mixin, we end up initializing the authentication process,
 * basically showing info about authorization and pushing some other checks over.
 *
 * @since 1.0
 */
@Mixin(NetLoginHandler.class)
public class MixinNetLoginHandler {
    @Inject(method = "initializePlayerConnection", at = @At(
            value = "INVOKE",
            target = "Lcom/fox2code/foxloader/internal/InternalPlayerHooks;sendPlayerJoinEvent(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/common/entity/player/EntityPlayer;)V",
            shift = At.Shift.AFTER
    ))
    private void authon$init_auth_chat(CallbackInfo ci, @Local EntityPlayerMP player) {
        if (AuthonServer.CONFIG.allowsSessions && GameUtils.checkSession(player)) {
            player.addChatMessage(AuthonServer.CONFIG.local_session_success);
            ((IPlayerAuth) player).setAuthenticated(true);
        } else {
            GameUtils.initPlayerAuth(player);
        }
    }

    @Inject(method = "handleErrorMessage", at = @At("TAIL"))
    private void authon$make_sure(String arg1, Object[] args, CallbackInfo ci) {
        //TODO: Measurements to safe player from cache
    }
}
