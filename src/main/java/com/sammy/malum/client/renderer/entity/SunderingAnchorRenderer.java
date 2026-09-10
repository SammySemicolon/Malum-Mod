package com.sammy.malum.client.renderer.entity;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.client.*;
import com.sammy.malum.common.entity.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.builder.WorldVFXBuilder;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;
import team.lodestar.lodestone.systems.rendering.uniform.UniformData;

public class SunderingAnchorRenderer extends EntityRenderer<SunderingAnchorProjectile> {

    public final ItemRenderer itemRenderer;

    public SunderingAnchorRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public boolean shouldRender(SunderingAnchorProjectile livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void render(SunderingAnchorProjectile entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) + 90f));
        poseStack.mulPose(Axis.ZN.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
        poseStack.mulPose(Axis.ZP.rotationDegrees(45f));
        var itemstack = entity.getItem();
        var model = this.itemRenderer.getModel(itemstack, entity.level(), null, 1);
        itemRenderer.render(itemstack, itemstack.getItem() instanceof MalumScytheItem ? ItemDisplayContext.NONE : ItemDisplayContext.FIXED, false, poseStack, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, model);
        poseStack.popPose();

        float delta = entity.getVisualEffectScalar();
        float scale = delta * 0.7f;
        float alpha = Mth.clamp(delta * 0.4f, 0, 1);
        var additive = LodestoneRenderTypes.ADDITIVE_TWO_SIDED_TEXTURE_TRIANGLE.apply(MalumRenderTypeTokens.CONCENTRATED_TRAIL);
        var transparent = LodestoneRenderTypes.TRANSPARENT_TWO_SIDED_TEXTURE_TRIANGLE.apply(MalumRenderTypeTokens.CONCENTRATED_TRAIL).addUniformData(UniformData.LUMITRANSPARENT);
        WorldVFXBuilder builder = VFXBuilders.createWorld().setRenderType(additive);
        RenderUtils.renderEntityTrail(poseStack, builder, entity.trailPointBuilder, entity, MalumSpiritTypes.EARTHEN_SPIRIT, scale * 1.2f, alpha*0.4f, partialTicks);
        RenderUtils.renderEntityTrail(poseStack, builder, entity.spinningTrailPointBuilder, entity, MalumSpiritTypes.EARTHEN_SPIRIT, scale * 2f, alpha, partialTicks);
        builder.setRenderType(transparent);
        RenderUtils.renderEntityTrail(poseStack, builder, entity.trailPointBuilder, entity, MalumSpiritTypes.AERIAL_SPIRIT, scale * 0.6f, alpha*0.3f, partialTicks);
        RenderUtils.renderEntityTrail(poseStack, builder, entity.spinningTrailPointBuilder, entity, MalumSpiritTypes.AERIAL_SPIRIT, scale, alpha, partialTicks);

        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(SunderingAnchorProjectile entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}