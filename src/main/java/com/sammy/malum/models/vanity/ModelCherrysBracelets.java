package com.sammy.malum.models.vanity;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelCherrysBracelets<T extends LivingEntity> extends BipedModel<T>
{
    private final ModelRenderer rightBracelet;
    private final ModelRenderer leftBracelet;
    
    public ModelCherrysBracelets()
    {
        super(1);
        textureWidth = 64;
        textureHeight = 64;
        
        
        rightBracelet = new ModelRenderer(this);
        rightBracelet.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedRightArm.addChild(rightBracelet);
        rightBracelet.setTextureOffset(0, 0).addBox(-2.0F, 5.0F, -2.0F, 3.0F, 1.0F, 4.0F, 0.3F, false);
        
        leftBracelet = new ModelRenderer(this);
        leftBracelet.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedLeftArm.addChild(leftBracelet);
        leftBracelet.setTextureOffset(0, 0).addBox(-1.0F, 5.0F, -2.0F, 3.0F, 1.0F, 4.0F, 0.3F, false);
    }
    
    @Override
    public void setRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        //previously the render function, render code was moved to a method below
    }
    
    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        bipedRightArm = rightBracelet;
        bipedLeftArm = leftBracelet;
        rightBracelet.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftBracelet.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
    
    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}