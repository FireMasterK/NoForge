package net.ccbluex.noforge.injection.mixins;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Copyright © 2015 - 2017 | CCBlueX | All rights reserved.
 *
 * 1.8.9-Forge - By CCBlueX(Marco)
 */
@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Shadow @Final private static Logger logger;

    @Inject(method = "startGame", at = @At("HEAD"))
    private void startGame(final CallbackInfo callbackInfo) {
        logger.info("NoForge by CCBlueX");
    }
}