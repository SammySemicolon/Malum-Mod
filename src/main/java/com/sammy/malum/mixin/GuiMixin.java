package com.sammy.malum.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.core.handlers.ScreenParticleHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.sammy.malum.core.systems.rendering.particle.screen.base.ScreenParticle.RenderOrder.BEFORE_TOOLTIPS;

@Mixin(Gui.class)
public class GuiMixin {
    @Inject(at = @At("HEAD"), method = "renderHotbar")
    private void malumRenderHotbarStart(float l1, PoseStack j1, CallbackInfo ci) {
        ScreenParticleHandler.renderingHotbar = true;
    }
    @Inject(at = @At("RETURN"), method = "renderHotbar")
    private void malumRenderHotbarEnd(float l1, PoseStack j1, CallbackInfo ci) {
        ScreenParticleHandler.renderingHotbar = false;
    }
}
