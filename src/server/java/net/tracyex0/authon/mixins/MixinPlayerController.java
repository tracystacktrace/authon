package net.tracyex0.authon.mixins;

import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.level.World;
import net.minecraft.src.server.player.PlayerController;
import net.tracyex0.authon.misc.IPlayerAuth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerController.class)
public class MixinPlayerController {
    @Shadow
    public EntityPlayer player;

    @Inject(method = "toggleGameType", at = @At("HEAD"), cancellable = true)
    public void authon$toggle_1(int i, CallbackInfo ci) {
        if (!((IPlayerAuth) this.player).isAuthenticated()) {
            ci.cancel();
        }
    }

    @Inject(method = "func_35695_b", at = @At("HEAD"), cancellable = true)
    public void authon$toggle_2(int i, CallbackInfo ci) {
        if (!((IPlayerAuth) this.player).isAuthenticated()) {
            ci.cancel();
        }
    }

    @Inject(method = "updateBlockRemoving", at = @At("HEAD"), cancellable = true)
    public void authon$toggle_3(CallbackInfo ci) {
        if (!((IPlayerAuth) this.player).isAuthenticated()) {
            ci.cancel();
        }
    }

    @Inject(method = "blockClicked", at = @At("HEAD"), cancellable = true)
    public void authon$toggle_4(int x, int y, int z, int facing, CallbackInfo ci) {
        if (!((IPlayerAuth) this.player).isAuthenticated()) {
            ci.cancel();
        }
    }

    @Inject(method = "blockRemoving", at = @At("HEAD"), cancellable = true)
    public void authon$toggle_5(int x, int y, int z, CallbackInfo ci) {
        if (!((IPlayerAuth) this.player).isAuthenticated()) {
            ci.cancel();
        }
    }

    @Inject(method = "removeBlock", at = @At("HEAD"), cancellable = true)
    public void authon$toggle_6(int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (!((IPlayerAuth) this.player).isAuthenticated()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "harvestBlock", at = @At("HEAD"), cancellable = true)
    public void authon$toggle_7(int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (!((IPlayerAuth) this.player).isAuthenticated()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "itemUsed", at = @At("HEAD"), cancellable = true)
    public void authon$toggle_8(EntityPlayer entityplayer, World world, ItemStack itemstack, CallbackInfoReturnable<Boolean> cir) {
        if (!((IPlayerAuth) this.player).isAuthenticated()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "activeBlockOrUseItem", at = @At("HEAD"), cancellable = true)
    public void authon$toggle_9(EntityPlayer player, World world, ItemStack itemstack, int x, int y, int z, int facing, float xVec, float yVec, float zVec, CallbackInfoReturnable<Boolean> cir) {
        if (!((IPlayerAuth) this.player).isAuthenticated()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
