package net.tracystacktrace.authon.mixins;

import net.minecraft.common.networking.*;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.networking.NetServerHandler;
import net.tracystacktrace.authon.AuthonServer;
import net.tracystacktrace.authon.tools.GameUtils;
import net.tracystacktrace.authon.tools.IPlayerAuth;
import net.tracystacktrace.authon.tools.ITapeHolder;
import net.tracystacktrace.authon.tools.TemporaryTape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * In order to prevent the illegal actions and block the user until auth is complete,
 * this mixin class ends up cancelling action events.
 *
 * @since 1.0
 */
@Mixin(NetServerHandler.class)
public abstract class MixinNetServerHandler implements ITapeHolder {
    @Shadow
    private EntityPlayerMP playerEntity;

    @Shadow
    public abstract void teleportTo(double arg1, double arg3, double arg5, float arg7, float arg8);

    @Shadow
    public abstract void sendPacket(Packet packet);

    @Unique
    private TemporaryTape authon$temp_solution;

    @Unique
    private boolean authon$keepInLimbo = false;

    @Override
    public void restoreCoordinates() {
        if (this.authon$temp_solution != null) {
            this.teleportTo(
                    this.authon$temp_solution.x,
                    this.authon$temp_solution.y,
                    this.authon$temp_solution.z,
                    this.authon$temp_solution.yaw,
                    this.authon$temp_solution.pitch
            );
        }
    }

    @Inject(method = "sendPacket*", at = @At("HEAD"), cancellable = true)
    private void authon$cause_chaos(Packet packet, CallbackInfo ci) {
        if (AuthonServer.CONFIG.hideCoordinates && !IPlayerAuth.isAuthenticated(this.playerEntity) && (packet instanceof Packet13PlayerLookMove move)) {
            if (move.xPosition != 0 && move.zPosition != 0 && move.yPosition != 0) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "handleCreativeSetSlot", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_creative_set_slot(Packet107CreativeSetSlot packet107, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.playerEntity)) {
            ci.cancel();
        }
    }

    @Inject(method = "handleFlying", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/MinecraftServer;getWorldManager(I)Lnet/minecraft/server/world/WorldServer;",
            shift = At.Shift.AFTER
    ), cancellable = true)
    private void authon$cancel_handleFlying(Packet10Flying packet10Flying, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.playerEntity)) {
            if (authon$temp_solution == null) {
                authon$temp_solution = new TemporaryTape(this.playerEntity);
            }
            if (AuthonServer.CONFIG.hideCoordinates) {
                this.sendPacket(new Packet13PlayerLookMove(0, -3 + 1.62, -3, 0, 0f, 0f, false));
                this.authon$keepInLimbo = true;
                ci.cancel();
            } else {
                this.restoreCoordinates();
            }
            return;
        }

        if (AuthonServer.CONFIG.hideCoordinates && this.authon$keepInLimbo) {
            this.restoreCoordinates();
            this.authon$keepInLimbo = false;
        }

        if (authon$temp_solution != null) {
            if (AuthonServer.CONFIG.hideCoordinates) {
                this.restoreCoordinates();
            }
            authon$temp_solution = null;
        }
    }

    @Inject(method = "handleBlockDig", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_handleBlockDig(Packet14BlockDig packet14, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.playerEntity)) {
            ci.cancel();
        }
    }

    @Inject(method = "handlePlace", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_handlePlace(Packet15Place packet15Place, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.playerEntity)) {
            ci.cancel();
        }
    }

    @Inject(method = "handleBlockItemSwitch", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_handleBlockItemSwitch(Packet16BlockItemSwitch packet16BlockItemSwitch, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.playerEntity)) {
            ci.cancel();
        }
    }

//    @Inject(method = "handleNameTag", at = @At("HEAD"), cancellable = true)
//    private void authon$cancel_handleNameTag(Packet91NameTag packet91, CallbackInfo ci) {
//        if (!IPlayerAuth.isAuthenticated(this.playerEntity)) {
//            ci.cancel();
//        }
//    }

    @Inject(method = "handleEmote", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_handleEmote(Packet92Emote packet92, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.playerEntity)) {
            ci.cancel();
        }
    }

    @Inject(method = "handleChat", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_handleChat(Packet3Chat packet3Chat, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.playerEntity)) {
            String message = packet3Chat.message.trim();
            if (!message.startsWith("/login") && !message.startsWith("/register")) {
                GameUtils.informPlayer(this.playerEntity);
                ci.cancel();
            }
        }
    }

    @Inject(method = "handleArmAnimation", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_handleArmAnimation(Packet18Animation packet18Animation, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.playerEntity)) {
            ci.cancel();
        }
    }

    @Inject(method = "handleEntityActions", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_handleEntityActions(Packet19EntityAction packet19EntityAction, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.playerEntity)) {
            ci.cancel();
        }
    }

    @Inject(method = "handleUseEntity", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_handleUseEntity(Packet7UseEntity packet7, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.playerEntity)) {
            ci.cancel();
        }
    }

    @Inject(method = "handleGuiClick", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_handleGuiClick(Packet102WindowClick packet102WindowClick, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.playerEntity)) {
            ci.cancel();
        }
    }

    @Inject(method = "handleTransaction", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_handleTransaction(Packet106Transaction packet106Transaction, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.playerEntity)) {
            ci.cancel();
        }
    }

    @Inject(method = "handleSignUpdate", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_handleSignUpdate(Packet130UpdateSign packet130UpdateSign, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.playerEntity)) {
            ci.cancel();
        }
    }

    @Inject(method = "handleCuneiformBlockUpdate", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_handleCuneiformBlockUpdate(Packet133UpdateCuneiformBlock packet133, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.playerEntity)) {
            ci.cancel();
        }
    }
}
