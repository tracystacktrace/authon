package net.tracyex0.authon.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.src.game.entity.player.EntityPlayer;
import net.tracyex0.authon.misc.IPlayerAuth;

@Mixin(EntityPlayer.class)
public class MixinEntityPlayer implements IPlayerAuth {
    @Unique
    private boolean authon$isAuthenticated = false;

    @Override
    public void setAuthenticated(boolean b) {
        this.authon$isAuthenticated = b;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authon$isAuthenticated;
    }
}
