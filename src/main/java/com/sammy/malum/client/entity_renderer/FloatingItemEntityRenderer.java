package com.sammy.malum.client.entity_renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.sammy.malum.common.entity.FloatingItemEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class FloatingItemEntityRenderer extends EntityRenderer<FloatingItemEntity>
{
    public final ItemRenderer itemRenderer;
    public final Random random = new Random();

    public FloatingItemEntityRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }
    
    
    @Override
    public void render(FloatingItemEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn)
    {
        poseStack.pushPose();
        ItemStack itemstack = entityIn.getItem();
        BakedModel ibakedmodel = this.itemRenderer.getModel(itemstack, entityIn.level, null, entityIn.getItem().getCount());
        float f1 = entityIn.getYOffset(partialTicks);
        float f2 = ibakedmodel.getTransforms().getTransform(ItemTransforms.TransformType.GROUND).scale.y();
        float f3 = entityIn.getRotation(partialTicks);
        poseStack.translate(0.0D, (f1 + 0.25F * f2), 0.0D);
        poseStack.mulPose(Vector3f.YP.rotation(f3));
        this.itemRenderer.render(itemstack, ItemTransforms.TransformType.GROUND, false, poseStack, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, ibakedmodel);
        poseStack.popPose();
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(FloatingItemEntity entity)
    {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}