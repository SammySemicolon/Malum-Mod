package com.sammy.malum.client.screen.codex.display.gizmo;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.client.screen.codex.display.IGizmoHolder;
import com.sammy.malum.client.screen.codex.screens.AbstractMalumCodexScreen;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.magic.MalumSpiritTypes;
import net.minecraft.client.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.modules.core.easing.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;

public class DisplayedSpiritSymbol extends DisplayedGizmo {

    protected final SpiritArcanaType spirit;

    public static DisplayedSpiritSymbol spirit(SpiritLike spirit) {
        return new DisplayedSpiritSymbol(spirit);
    }

    public DisplayedSpiritSymbol(SpiritLike spirit) {
        this.spirit = spirit.getSpirit();
        width = 64;
        height = 64;
    }

    @Override
    public void renderDecals(AbstractMalumCodexScreen screen, IGizmoHolder holder, GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY, float partialTicks) {
        var data = spirit.getTextureData();
        var minecraft = Minecraft.getInstance();
        var time = minecraft.level.getGameTime();

        var distorted = LodestoneShaders.SCREEN_DISTORTED_TEXTURE.getShaderInstance();
        var builder = VFXBuilders.createScreen().setShader(distorted)
                .setTexture(data.getSymbolTexture())
                .setZLevel(200);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        for (int i = 0; i <= 6; i++) {
            float delta = i / 6f;
            float alpha = Easing.SINE_IN_OUT.lerp(delta, 0.75f, 0.1f);
            float distance = Easing.SINE_IN_OUT.lerp(delta, 2.5f, 0.25f);
            float rate = Easing.EXPO_IN_OUT.lerp(delta, 0.75f, 0.25f);
            float xFrequency = Easing.CUBIC_IN_OUT.lerp(delta, 24f, 4f);
            float yFrequency = Easing.SINE_IN_OUT.lerp(delta, 8f, 64f);
            float distortionIntensity = Easing.EXPO_IN_OUT.lerp(delta, 60f, 15f);
            float distortionRate = rate * 1000f;
            float angle = ((time * rate) % 80) / 40 * Mth.PI;
            float xOffset = Mth.sin(angle) * distance;
            float yOffset = Mth.cos(angle) * distance;
            var color = ColorHelper.colorLerp(Easing.SINE_IN_OUT, delta, spirit.getPrimaryColor(), spirit.getSecondaryColor());


            distorted.safeGetUniform("YFrequency").set(xFrequency);
            distorted.safeGetUniform("XFrequency").set(yFrequency);
            distorted.safeGetUniform("Speed").set(distortionRate);
            distorted.safeGetUniform("Intensity").set(distortionIntensity);
            distorted.safeGetUniform("Width").set(64f);
            distorted.safeGetUniform("Height").set(64f);

            builder.setColor(color, alpha)
                    .setPositionWithWidth(x+xOffset, y+yOffset, width, height)
                    .blit(guiGraphics);
        }

//        if (isHoveredOver) {
//            float alphaScale = color.getRed() / 255f;
//            builder
//                    .setColor(MalumSpiritTypes.ARCANE_COLORS().primaryColor())
//                    .multiplyColor(color.getRed(), color.getBlue(), color.getGreen())
//                    .setAlpha(0.3f * alphaScale)
//                    .blit(stack);
//            RenderSystem.defaultBlendFunc();
//        }
    }

    @Override
    public void gatherTooltip(IGizmoHolder holder, GizmoTooltipBuilder tooltip) {
        var textData = spirit.getTextData();
        textData.addToCodexTooltip(tooltip);
    }
}