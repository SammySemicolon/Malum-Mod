package com.sammy.malum.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.RenderUtils;
import com.sammy.malum.client.SpiritBasedWorldVFXBuilder;
import com.sammy.malum.common.entity.activator.SpiritCollectionActivatorEntity;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.client.MalumRenderTypeTokens;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderType;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.UMBRAL_SPIRIT;

public class SpiritCollectionActivatorEntityRenderer extends EntityRenderer<SpiritCollectionActivatorEntity> {
    public final ItemRenderer itemRenderer;

    public SpiritCollectionActivatorEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }

    private static final LodestoneRenderType TRAIL_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE_TRIANGLE.applyAndCache(MalumRenderTypeTokens.CONCENTRATED_TRAIL);

    @Override
    public void render(SpiritCollectionActivatorEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        MalumSpiritType spiritType = UMBRAL_SPIRIT;
        VFXBuilders.WorldVFXBuilder trailBuilder = SpiritBasedWorldVFXBuilder.create(spiritType).setRenderType(TRAIL_TYPE);
        float yOffset = entity.getYOffset(partialTicks);
        poseStack.pushPose();
        poseStack.translate(0.0D, yOffset, 0.0D);
        FloatingItemEntityRenderer.renderSpiritGlimmer(poseStack, spiritType, 0.85f, 4f, partialTicks);
        FloatingItemEntityRenderer.renderSpiritGlimmer(poseStack, SpiritTypeRegistry.ELDRITCH_SPIRIT, 0.6f, 0.5f, partialTicks);
        poseStack.popPose();
        RenderUtils.renderEntityTrail(poseStack, trailBuilder, entity.trailPointBuilder, entity, spiritType.getPrimaryColor(), spiritType.getSecondaryColor(), 1f, partialTicks);
        spiritType = SpiritTypeRegistry.ELDRITCH_SPIRIT;
        trailBuilder = SpiritBasedWorldVFXBuilder.create(spiritType).setRenderType(TRAIL_TYPE);
        RenderUtils.renderEntityTrail(poseStack, trailBuilder, entity.trailPointBuilder, entity, spiritType.getPrimaryColor(), spiritType.getSecondaryColor(), 0.75f, 0.5f, partialTicks);


        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(SpiritCollectionActivatorEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}