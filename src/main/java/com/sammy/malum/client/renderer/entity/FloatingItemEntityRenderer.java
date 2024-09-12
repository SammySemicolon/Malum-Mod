package com.sammy.malum.client.renderer.entity;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.client.*;
import com.sammy.malum.common.entity.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.rendering.*;

import java.awt.*;

public class FloatingItemEntityRenderer extends EntityRenderer<FloatingItemEntity> {
    public final ItemRenderer itemRenderer;

    public FloatingItemEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }

    private static final LodestoneRenderType TRAIL_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE_TRIANGLE.applyAndCache(MalumRenderTypeTokens.CONCENTRATED_TRAIL);

    private static final LodestoneRenderType TWINKLE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.applyAndCache(MalumRenderTypeTokens.TWINKLE);
    private static final LodestoneRenderType STAR = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.applyAndCache(MalumRenderTypeTokens.STAR);

    @Override
    public void render(FloatingItemEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        final MalumSpiritType spiritType = entity.getSpiritType();
        VFXBuilders.WorldVFXBuilder trailBuilder = SpiritBasedWorldVFXBuilder.create(spiritType).setRenderType(TRAIL_TYPE);
        RenderUtils.renderEntityTrail(poseStack, trailBuilder, entity.trailPointBuilder, entity, spiritType.getPrimaryColor(), spiritType.getSecondaryColor(), 1f, partialTicks);
        renderSpiritEntity(entity, itemRenderer, partialTicks, poseStack, bufferIn, packedLightIn);
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    public static void renderSpiritEntity(FloatingItemEntity entity, ItemRenderer itemRenderer, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        ItemStack itemStack = entity.getItem();
        BakedModel model = itemRenderer.getModel(itemStack, entity.level(), null, entity.getItem().getCount());
        float yOffset = entity.getYOffset(partialTicks);
        float scale = model.getTransforms().getTransform(ItemDisplayContext.GROUND).scale.y();
        float rotation = entity.getRotation(partialTicks);

        poseStack.pushPose();
        poseStack.translate(0.0D, (yOffset - 0.25F * scale), 0.0D);
        poseStack.mulPose(Axis.YP.rotation(rotation));
        itemRenderer.render(itemStack, ItemDisplayContext.GROUND, false, poseStack, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, model);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.0D, yOffset, 0.0D);
        renderSpiritGlimmer(poseStack, entity.getSpiritType(), partialTicks);
        poseStack.popPose();
    }

    public static void renderSpiritGlimmer(PoseStack poseStack, MalumSpiritType spiritType, float partialTicks) {
        renderSpiritGlimmer(poseStack, spiritType, 1f, partialTicks);
    }

    public static void renderSpiritGlimmer(PoseStack poseStack, MalumSpiritType spiritType, float scalar, float partialTicks) {
        renderSpiritGlimmer(poseStack, SpiritBasedWorldVFXBuilder.create(spiritType), spiritType.getPrimaryColor(), spiritType.getSecondaryColor(), scalar, scalar, partialTicks);
    }

    public static void renderSpiritGlimmer(PoseStack poseStack, MalumSpiritType spiritType, float scaleScalar, float alphaScalar, float partialTicks) {
        renderSpiritGlimmer(poseStack, SpiritBasedWorldVFXBuilder.create(spiritType), spiritType.getPrimaryColor(), spiritType.getSecondaryColor(), scaleScalar, alphaScalar, partialTicks);
    }

    public static void renderSpiritGlimmer(PoseStack poseStack, Color primaryColor, Color secondaryColor, float partialTicks) {
        renderSpiritGlimmer(poseStack, primaryColor, secondaryColor, 1f, partialTicks);
    }

    public static void renderSpiritGlimmer(PoseStack poseStack, Color primaryColor, Color secondaryColor, float scalar, float partialTicks) {
        renderSpiritGlimmer(poseStack, VFXBuilders.createWorld(), primaryColor, secondaryColor, scalar, scalar, partialTicks);
    }

    public static void renderSpiritGlimmer(PoseStack poseStack, Color primaryColor, Color secondaryColor, float scaleScalar, float alphaScalar, float partialTicks) {
        renderSpiritGlimmer(poseStack, VFXBuilders.createWorld(), primaryColor, secondaryColor, scaleScalar, alphaScalar, partialTicks);
    }

    public static void renderSpiritGlimmer(PoseStack poseStack, VFXBuilders.WorldVFXBuilder builder, Color primaryColor, Color secondaryColor, float scaleScalar, float alphaScalar, float partialTicks) {
        Level level = Minecraft.getInstance().level;
        float gameTime = level.getGameTime() + partialTicks;
        float sine = (float) Math.abs(((Math.sin((gameTime / 80f) % 360)) * 0.075f));
        float bounce = EasingHelper.weightedEasingLerp(Easing.BOUNCE_IN_OUT, (gameTime % 20) / 20f, 0.025f, 0.05f, 0.025f);
        float scale = (0.12f + sine + bounce) * scaleScalar;

        poseStack.pushPose();
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180f));
        builder.setAlpha(0.6f * alphaScalar)
                .setColor(primaryColor)
                .setRenderType(STAR)
                .renderQuad(poseStack, scale * 0.8f);
        builder.setAlpha(0.8f * alphaScalar)
                .setRenderType(TWINKLE)
                .renderQuad(poseStack, scale * 0.6f);
        builder.setAlpha(0.2f * alphaScalar)
                .setColor(secondaryColor)
                .renderQuad(poseStack, scale * 0.6f);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(FloatingItemEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}