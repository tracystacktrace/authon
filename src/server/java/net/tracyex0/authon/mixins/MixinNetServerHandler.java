package net.tracyex0.authon.mixins;

import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.minecraft.src.server.packets.*;
import net.tracyex0.authon.misc.GameUtils;
import net.tracyex0.authon.misc.IPlayerAuth;
import net.tracyex0.authon.misc.TemporaryTape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * literally a cancelling mixin
 * <br>
 * even like twitter didnt cancel that hard
 */
@Mixin(NetServerHandler.class)
public abstract class MixinNetServerHandler {
    @Shadow
    private EntityPlayerMP playerEntity;
    @Unique
    private TemporaryTape authon$temp_solution;

    @Shadow
    public abstract void teleportTo(double arg1, double arg3, double arg5, float arg7, float arg8);

    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    private void authon$cause_chaos(Packet packet, CallbackInfo ci) {
        if (!((IPlayerAuth) this.playerEntity).isAuthenticated() && packet instanceof Packet5PlayerInventory) {
            ci.cancel();
        }
    }

    @Inject(method = "handleCauldron", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_cauldron(Packet66Cauldron packet66Cauldron, CallbackInfo ci) {
        if (!((IPlayerAuth) this.playerEntity).isAuthenticated()) {
            ci.cancel();
        }
    }

    @Inject(method = "handleCreativeSetSlot", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_creative_set_slot(Packet107CreativeSetSlot packet107, CallbackInfo ci) {
        if (!((IPlayerAuth) this.playerEntity).isAuthenticated()) {
            ci.cancel();
        }
    }

    @Inject(method = "handleFlying", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/MinecraftServer;getWorldManager(I)Lnet/minecraft/src/game/level/WorldServer;",
            shift = At.Shift.AFTER))
    private void authon$cancel_creative_fly(
            Packet10Flying packet10Flying,
            CallbackInfo ci
    ) {
        if (!((IPlayerAuth) this.playerEntity).isAuthenticated()) {
            if (authon$temp_solution == null) {
                authon$temp_solution = new TemporaryTape(this.playerEntity);
            }
            teleportTo(
                    authon$temp_solution.x,
                    authon$temp_solution.y,
                    authon$temp_solution.z,
                    authon$temp_solution.yaw,
                    authon$temp_solution.pitch
            );
        } else if (authon$temp_solution != null) {
            authon$temp_solution = null;
        }
    }

    @Inject(method = "handleBlockDig", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_block_dig(Packet14BlockDig packet14, CallbackInfo ci) {
        if (!((IPlayerAuth) this.playerEntity).isAuthenticated()) {
            ci.cancel();
        }
    }

    @Inject(method = "handlePlace", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_place(Packet15Place packet15Place, CallbackInfo ci) {
        if (!((IPlayerAuth) this.playerEntity).isAuthenticated()) {
            ci.cancel();
        }
    }

    @Inject(method = "handleBlockItemSwitch", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_switch_block_item(Packet16BlockItemSwitch packet16BlockItemSwitch, CallbackInfo ci) {
        if (!((IPlayerAuth) this.playerEntity).isAuthenticated()) {
            ci.cancel();
        }
    }

    @Inject(method = "handleNameTag", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_nametag(Packet91NameTag packet91, CallbackInfo ci) {
        if (!((IPlayerAuth) this.playerEntity).isAuthenticated()) {
            ci.cancel();
        }
    }

    @Inject(method = "handleEmote", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_emote(Packet92Emote packet92, CallbackInfo ci) {
        if (!((IPlayerAuth) this.playerEntity).isAuthenticated()) {
            ci.cancel();
        }
    }

    @Inject(method = "handleChat", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_chat(Packet3Chat packet3Chat, CallbackInfo ci) {
        if (!((IPlayerAuth) this.playerEntity).isAuthenticated()) {
            String message = packet3Chat.message;
            if (!message.startsWith("/login") && !message.startsWith("/register")) {
                GameUtils.informPlayer(this.playerEntity);
                ci.cancel();
            }
        }
    }

    @Inject(method = "handleArmAnimation", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_arm_animation(Packet18Animation packet18Animation, CallbackInfo ci) {
        if (!((IPlayerAuth) this.playerEntity).isAuthenticated()) {
            ci.cancel();
        }
    }

    @Inject(method = "func_21001_a", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_action(Packet19EntityAction packet19EntityAction, CallbackInfo ci) {
        if (!((IPlayerAuth) this.playerEntity).isAuthenticated()) {
            ci.cancel();
        }
    }

    @Inject(method = "handleUseEntity", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_use_entity(Packet7UseEntity packet7, CallbackInfo ci) {
        if (!((IPlayerAuth) this.playerEntity).isAuthenticated()) {
            ci.cancel();
        }
    }

    @Inject(method = "handleWindowClick", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_windows(Packet102WindowClick packet102WindowClick, CallbackInfo ci) {
        if (!((IPlayerAuth) this.playerEntity).isAuthenticated()) {
            ci.cancel();
        }
    }

    @Inject(method = "func_20008_a", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_craft_transaction(Packet106Transaction packet106Transaction, CallbackInfo ci) {
        if (!((IPlayerAuth) this.playerEntity).isAuthenticated()) {
            ci.cancel();
        }
    }

    @Inject(method = "handleUpdateSign", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_sign_update(Packet130UpdateSign packet130UpdateSign, CallbackInfo ci) {
        if (!((IPlayerAuth) this.playerEntity).isAuthenticated()) {
            ci.cancel();
        }
    }
}
