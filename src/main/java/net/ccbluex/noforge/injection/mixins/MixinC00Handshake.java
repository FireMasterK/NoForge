package net.ccbluex.noforge.injection.mixins;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.client.C00Handshake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.IOException;

/**
 * Copyright © 2015 - 2017 | CCBlueX | All rights reserved.
 *
 * NoForge - By CCBlueX(Marco)
 */
@Mixin(C00Handshake.class)
public class MixinC00Handshake {

    @Shadow private int protocolVersion;

    @Shadow private String ip;

    @Shadow private int port;

    @Shadow private EnumConnectionState requestedState;

    /**
     * @author
     * Writes the raw packet data to the data stream.
     */
    @Overwrite
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeVarIntToBuffer(this.protocolVersion);
        buf.writeString(this.ip);
        buf.writeShort(this.port);
        buf.writeVarIntToBuffer(this.requestedState.getId());
    }
}
