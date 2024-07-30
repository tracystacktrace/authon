package net.tracyex0.authon.mixins;

import net.minecraft.src.game.entity.player.EntityPlayer;
import net.tracyex0.authon.misc.IPlayerAuth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer implements IPlayerAuth {
    @Unique
    private boolean authon$isAuthenticated = false;

    @Override
    public boolean isAuthenticated() {
        return this.authon$isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean b) {
        this.authon$isAuthenticated = b;
    }
}
