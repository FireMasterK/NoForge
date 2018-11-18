package net.ccbluex.noforge.injection.mixins;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

/**
 * Copyright © 2015 - 2017 | CCBlueX | All rights reserved.
 * <p>
 * 1.8.9-Forge - By CCBlueX(Marco)
 */
@Mixin(NetworkManager.class)
public class MixinNetworkManager {

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void sendPacket(final Packet<?> packet, final CallbackInfo callbackInfo) {
        if(Minecraft.getMinecraft().isIntegratedServerRunning())
            return;

        if(packet instanceof FMLProxyPacket) {
            callbackInfo.cancel();
            return;
        }

        if(packet instanceof C17PacketCustomPayload) {
            final C17PacketCustomPayload packetCustomPayload = (C17PacketCustomPayload) packet;
            final String channelName = packetCustomPayload.getChannelName();

            if(!channelName.startsWith("MC|"))
                callbackInfo.cancel();
            else if(channelName.equalsIgnoreCase("MC|Brand")) {
                final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer()).writeString("vanilla");

                try{
                    final Field field = packetCustomPayload.getClass().getDeclaredField("field_149561_c");
                    field.setAccessible(true);
                    field.set(packetCustomPayload, packetBuffer);
                }catch(final NoSuchFieldException e) {
                    try {
                        final Field field = packetCustomPayload.getClass().getDeclaredField("data");
                        field.setAccessible(true);
                        field.set(packetCustomPayload, packetBuffer);
                    }catch(NoSuchFieldException | IllegalAccessException e1) {
                        e1.printStackTrace();
                    }
                }catch(IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
