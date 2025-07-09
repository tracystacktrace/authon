package net.tracystacktrace.authon.tools;

import net.minecraft.common.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

public class TemporaryTape {
    public final double x;
    public final double y;
    public final double z;
    public final float yaw;
    public final float pitch;

    public TemporaryTape(@NotNull EntityPlayer entityPlayer) {
        this.x = entityPlayer.posX;
        this.y = entityPlayer.posY;
        this.z = entityPlayer.posZ;
        this.yaw = entityPlayer.rotationYaw;
        this.pitch = entityPlayer.rotationPitch;
    }
}
