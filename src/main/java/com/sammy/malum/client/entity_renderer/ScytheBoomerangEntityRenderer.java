package com.sammy.malum.client.entity_renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class ScytheBoomerangEntityRenderer extends EntityRenderer<ScytheBoomerangEntity>
{
    public final net.minecraft.client.renderer.ItemRenderer itemRenderer;
    
    public ScytheBoomerangEntityRenderer(EntityRendererManager renderManager, net.minecraft.client.renderer.ItemRenderer itemRendererIn)
    {
        super(renderManager);
        this.itemRenderer = itemRendererIn;
        this.shadowRadius = 2F;
        this.shadowStrength = 0.5F;
    }
    
    @Override
    public void render(ScytheBoomerangEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        matrixStackIn.pushPose();
        ItemStack itemstack = entityIn.getItem();
        IBakedModel ibakedmodel = this.itemRenderer.getModel(itemstack, entityIn.level, null);
    
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90F));
        matrixStackIn.scale(2f, 2f, 2f);
        matrixStackIn.mulPose(Vector3f.ZP.rotation(-(entityIn.age + partialTicks) * 0.9f));
        itemRenderer.render(itemstack, ItemCameraTransforms.TransformType.FIXED, false, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, ibakedmodel);
    
        matrixStackIn.popPose();
    
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
    
    @Override
    public ResourceLocation getTextureLocation(ScytheBoomerangEntity entity)
    {
        return AtlasTexture.LOCATION_BLOCKS;
    }
}