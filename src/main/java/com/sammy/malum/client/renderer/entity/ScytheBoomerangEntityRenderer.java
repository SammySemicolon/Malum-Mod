package com.sammy.malum.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.item.tools.ScytheItem;
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

public class ScytheBoomerangEntityRenderer extends EntityRenderer<ScytheBoomerangEntity>
{
    public final ItemRenderer itemRenderer;
    
    public ScytheBoomerangEntityRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 2F;
        this.shadowStrength = 0.5F;
    }
    
    @Override
    public void render(ScytheBoomerangEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn)
    {
        poseStack.pushPose();
        ItemStack itemstack = entityIn.getItem();
        BakedModel ibakedmodel = this.itemRenderer.getModel(itemstack, entityIn.level, null, 1);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(90F));
        poseStack.scale(2f, 2f, 2f);
        poseStack.mulPose(Vector3f.ZP.rotation((entityIn.age + partialTicks) * 0.9f));
        itemRenderer.render(itemstack, itemstack.getItem() instanceof ScytheItem ? ItemTransforms.TransformType.NONE : ItemTransforms.TransformType.FIXED, false, poseStack, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, ibakedmodel);
    
        poseStack.popPose();
    
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }
    
    @Override
    public ResourceLocation getTextureLocation(ScytheBoomerangEntity entity)
    {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}