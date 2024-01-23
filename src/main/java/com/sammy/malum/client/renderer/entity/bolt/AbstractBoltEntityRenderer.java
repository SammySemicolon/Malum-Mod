package com.sammy.malum.client.renderer.entity.bolt;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.common.entity.bolt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.trail.*;

import java.awt.*;
import java.lang.*;
import java.util.List;

import static com.sammy.malum.MalumMod.*;
import static team.lodestar.lodestone.handlers.RenderHandler.*;

public abstract class AbstractBoltEntityRenderer<T extends AbstractBoltProjectileEntity> extends EntityRenderer<T> {
    public final Color primaryColor;
    public final Color secondaryColor;
    public AbstractBoltEntityRenderer(EntityRendererProvider.Context context, Color primaryColor, Color secondaryColor) {
        super(context);
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }

    private static final ResourceLocation LIGHT_TRAIL = malumPath("textures/vfx/concentrated_trail.png");
    private static final RenderType TRAIL_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE_TRIANGLE.applyAndCache(LIGHT_TRAIL);

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        if (entity.spawnDelay > 0) {
            return;
        }
        float effectScalar = entity.getVisualEffectScalar();
        List<TrailPoint> spinningTrailPoints = entity.spinningTrailPointBuilder.getTrailPoints();
        List<TrailPoint> trailPoints = entity.trailPointBuilder.getTrailPoints();
        poseStack.pushPose();
        VertexConsumer lightBuffer = DELAYED_RENDER.getBuffer(TRAIL_TYPE);
        float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
        float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
        float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());
        if (spinningTrailPoints.size() > 3) {
            poseStack.translate(-x, -y, -z);
            VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
            for (int i = 0; i < 2; i++) {
                float size = (0.2f + i * 0.2f) * effectScalar;
                float alpha = (0.7f - i * 0.35f) * effectScalar;
                builder.setAlpha(alpha)
                        .renderTrail(lightBuffer, poseStack, spinningTrailPoints, f -> size, f -> builder.setAlpha(alpha * f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 2f, secondaryColor, primaryColor)))
                        .renderTrail(lightBuffer, poseStack, spinningTrailPoints, f -> 1.5f * size, f -> builder.setAlpha(alpha * f / 2f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 1.5f, secondaryColor, primaryColor)))
                        .renderTrail(lightBuffer, poseStack, spinningTrailPoints, f -> size * 2.5f, f -> builder.setAlpha(alpha * f / 4f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 1.5f, secondaryColor, primaryColor)));
            }
            poseStack.translate(x, y, z);
        }
        if (trailPoints.size() > 3) {
            poseStack.translate(-x, -y, -z);
            VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
            for (int i = 0; i < 2; i++) {
                float size = (0.3f + i * 0.3f) * effectScalar;
                float alpha = (0.7f - i * 0.35f) * effectScalar;
                builder.setAlpha(alpha)
                        .renderTrail(lightBuffer, poseStack, trailPoints, f -> size, f -> builder.setAlpha(alpha * f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 2f, secondaryColor, primaryColor)))
                        .renderTrail(lightBuffer, poseStack, trailPoints, f -> 1.5f * size, f -> builder.setAlpha(alpha * f / 2f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 1.5f, secondaryColor, primaryColor)))
                        .renderTrail(lightBuffer, poseStack, trailPoints, f -> size * 2.5f, f -> builder.setAlpha(alpha * f / 4f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 1.5f, secondaryColor, primaryColor)));
            }
        }

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
