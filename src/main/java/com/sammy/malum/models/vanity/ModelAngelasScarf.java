package com.sammy.malum.models.vanity;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelAngelasScarf<T extends LivingEntity> extends BipedModel<T>
{
    private final ModelRenderer scarfBody;
    
    public ModelAngelasScarf()
    {
        super(1);
        textureWidth = 64;
        textureHeight = 64;
        
        scarfBody = new ModelRenderer(this);
        scarfBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedBody.addChild(scarfBody);
        scarfBody.setTextureOffset(0, 8).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 5.0F, 0.51F, false);
    }
    
    @Override
    public void setRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        //previously the render function, render code was moved to a method below
    }
    
    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        bipedBody = scarfBody;
        scarfBody.render(matrixStack, buffer, packedLight, packedOverlay);
    }
    
    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}