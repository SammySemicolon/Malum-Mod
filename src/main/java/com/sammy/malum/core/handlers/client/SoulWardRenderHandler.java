package com.sammy.malum.core.handlers.client;

import com.mojang.blaze3d.systems.*;
import com.sammy.malum.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.neoforged.neoforge.client.event.*;
import org.lwjgl.opengl.*;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;

import java.lang.Math;

public class SoulWardRenderHandler {

    public static ResourceLocation SOUL_WARD = MalumMod.malumPath("textures/gui/hud/soul_ward.png");
    public static ResourceLocation MASK = MalumMod.malumPath("textures/gui/hud/soul_ward_mask.png");

    private static float activeDelta;
    private static float glowDelta;

    public static int glow;
    public static int fadeout;

    private static double oldSoulWard;
    private static double displayedSoulWard;

    public static void tick(ClientTickEvent.Pre event) {
        var player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if (!player.hasData(MalumAttachmentTypes.SOUL_WARD)) {
            return;
        }
        var data = player.getData(MalumAttachmentTypes.SOUL_WARD);
        double capacity = player.getAttributeValue(MalumAttributes.SOUL_WARD_CAPACITY);
        if (capacity <= 0) {
            activeDelta = DataHelper.approach(activeDelta, 0, 0.01f);
            glowDelta = 0;
            return;
        }
        activeDelta = DataHelper.approach(activeDelta, 1, 0.025f);
        double soulWard = data.getSoulWard();

        if (oldSoulWard != soulWard) {
            glowDelta = 1;
            oldSoulWard = soulWard;
        }
        displayedSoulWard = DataHelper.approach(displayedSoulWard, soulWard, 0.01);
        displayedSoulWard = Mth.lerp(0.05, displayedSoulWard, soulWard);
        if (glowDelta > 0) {
            glowDelta = DataHelper.approach(glowDelta, 0, 0.025f);
        }
    }

    public static void renderSoulWard(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        var minecraft = Minecraft.getInstance();
        var poseStack = guiGraphics.pose();
        if (minecraft.options.hideGui) {
            return;
        }
        var player = minecraft.player;
        assert player != null;
        if (player.isCreative() || player.isSpectator()) {
            return;
        }
        double capacity = player.getAttributeValue(MalumAttributes.SOUL_WARD_CAPACITY);
        if (displayedSoulWard <= 0 || capacity <= 0) {
            return;
        }
        float delta = (float) (displayedSoulWard / capacity);
        float dissolve = 1 - Easing.QUAD_IN.ease(1 - delta);
        float alpha = Easing.SINE_IN_OUT.lerp(delta, 0.75f, 1f);

        int left = guiGraphics.guiWidth() / 2 - 14;
        int top = guiGraphics.guiHeight() - minecraft.gui.leftHeight + 24;

        poseStack.pushPose();
        RenderSystem.depthMask(true);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        var hud = MalumShaders.DISSOLVING_HUD_ELEMENT.getShaderInstance();
        RenderSystem.setShaderTexture(1, MASK);
        hud.safeGetUniform("YFrequency").set(24f);
        hud.safeGetUniform("XFrequency").set(16f);
        hud.safeGetUniform("Speed").set(1000f);
        hud.safeGetUniform("Intensity").set(80f);
        hud.safeGetUniform("Dissolvement").set(dissolve);
        hud.safeGetUniform("Width").set(64f);
        hud.safeGetUniform("Height").set(64f);

        var builder = VFXBuilders.createScreen().setShader(hud);
        builder.setPositionWithWidth(left - 16, top - 16, 32, 32);
        builder.setAlpha(alpha).setTexture(SOUL_WARD).blit(poseStack);


//                    RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
//                    builder.setAlpha(0.2f * alpha).blit(poseStack);
//                    if (glow > 0 && glow < 40) {
//                        float time = minecraft.level.getGameTime() + deltaTracker.getGameTimeDeltaPartialTick(true);
//                        float glowAlpha = (20 - Math.abs(20 - glow)) / 20f;
//                        int angle = Mth.floor((time * 20) % 360);
//                        float range = Easing.SINE_IN_OUT.ease(glowAlpha) * 320f;
//                        var light = LodestoneShaders.RADIAL_DISTORTED_SCREEN_LIGHT.getShaderInstance();
//                        light.safeGetUniform("YFrequency").set(24f);
//                        light.safeGetUniform("XFrequency").set(16f);
//                        light.safeGetUniform("Speed").set(1000f);
//                        light.safeGetUniform("Intensity").set(80f);
//                        light.safeGetUniform("Width").set(64f);
//                        light.safeGetUniform("Height").set(64f);
//                        light.safeGetUniform("Angle").set(angle);
//                        light.safeGetUniform("LightAngleRange").set(range);
//                        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
//                        builder.setShader(light).setAlpha(alpha);
//                        if (displayedSoulWard >= capacity) {
//                            builder.setTexture(SOUL_WARD).blit(poseStack);
//                        }
//                        builder.setTexture(GLOW).blit(poseStack);
//                        light.applyUniformDefaults();
//                    }
        hud.applyUniformDefaults();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        poseStack.popPose();
    }
}
