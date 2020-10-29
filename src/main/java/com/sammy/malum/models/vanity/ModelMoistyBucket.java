package com.sammy.malum.models.vanity;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelMoistyBucket<T extends LivingEntity> extends BipedModel<T>
{
    public final ModelRenderer bucket;
    
    public ModelMoistyBucket()
    {
        super(1);
        textureWidth = 64;
        textureHeight = 64;
        
        bucket = new ModelRenderer(this);
        bucket.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedHead.addChild(bucket);
        bucket.setTextureOffset(0, 0).addBox(-4.5F, -13.0F, -4.5F, 9.0F, 9.0F, 9.0F, 0.1F, false);
        bucket.setTextureOffset(0, 18).addBox(-4.5F, -5.0F, -4.5F, 9.0F, 1.0F, 9.0F, 0.4F, false);
        
        ModelRenderer bucket = new ModelRenderer(this);
        bucket.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.bucket.addChild(bucket);
        setRotationAngle(bucket, -0.3054F, 0.0F, 0.0F);
        bucket.setTextureOffset(0, 28).addBox(-4.5F, -0.5F, -0.5F, 9.0F, 9.0F, 1.0F, 0.2F, false);
    }
    
    @Override
    public void setRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        //previously the render function, render code was moved to a method below
    }
    
    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        bipedHead = bucket;
        bucket.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
    
    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}