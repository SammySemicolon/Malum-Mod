package com.sammy.malum.client.renderer.entity.nitrate;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.common.entity.nitrate.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.trail.*;

import java.awt.*;
import java.util.List;
import java.util.function.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.client.renderer.entity.FloatingItemEntityRenderer.*;
import static team.lodestar.lodestone.handlers.RenderHandler.*;

public class AbstractNitrateEntityRenderer<T extends AbstractNitrateEntity> extends EntityRenderer<T> {
    public final Function<Float, Color> primaryColor;
    public final Function<Float, Color> secondaryColor;

    public AbstractNitrateEntityRenderer(EntityRendererProvider.Context context, Function<Float, Color> primaryColor, Function<Float, Color> secondaryColor) {
        super(context);
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }

    public AbstractNitrateEntityRenderer(EntityRendererProvider.Context context, Color primaryColor, Color secondaryColor) {
        this(context, f -> primaryColor, f -> secondaryColor);
    }

    private static final ResourceLocation LIGHT_TRAIL = malumPath("textures/vfx/concentrated_trail.png");
    private static final RenderType TRAIL_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE_TRIANGLE.applyAndCache(LIGHT_TRAIL);

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        float effectScalar = entity.getVisualEffectScalar();
        List<TrailPoint> spinningTrailPoints = entity.spinningTrailPointBuilder.getTrailPoints();
        List<TrailPoint> trailPoints = entity.trailPointBuilder.getTrailPoints();
        poseStack.pushPose();
        VertexConsumer lightBuffer = DELAYED_RENDER.getBuffer(TRAIL_TYPE);
        final Vec3 motionOffset = entity.getDeltaMovement().scale(0.5f);
        float trailOffsetX = (float) (Mth.lerp(partialTicks, entity.xOld, entity.getX()) - motionOffset.x);
        float trailOffsetY = (float) (Mth.lerp(partialTicks, entity.yOld, entity.getY()) - motionOffset.y);
        float trailOffsetZ = (float) (Mth.lerp(partialTicks, entity.zOld, entity.getZ()) - motionOffset.z);
        if (spinningTrailPoints.size() > 3) {
            poseStack.translate(-trailOffsetX, -trailOffsetY, -trailOffsetZ);
            VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
            for (int i = 0; i < 2; i++) {
                float size = (0.2f + i * 0.2f) * effectScalar;
                float alpha = (0.7f - i * 0.35f) * effectScalar;
                builder.setAlpha(alpha)
                        .renderTrail(lightBuffer, poseStack, spinningTrailPoints, f -> size, f -> builder.setAlpha(alpha * f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 2f, secondaryColor.apply(f), primaryColor.apply(f))))
                        .renderTrail(lightBuffer, poseStack, spinningTrailPoints, f -> 1.5f * size, f -> builder.setAlpha(alpha * f / 2f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 1.5f, secondaryColor.apply(f), primaryColor.apply(f))))
                        .renderTrail(lightBuffer, poseStack, spinningTrailPoints, f -> size * 2.5f, f -> builder.setAlpha(alpha * f / 4f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 1.5f, secondaryColor.apply(f), primaryColor.apply(f))));
            }
            poseStack.translate(trailOffsetX, trailOffsetY, trailOffsetZ);
        }
        if (trailPoints.size() > 3) {
            poseStack.translate(-trailOffsetX, -trailOffsetY, -trailOffsetZ);
            VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
            for (int i = 0; i < 2; i++) {
                float size = (0.4f + i * 0.4f) * effectScalar;
                float alpha = (0.7f - i * 0.35f) * effectScalar;
                builder.setAlpha(alpha)
                        .renderTrail(lightBuffer, poseStack, trailPoints, f -> size, f -> builder.setAlpha(alpha * f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 2f, secondaryColor.apply(f), primaryColor.apply(f))))
                        .renderTrail(lightBuffer, poseStack, trailPoints, f -> 1.5f * size, f -> builder.setAlpha(alpha * f / 2f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 1.5f, secondaryColor.apply(f), primaryColor.apply(f))))
                        .renderTrail(lightBuffer, poseStack, trailPoints, f -> size * 2.5f, f -> builder.setAlpha(alpha * f / 4f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 1.5f, secondaryColor.apply(f), primaryColor.apply(f))));
            }
            poseStack.translate(trailOffsetX, trailOffsetY, trailOffsetZ);
        }
        if (entity.age > 1 && !entity.fadingAway) {
            float glimmerScale = 3f * Math.min(1f, (entity.age-2) / 5f);
            poseStack.scale(glimmerScale, glimmerScale, glimmerScale);
            renderSpiritGlimmer(poseStack, primaryColor.apply(0f), secondaryColor.apply(0.125f), partialTicks);
        }
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
