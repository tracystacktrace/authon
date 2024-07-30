package net.tracyex0.authon.mixins;

import net.minecraft.src.game.entity.Entity;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.tracyex0.authon.misc.IPlayerAuth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayerMP.class)
public class MixinEntityPlayerMP {
    @Inject(method = "attackEntityFrom", at = @At("HEAD"), cancellable = true)
    private void authon$secure_from_attack(Entity entity, int arg2, CallbackInfoReturnable<Boolean> cir) {
        if (!((IPlayerAuth) this).isAuthenticated()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
