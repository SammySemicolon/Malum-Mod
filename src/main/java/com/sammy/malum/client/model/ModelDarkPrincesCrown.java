package com.sammy.malum.client.model;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelDarkPrincesCrown<T extends LivingEntity> extends BipedModel<T>
{
    public final ModelRenderer crown;
    public ModelDarkPrincesCrown()
    {
        super(1);
        textureWidth = 32;
        textureHeight = 32;

        crown = new ModelRenderer(this);
        crown.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(crown, -0.0873F, 0.0F, 0.0F);
        crown.setTextureOffset(0, 0).addBox(-4.0F, -12.5F, -4.0F, 8.0F, 6.0F, 8.0F, 0.33F, false);
        crown.setTextureOffset(0, 18).addBox(-4.0F, -12.5F, -4.0F, 8.0F, 6.0F, 8.0F, 0.58F, false);
    }
    
    @Override
    public void setRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
    
    }
    
    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        crown.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
    
    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}