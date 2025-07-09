package net.tracystacktrace.authon.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.common.networking.NetworkManager;
import net.minecraft.common.networking.Packet13PlayerLookMove;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.networking.NetServerHandler;
import net.minecraft.server.util.ServerConfigurationManager;
import net.tracystacktrace.authon.AuthonServer;
import net.tracystacktrace.authon.tools.IPlayerAuth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerConfigurationManager.class)
public class MixinServerConfigurationManager {
    @Inject(method = "initializeConnectionToPlayer", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/networking/NetServerHandler;teleportTo(DDDFF)V"))
    private void authon$injectProceedHideCoords(NetworkManager net_manager, EntityPlayerMP player, CallbackInfo ci, @Local NetServerHandler server_net_handler) {
        if (AuthonServer.CONFIG.hideCoordinates && !IPlayerAuth.isAuthenticated(player)) {
            server_net_handler.sendPacket(new Packet13PlayerLookMove(0, -3 + 1.62, -3, 0, 0f, 0f, false));
        }
    }

    @Inject(method = "playerLoggedOut", at = @At("TAIL"))
    private void authon$stripTimeOut(EntityPlayerMP player, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(player)) {
            ((IPlayerAuth) player).cancelTimeout();
        }
    }
}
