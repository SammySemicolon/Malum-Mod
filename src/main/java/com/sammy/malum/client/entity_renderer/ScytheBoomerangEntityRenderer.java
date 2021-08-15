package com.sammy.malum.client.entity_renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class ScytheBoomerangEntityRenderer extends EntityRenderer<ScytheBoomerangEntity>
{
    public final net.minecraft.client.renderer.ItemRenderer itemRenderer;
    
    public ScytheBoomerangEntityRenderer(EntityRendererManager renderManager, net.minecraft.client.renderer.ItemRenderer itemRendererIn)
    {
        super(renderManager);
        this.itemRenderer = itemRendererIn;
        this.shadowSize = 2F;
        this.shadowOpaque = 0.5F;
    }
    
    @Override
    public void render(ScytheBoomerangEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        matrixStackIn.push();
        ItemStack itemstack = entityIn.getItem();
        IBakedModel ibakedmodel = this.itemRenderer.getItemModelWithOverrides(itemstack, entityIn.world, null);
    
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90F));
        matrixStackIn.scale(2f, 2f, 2f);
        matrixStackIn.rotate(Vector3f.ZP.rotation(-(entityIn.age + partialTicks) * 0.9f));
        itemRenderer.renderItem(itemstack, ItemCameraTransforms.TransformType.FIXED, false, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, ibakedmodel);
    
        matrixStackIn.pop();
    
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
    
    @Override
    public ResourceLocation getEntityTexture(ScytheBoomerangEntity entity)
    {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }
}