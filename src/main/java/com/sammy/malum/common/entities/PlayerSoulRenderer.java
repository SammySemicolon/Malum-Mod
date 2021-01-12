package com.sammy.malum.common.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.core.init.MalumItems;
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

import java.util.Random;

public class PlayerSoulRenderer extends EntityRenderer<PlayerSoulEntity>
{
    public final net.minecraft.client.renderer.ItemRenderer itemRenderer;
    public final Random random = new Random();
    
    public PlayerSoulRenderer(EntityRendererManager renderManager, net.minecraft.client.renderer.ItemRenderer itemRendererIn)
    {
        super(renderManager);
        this.itemRenderer = itemRendererIn;
        this.shadowSize = 0;
        this.shadowOpaque = 0;
    }
    
    
    @Override
    public void render(PlayerSoulEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
    
    }
    @Override
    public ResourceLocation getEntityTexture(PlayerSoulEntity entity)
    {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }
}