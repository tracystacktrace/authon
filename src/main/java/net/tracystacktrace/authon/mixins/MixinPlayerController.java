package net.tracystacktrace.authon.mixins;

import net.minecraft.common.entity.player.EntityPlayer;
import net.minecraft.common.item.ItemStack;
import net.minecraft.common.world.World;
import net.minecraft.server.entity.player.PlayerController;
import net.tracystacktrace.authon.tools.IPlayerAuth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * In order to prevent the illegal actions and block the user until auth is complete,
 * this mixin class ends up cancelling action events.
 *
 * @since 1.0
 */
@Mixin(PlayerController.class)
public class MixinPlayerController {
    @Shadow
    public EntityPlayer player;

    @Inject(method = "toggleGameType", at = @At("HEAD"), cancellable = true)
    public void authon$cancel_toggleGameType(int i, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.player)) {
            ci.cancel();
        }
    }

    @Inject(method = "func_35695_b", at = @At("HEAD"), cancellable = true)
    public void authon$cancel_func_35695_b(int i, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.player)) {
            ci.cancel();
        }
    }

    @Inject(method = "updateBlockRemoving", at = @At("HEAD"), cancellable = true)
    public void authon$cancel_updateBlockRemoving(CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.player)) {
            ci.cancel();
        }
    }

    @Inject(method = "blockClicked", at = @At("HEAD"), cancellable = true)
    public void authon$cancel_blockClicked(int x, int y, int z, int facing, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.player)) {
            ci.cancel();
        }
    }

    @Inject(method = "blockRemoving", at = @At("HEAD"), cancellable = true)
    public void authon$cancel_blockRemoving(int x, int y, int z, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.player)) {
            ci.cancel();
        }
    }

    @Inject(method = "removeBlock", at = @At("HEAD"), cancellable = true)
    public void authon$cancel_removeBlock(int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (!IPlayerAuth.isAuthenticated(this.player)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "harvestBlock", at = @At("HEAD"), cancellable = true)
    public void authon$cancel_harvestBlock(int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (!IPlayerAuth.isAuthenticated(this.player)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "itemUsed", at = @At("HEAD"), cancellable = true)
    public void authon$cancel_itemUsed(EntityPlayer entityplayer, World world, ItemStack itemstack, CallbackInfoReturnable<Boolean> cir) {
        if (!IPlayerAuth.isAuthenticated(this.player)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "activeBlockOrUseItem", at = @At("HEAD"), cancellable = true)
    public void authon$cancel_activeBlockOrUseItem(EntityPlayer player, World world, ItemStack itemstack, int x, int y, int z, int facing, float xVec, float yVec, float zVec, CallbackInfoReturnable<Boolean> cir) {
        if (!IPlayerAuth.isAuthenticated(this.player)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "cancelDestroyingBlock", at = @At("HEAD"), cancellable = true)
    private void authon$cancel_cancelDestroyingBlock(int x, int y, int z, CallbackInfo ci) {
        if (!IPlayerAuth.isAuthenticated(this.player)) {
            ci.cancel();
        }
    }
}
