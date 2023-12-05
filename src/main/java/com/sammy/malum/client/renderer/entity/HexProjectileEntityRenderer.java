package com.sammy.malum.client.renderer.entity;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.common.entity.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.phys.*;
import org.joml.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;
import team.lodestar.lodestone.systems.rendering.trail.*;

import java.awt.*;
import java.lang.*;
import java.lang.Math;
import java.util.List;

import static com.sammy.malum.MalumMod.*;
import static team.lodestar.lodestone.handlers.RenderHandler.*;

public class HexProjectileEntityRenderer extends EntityRenderer<HexProjectileEntity> {
    public final ItemRenderer itemRenderer;

    public HexProjectileEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }

    private static final ResourceLocation LIGHT_TRAIL = malumPath("textures/vfx/concentrated_trail.png");
    private static final RenderType TRAIL_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE_TRIANGLE.apply(LIGHT_TRAIL);

    @Override
    public void render(HexProjectileEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        if (entity.spawnDelay > 0) {
            return;
        }
        float effectScalar = entity.fadingAway ? 1 - (entity.age - HexProjectileEntity.MAX_AGE + 10) / 10f : 1;
        List<TrailPoint> spinningTrailPoints = entity.spinningTrailPointBuilder.getTrailPoints(partialTicks);
        List<TrailPoint> trailPoints = entity.trailPointBuilder.getTrailPoints(partialTicks);
        poseStack.pushPose();
        final MalumSpiritType spirit = SpiritTypeRegistry.WICKED_SPIRIT;
        if (spinningTrailPoints.size() > 3) {
            float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
            float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
            float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());
            poseStack.translate(-x, -y, -z);
            VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
            VertexConsumer lightBuffer = DELAYED_RENDER.getBuffer(TRAIL_TYPE);
            final Color primaryColor = spirit.getPrimaryColor();
            final Color secondaryColor = spirit.getSecondaryColor();
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
            float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
            float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
            float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());
            trailPoints.add(new TrailPoint(new Vec3(x, y, z).add(entity.getDeltaMovement().scale(1 + partialTicks))));
            poseStack.translate(-x, -y, -z);
            VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
            VertexConsumer lightBuffer = DELAYED_RENDER.getBuffer(TRAIL_TYPE);
            final Color primaryColor = spirit.getPrimaryColor();
            final Color secondaryColor = spirit.getSecondaryColor();
            for (int i = 0; i < 2; i++) {
                float size = (0.3f + i * 0.3f) * effectScalar;
                float alpha = (0.7f - i * 0.35f) * effectScalar;
                builder.setAlpha(alpha)
                        .renderTrail(lightBuffer, poseStack, trailPoints, f -> size, f -> builder.setAlpha(alpha * f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 2f, secondaryColor, primaryColor)))
                        .renderTrail(lightBuffer, poseStack, trailPoints, f -> 1.5f * size, f -> builder.setAlpha(alpha * f / 2f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 1.5f, secondaryColor, primaryColor)))
                        .renderTrail(lightBuffer, poseStack, trailPoints, f -> size * 2.5f, f -> builder.setAlpha(alpha * f / 4f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 1.5f, secondaryColor, primaryColor)));
            }
            poseStack.translate(x, y, z);
        }

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(HexProjectileEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
