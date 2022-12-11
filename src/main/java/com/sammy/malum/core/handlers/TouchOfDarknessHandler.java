package com.sammy.malum.core.handlers;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.core.setup.client.ShaderRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.ForgeIngameGui;
import org.lwjgl.opengl.GL11;
import team.lodestar.lodestone.setup.LodestoneShaderRegistry;
import team.lodestar.lodestone.systems.rendering.ExtendedShaderInstance;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.glDisable;

public class TouchOfDarknessHandler {

    public static class ClientOnly {
        private static final Tesselator INSTANCE = new Tesselator();

        public static void renderVignette(ForgeIngameGui gui, PoseStack poseStack, int width, int height) {
            renderTextureOverlay(gui, poseStack);
        }

        protected static void renderTextureOverlay(ForgeIngameGui gui, PoseStack poseStack) {
            Minecraft instance = Minecraft.getInstance();
            int width = instance.getWindow().getGuiScaledWidth();
            int height = instance.getWindow().getGuiScaledHeight();

//            float effectStrength = 0.4f;
            float effectStrength = (instance.level.getGameTime()%200)/200f;

            float alpha = Math.min(1, effectStrength*5);
            float zoom = 0.5f + Math.min(0.35f, effectStrength);
            float intensity = 1f + (effectStrength > 0.5f ? (effectStrength-0.5f)*4f : 0);

            ExtendedShaderInstance shaderInstance = (ExtendedShaderInstance) ShaderRegistry.TOUCH_OF_DARKNESS.getInstance().get();
            shaderInstance.safeGetUniform("Speed").set(1000f);
            Consumer<Float> setZoom = f -> shaderInstance.safeGetUniform("Zoom").set(f);
            Consumer<Float> setIntensity = f -> shaderInstance.safeGetUniform("Intensity").set(f);
            VFXBuilders.ScreenVFXBuilder builder = VFXBuilders.createScreen()
                    .setPosColorTexDefaultFormat()
                    .setPositionWithWidth(0, 0, width, height)
                    .overrideBufferBuilder(INSTANCE.getBuilder())
                    .setColor(0, 0, 0)
                    .setAlpha(alpha)
                    .setShader(ShaderRegistry.TOUCH_OF_DARKNESS.getInstance());

            poseStack.pushPose();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            setZoom.accept(zoom);
            setIntensity.accept(intensity);
            builder.draw(poseStack);

            setZoom.accept(zoom*1.25f+0.15f);
            setIntensity.accept(intensity*1.25f+0.5f);
            builder.setAlpha(0.5f*alpha).draw(poseStack);

            RenderSystem.disableBlend();
            poseStack.popPose();

            shaderInstance.setUniformDefaults();
        }
    }
}