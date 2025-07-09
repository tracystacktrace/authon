package net.tracystacktrace.authon.mixins;

import net.minecraft.common.entity.player.EntityPlayer;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.tracystacktrace.authon.AuthonServer;
import net.tracystacktrace.authon.tools.IPlayerAuth;
import net.tracystacktrace.authon.tools.ITapeHolder;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.concurrent.ScheduledFuture;

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

    @Unique
    private ScheduledFuture<?> authon$personalTimeout;

    @Override
    public boolean isAuthenticated() {
        return this.authon$isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean value) {
        this.authon$isAuthenticated = value;
        this.cancelTimeout();
        if (AuthonServer.CONFIG.hideInventoryContent && value) {
            EntityPlayerMP.class.cast(this).func_20057_k();
        }
        if (AuthonServer.CONFIG.hideCoordinates && value) {
            ((ITapeHolder) EntityPlayerMP.class.cast(this).playerNetServerHandler).restoreCoordinates();
        }

        //force update inventory
        EntityPlayerMP.class.cast(this).currentContainer.updateInventory();
    }

    @Override
    public @NotNull String getLoginUsername() {
        return this.username;
    }

    @Override
    public void setTimeout(ScheduledFuture<?> timeout) {
        this.authon$personalTimeout = timeout;
    }

    @Override
    public void cancelTimeout() {
        if (this.authon$personalTimeout != null) {
            this.authon$personalTimeout.cancel(true);
        }
    }
}
