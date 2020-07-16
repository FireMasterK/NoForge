package net.ccbluex.noforge.injection.mixins;

import io.netty.channel.ChannelHandlerContext;
import net.minecraftforge.fml.common.network.handshake.HandshakeMessageHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandshakeMessageHandler.class)
public class MixinHandshakeMessageHandler {

    @Inject(method = "userEventTriggered(Lio/netty/channel/ChannelHandlerContext;Ljava/lang/Object;)V", at = @At("HEAD"), remap = false, cancellable = true)
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt, final CallbackInfo callbackInfo) throws Exception {
        callbackInfo.cancel();
    }
}
