package net.tracystacktrace.authon.mixins;

import net.minecraft.common.block.container.Container;
import net.minecraft.common.entity.player.EntityPlayer;
import net.minecraft.common.recipe.ICrafting;
import net.tracystacktrace.authon.AuthonServer;
import net.tracystacktrace.authon.tools.IPlayerAuth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Container.class)
public class MixinContainer {

    @Inject(method = "onCraftGuiOpened", at = @At("HEAD"), cancellable = true)
    private void authon$cancelInventoryData(ICrafting crafting, CallbackInfo ci) {
        if (AuthonServer.CONFIG.hideInventoryContent && (!IPlayerAuth.isAuthenticated((EntityPlayer) crafting))) {
            ci.cancel();
        }
    }
}
