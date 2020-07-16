package net.ccbluex.noforge.injection.mixins;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.network.handshake.NetworkDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NetworkDispatcher.class)
public class MixinNetworkDispatcher {

    @Inject(method = "handleVanilla(Lnet/minecraft/network/Packet;)Z", at = @At("HEAD"), remap = false, cancellable = true)
    private void handleVanilla(Packet<?> msg, final CallbackInfoReturnable callbackInfo) {
        callbackInfo.setReturnValue(false);
    }
}