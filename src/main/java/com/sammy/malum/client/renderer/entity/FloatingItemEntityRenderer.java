package com.sammy.malum.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.sammy.malum.common.entity.FloatingItemEntity;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.helpers.EasingHelper;
import team.lodestar.lodestone.setup.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.trail.TrailPoint;

import java.awt.*;
import java.util.List;

import static com.sammy.malum.MalumMod.malumPath;
import static team.lodestar.lodestone.LodestoneLib.lodestonePath;
import static team.lodestar.lodestone.handlers.RenderHandler.DELAYED_RENDER;

public class FloatingItemEntityRenderer extends EntityRenderer<FloatingItemEntity> {
    public final ItemRenderer itemRenderer;

    public FloatingItemEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }

    private static final ResourceLocation LIGHT_TRAIL = malumPath("textures/vfx/concentrated_trail.png");
    private static final RenderType TRAIL_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE_TRIANGLE.apply(LIGHT_TRAIL);

    @Override
    public void render(FloatingItemEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        List<TrailPoint> trailPoints = entity.trailPointBuilder.getTrailPoints(partialTicks);
        poseStack.pushPose();
        if (trailPoints.size() > 3) {
            float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
            float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
            float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());
            trailPoints.add(new TrailPoint(new Vec3(x, y + entity.getYOffset(partialTicks) + 0.25F, z).add(entity.getDeltaMovement().scale(1 + partialTicks))));
            poseStack.translate(-x, -y, -z);
            VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
            VertexConsumer lightBuffer = DELAYED_RENDER.getBuffer(TRAIL_TYPE);
            final MalumSpiritType spiritType = entity.getSpiritType();
            final Color primaryColor = spiritType.getPrimaryColor();
            final Color secondaryColor = spiritType.getSecondaryColor();
            for (int i = 0; i < 2; i++) {
                float size = 0.2f + i * 0.2f;
                float alpha = (0.7f - i * 0.35f);
                builder.setAlpha(alpha)
                        .renderTrail(lightBuffer, poseStack, trailPoints, f -> size, f -> builder.setAlpha(alpha * f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 2f, secondaryColor, primaryColor)))
                        .renderTrail(lightBuffer, poseStack, trailPoints, f -> 1.5f * size, f -> builder.setAlpha(alpha * f / 2f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 1.5f, secondaryColor, primaryColor)))
                        .renderTrail(lightBuffer, poseStack, trailPoints, f -> size * 2.5f, f -> builder.setAlpha(alpha * f / 4f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 1.5f, secondaryColor, primaryColor)));
            }
            poseStack.translate(x, y, z);
        }
        renderSpirit(entity, itemRenderer, partialTicks, poseStack, bufferIn, packedLightIn);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    public static void renderSpirit(FloatingItemEntity entity, ItemRenderer itemRenderer, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        ItemStack itemStack = entity.getItem();
        BakedModel model = itemRenderer.getModel(itemStack, entity.level(), null, entity.getItem().getCount());
        float yOffset = entity.getYOffset(partialTicks);
        float scale = model.getTransforms().getTransform(ItemDisplayContext.GROUND).scale.y();
        float rotation = entity.getRotation(partialTicks);
        poseStack.pushPose();
        poseStack.translate(0.0D, (yOffset + 0.25F * scale), 0.0D);
        poseStack.mulPose(Axis.YP.rotation(rotation));
        itemRenderer.render(itemStack, ItemDisplayContext.GROUND, false, poseStack, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, model);
        poseStack.popPose();
        poseStack.pushPose();
        poseStack.translate(0.0D, (yOffset + 0.5F * scale), 0.0D);
        renderSpiritGlimmer(poseStack, entity.getSpiritType(), partialTicks);
        poseStack.popPose();
    }

    public static void renderSpiritGlimmer(PoseStack poseStack, MalumSpiritType spiritType, float partialTicks) {
        Level level = Minecraft.getInstance().level;
        VertexConsumer bloom = DELAYED_RENDER.getBuffer(LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.applyAndCache(lodestonePath("textures/particle/twinkle.png")));
        VertexConsumer star = DELAYED_RENDER.getBuffer(LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.applyAndCache(malumPath("textures/particle/star.png")));
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setColor(spiritType.getPrimaryColor());
        float gameTime = level.getGameTime() + partialTicks;
        float sine = (float) Math.abs(((Math.sin((gameTime / 80f) % 360)) * 0.075f));
        float bounce = EasingHelper.weightedEasingLerp(Easing.BOUNCE_IN_OUT, (gameTime % 20) / 20f, 0.025f, 0.05f, 0.025f);
        float scale = 0.12f + sine + bounce;

        poseStack.pushPose();
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180f));
        builder.setAlpha(0.6f).renderQuad(star, poseStack, scale * 1.2f);
        builder.setAlpha(0.8f).renderQuad(bloom, poseStack, scale * 0.8f);
        builder.setAlpha(0.2f).setColor(spiritType.getSecondaryColor()).renderQuad(bloom, poseStack, scale * 1.2f);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(FloatingItemEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
