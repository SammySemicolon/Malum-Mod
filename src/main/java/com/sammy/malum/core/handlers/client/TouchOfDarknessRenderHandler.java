package com.sammy.malum.core.handlers.client;

import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.common.data.attachment.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.player.*;
import org.lwjgl.opengl.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.builder.ScreenVFXBuilder;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.shader.*;

import java.util.function.*;

public class TouchOfDarknessRenderHandler {

    public static void renderDarknessVignette(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        PoseStack poseStack = guiGraphics.pose();
        Player player = minecraft.player;
        var data = player.getData(MalumAttachmentTypes.TOUCH_OF_DARKNESS);
        if (data.touchOfDarkness == 0f) {
            return;
        }
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        float effectStrength = Easing.SINE_IN_OUT.ease(data.touchOfDarkness / TouchOfDarknessData.MAX_TOUCH_OF_DARKNESS);
        float alpha = Math.min(1, effectStrength * 5);
        float zoom = 0.5f + Math.min(0.35f, effectStrength);
        float intensity = 1f + (effectStrength > 0.5f ? (effectStrength - 0.5f) * 2.5f : 0);

        ExtendedShaderInstance shaderInstance = MalumShaders.TOUCH_OF_DARKNESS.getShaderInstance();
        shaderInstance.safeGetUniform("Speed").set(1000f);
        Consumer<Float> setZoom = f -> shaderInstance.safeGetUniform("Zoom").set(f);
        Consumer<Float> setIntensity = f -> shaderInstance.safeGetUniform("Intensity").set(f);
        ScreenVFXBuilder builder = VFXBuilders.createScreen()
                .setPositionWithWidth(screenWidth*-0.2f, screenHeight*-0.2f, screenWidth*1.4f, screenHeight*1.4f)
                .setAlpha(alpha)
                .setColor(0, 0,0)
                .setShader(shaderInstance);

        poseStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        for (int i = 0; i < 4; i++) {
            poseStack.pushPose();
            float angle = ((player.level().getGameTime() + deltaTracker.getGameTimeDeltaTicks()) / 80 + i * 2.09f) * 6.28f;
            float xOffset = Mth.sin(angle) * 20;
            float yOffset = Mth.cos(angle) * 20;
            poseStack.translate(xOffset, yOffset, 0);
            setZoom.accept(zoom);
            setIntensity.accept(intensity);
            builder.setAlpha(alpha).blit(poseStack);

            setZoom.accept(zoom * 1.25f + 0.15f);
            setIntensity.accept(intensity * 0.8f + 0.5f);
            builder.setAlpha(0.5f * alpha).blit(poseStack);
            poseStack.popPose();
        }

        RenderSystem.disableBlend();
        poseStack.popPose();

        shaderInstance.applyUniformDefaults();
    }
}
