package com.sammy.malum.models.vanity;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelAngelasScarfHandBits<T extends LivingEntity> extends BipedModel<T>
{
    private final ModelRenderer scarfRightArm;
    private final ModelRenderer scarfLeftArm;
    
    public ModelAngelasScarfHandBits()
    {
        super(1);
        textureWidth = 64;
        textureHeight = 64;
        
        scarfRightArm = new ModelRenderer(this);
        scarfRightArm.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedRightArm.addChild(scarfRightArm);
        scarfRightArm.setTextureOffset(24, 0).addBox(-2.75F, -2.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.5F, false);
        
        scarfLeftArm = new ModelRenderer(this);
        scarfLeftArm.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedLeftArm.addChild(scarfLeftArm);
        scarfLeftArm.setTextureOffset(24, 0).addBox(-1.25F, -2.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.5F, true);
        
    }
    
    @Override
    public void setRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        //previously the render function, render code was moved to a method below
    }
    
    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        bipedRightArm = scarfRightArm;
        bipedLeftArm = scarfLeftArm;
        scarfRightArm.render(matrixStack, buffer, packedLight, packedOverlay);
        scarfLeftArm.render(matrixStack, buffer, packedLight, packedOverlay);
    }
    
    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}