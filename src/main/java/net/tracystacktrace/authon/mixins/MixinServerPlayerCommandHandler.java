package net.tracystacktrace.authon.mixins;

import net.minecraft.common.util.logging.LogAgent;
import net.minecraft.server.command.ServerPlayerCommandHandler;
import net.tracystacktrace.authon.tools.GameUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * The whole purpose of this mixin is to prevent the leak of credentials to console logger.
 * <br>
 * We don't need to have users passwords be inside .log files, do we?
 *
 * @since 1.0
 */
@Mixin(ServerPlayerCommandHandler.class)
public class MixinServerPlayerCommandHandler {
    @Redirect(method = "handleSlashCommand", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/common/util/logging/LogAgent;info(Ljava/lang/String;)V"))
    private void authon$redirectStripLogs(LogAgent instance, String s) {
        final String command = s.split(":")[1].trim();
        if (GameUtils.isAuthCommand(command)) {
            return;
        }
        instance.info(s);
    }
}
