package com.sammy.malum.client.entity_renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.common.entity.FloatingItemEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

import java.util.Random;

public class FloatingItemEntityRenderer extends EntityRenderer<FloatingItemEntity>
{
    public final net.minecraft.client.renderer.ItemRenderer itemRenderer;
    public final Random random = new Random();

    public FloatingItemEntityRenderer(EntityRendererManager renderManager, net.minecraft.client.renderer.ItemRenderer itemRendererIn)
    {
        super(renderManager);
        this.itemRenderer = itemRendererIn;
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }
    
    
    @Override
    public void render(FloatingItemEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        matrixStackIn.pushPose();
        ItemStack itemstack = entityIn.getItem();
        IBakedModel ibakedmodel = this.itemRenderer.getModel(itemstack, entityIn.level, null);
        float f1 = entityIn.getYOffset(partialTicks);
        float f2 = ibakedmodel.getTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y();
        float f3 = entityIn.getRotation(partialTicks);
        matrixStackIn.translate(0.0D, (f1 + 0.25F * f2), 0.0D);
        matrixStackIn.mulPose(Vector3f.YP.rotation(f3));
        this.itemRenderer.render(itemstack, ItemCameraTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, ibakedmodel);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(FloatingItemEntity entity)
    {
        return AtlasTexture.LOCATION_BLOCKS;
    }
}