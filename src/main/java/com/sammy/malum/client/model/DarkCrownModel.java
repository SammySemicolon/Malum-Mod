package com.sammy.malum.client.model;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.world.entity.LivingEntity;

public class DarkCrownModel<T extends LivingEntity> extends BipedModel<T>
{
    public final ModelRenderer crown;
    public DarkCrownModel()
    {
        super(1);
        texWidth = 32;
        texHeight = 32;

        crown = new ModelRenderer(this);
        crown.setPos(0.0F, 0.0F, 0.0F);
        setRotationAngle(crown, -0.0873F, 0.0F, 0.0F);
        crown.texOffs(0, 0).addBox(-4.0F, -12.5F, -4.0F, 8.0F, 6.0F, 8.0F, 0.33F, false);
        crown.texOffs(0, 18).addBox(-4.0F, -12.5F, -4.0F, 8.0F, 6.0F, 8.0F, 0.58F, false);
    }
    
    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
    
    }
    
    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        crown.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
    
    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}