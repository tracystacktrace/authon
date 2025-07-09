package net.tracystacktrace.authon.mixins;

import net.minecraft.common.entity.player.EntityPlayer;
import net.tracystacktrace.authon.tools.IPlayerAuth;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/**
 * This mixin simply implements a custom interface {@link IPlayerAuth},
 * so it'd be easier to process some data and keep variables.
 *
 * @since 1.0
 */
@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer implements IPlayerAuth {
    @Shadow
    public String username;

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

    @Override
    public @NotNull String getLoginUsername() {
        return this.username;
    }
}
