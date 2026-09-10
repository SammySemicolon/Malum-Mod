package com.sammy.malum.core.handlers.client;

import com.mojang.blaze3d.systems.*;
import com.sammy.malum.*;
import com.sammy.malum.common.data.attachment.*;
import com.sammy.malum.config.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.neoforged.neoforge.client.event.*;
import org.lwjgl.opengl.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;

import java.lang.Math;

public class MalignantAegisRenderHandler {

    public static ResourceLocation REINFORCEMENT = MalumMod.malumPath("textures/gui/hud/malignant_aegis.png");
    public static ResourceLocation GLOW = MalumMod.malumPath("textures/gui/hud/malignant_aegis_glow.png");
    public static ResourceLocation EMPTY = MalumMod.malumPath("textures/gui/hud/malignant_aegis_empty.png");
    public static ResourceLocation DISSOLVEMENT = MalumMod.malumPath("textures/gui/hud/malignant_aegis_dissolvement.png");

    public static int glow;
    public static int fadeout;

    public static float displayedReinforcement;

    public static void tick(ClientTickEvent event) {
        var player = Minecraft.getInstance().player;
        if (player != null) {
            MalignantInfluenceData.getMalignantAegisData(player).ifPresent(data -> {
                double capacity = MalignantInfluenceData.getMalignantAegisCapacity(player);
                double currentReinforcement = data.getMalignantAegis();
                if (currentReinforcement >= capacity) {
                    if (glow < 80) {
                        glow++;
                    }
                } else {
                    if (glow > 0) {
                        glow--;
                    }
                }
                if (displayedReinforcement - currentReinforcement > 0.01f) {
                    glow = 40;
                }
                displayedReinforcement = Mth.lerp(0.2f, displayedReinforcement, (float) currentReinforcement);
                if (currentReinforcement > 0 && currentReinforcement < capacity) {
                    if (fadeout > 0) {
                        fadeout = Math.max(0, fadeout - 10);
                    }
                } else {
                    if (fadeout < 80) {
                        fadeout++;
                    }
                }
            });
        }
    }

    public static void renderMalignantAegis(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        var minecraft = Minecraft.getInstance();
        var poseStack = guiGraphics.pose();
        if (!minecraft.options.hideGui) {
            var player = minecraft.player;
            if (!player.isCreative() && !player.isSpectator()) {
                double capacity = MalignantInfluenceData.getMalignantAegisCapacity(player);
                if (displayedReinforcement > 0 && capacity > 0) {
                    float delta = (float) (displayedReinforcement / capacity);
                    float dissolvement = Easing.QUAD_OUT.ease(delta);
                    float alpha = (1 - fadeout / 80f) * 0.75f;
                    int left = guiGraphics.guiWidth() / 2 - ClientConfig.UI_SHIELD_X_OFFSET.getConfigValue();
                    int top = guiGraphics.guiHeight() - ClientConfig.UI_SHIELD_Y_OFFSET.getConfigValue();

                    poseStack.pushPose();
                    RenderSystem.depthMask(true);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    var distorted = LodestoneShaders.SCREEN_DISTORTED_TEXTURE.getShaderInstance();
                    distorted.safeGetUniform("YFrequency").set(24f);
                    distorted.safeGetUniform("XFrequency").set(16f);
                    distorted.safeGetUniform("Speed").set(1000f);
                    distorted.safeGetUniform("Intensity").set(80f);
                    distorted.safeGetUniform("Width").set(64f);
                    distorted.safeGetUniform("Height").set(64f);

                    var builder = VFXBuilders.createScreen().setZLevel(100).setShader(distorted);
                    builder.setPositionWithWidth(left - 16, top - 16, 32, 32);
                    builder.setAlpha(alpha).setTexture(EMPTY).blit(poseStack);

                    var hud = MalumShaders.DISSOLVING_HUD_ELEMENT.getShaderInstance();
                    RenderSystem.setShaderTexture(1, DISSOLVEMENT);
                    hud.safeGetUniform("YFrequency").set(24f);
                    hud.safeGetUniform("XFrequency").set(16f);
                    hud.safeGetUniform("Speed").set(1000f);
                    hud.safeGetUniform("Intensity").set(80f);
                    hud.safeGetUniform("Dissolvement").set(dissolvement);
                    hud.safeGetUniform("Width").set(64f);
                    hud.safeGetUniform("Height").set(64f);

                    builder.setShader(hud).setTexture(REINFORCEMENT).blit(poseStack);
                    RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
                    builder.setAlpha(0.2f * alpha).blit(poseStack);
                    if (glow > 0 && glow < 80) {
                        float time = minecraft.level.getGameTime() + deltaTracker.getGameTimeDeltaPartialTick(true);
                        float glowAlpha = (40 - Math.abs(40 - glow)) / 40f;
                        int angle = Mth.floor((time * 20) % 360);
                        float range = Easing.SINE_IN_OUT.lerp(glowAlpha, 160f, 320f);
                        var light = LodestoneShaders.RADIAL_DISTORTED_SCREEN_LIGHT.getShaderInstance();
                        light.safeGetUniform("YFrequency").set(24f);
                        light.safeGetUniform("XFrequency").set(16f);
                        light.safeGetUniform("Speed").set(1000f);
                        light.safeGetUniform("Intensity").set(80f);
                        light.safeGetUniform("Width").set(64f);
                        light.safeGetUniform("Height").set(64f);
                        light.safeGetUniform("Angle").set(angle);
                        light.safeGetUniform("LightAngleRange").set(range);
                        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
                        builder.setShader(light).setAlpha(alpha);
                        if (displayedReinforcement >= capacity) {
                            builder.setTexture(REINFORCEMENT).blit(poseStack);
                        }
                        builder.setTexture(GLOW).blit(poseStack);
                        light.applyUniformDefaults();
                    }
                    distorted.applyUniformDefaults();
                    hud.applyUniformDefaults();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.disableBlend();
                    poseStack.popPose();
                }
            }
        }
    }
}
