package net.tracyex0.authon.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.src.game.entity.player.EntityPlayer;
import net.tracyex0.authon.misc.IPlayerAuth;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public class MixinEntityPlayer implements IPlayerAuth {
    @Unique
    private boolean authon$isAuthenticated = false;

    @Unique
    private int authon$timeoutchat = 0;

    @Inject(method = "onUpdate", at = @At("TAIL"))
    private void authon$update_timeout(CallbackInfo ci) {
        if(authon$timeoutchat > 0) authon$timeoutchat--;
    }

    @Override
    public void setAuthenticated(boolean b) {
        this.authon$isAuthenticated = b;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authon$isAuthenticated;
    }

    @Override
    public void setChatTimeout(int i) {
        this.authon$timeoutchat = i;
    }

    @Override
    public boolean hasTimeCome() {
        return authon$timeoutchat < 1;
    }
}
