package com.sammy.malum.mixin;

import com.sammy.malum.core.handlers.SoulWardHandler;
import com.sammy.malum.core.handlers.TouchOfDarknessHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
final class GuiMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V"))
    private void malum$renderArmorOverlay(GuiGraphics guiGraphics, CallbackInfo ci) {
        SoulWardHandler.ClientOnly.renderSoulWard(guiGraphics, this.minecraft.getWindow());
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getSleepTimer()I"))
    private void malum$renderDarknessOverlay(GuiGraphics guiGraphics, float partialTick, CallbackInfo ci) {
        TouchOfDarknessHandler.ClientOnly.renderDarknessVignette(guiGraphics);
    }
}