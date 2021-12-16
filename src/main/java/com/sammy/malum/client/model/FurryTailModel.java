package com.sammy.malum.client.model;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class FurryTailModel<T extends LivingEntity> extends BipedModel<T>
{
    private final ModelRenderer tail;
    
    public FurryTailModel()
    {
        super(1);
        texWidth = 64;
        texHeight = 64;
        tail = new ModelRenderer(this);
        tail.setPos(0.0F, 12.0F, 2.0F);
        setRotationAngle(tail, -0.7854F, 0.0F, 0.0F);
        tail.texOffs(0, 13).addBox(-2.0F, -4.0F, -3.0F, 4.0F, 4.0F, 3.0F, 0.0F, false);
        tail.texOffs(19, 13).addBox(-2.0F, -4.0F, -3.0F, 4.0F, 4.0F, 3.0F, 0.25F, false);
        
        ModelRenderer tail2 = new ModelRenderer(this);
        tail2.setPos(0.0F, -4.0F, -1.5F);
        tail.addChild(tail2);
        setRotationAngle(tail2, 0.0873F, 0.0F, 0.0F);
        tail2.texOffs(0, 0).addBox(-3.0F, -7.5F, -2.5F, 6.0F, 8.0F, 5.0F, 0.0F, false);
        tail2.texOffs(22, 0).addBox(-3.0F, -7.5F, -2.5F, 6.0F, 8.0F, 5.0F, 0.25F, false);
        
        ModelRenderer tailtip = new ModelRenderer(this);
        tailtip.setPos(0.0F, -7.5F, 0.0F);
        tail2.addChild(tailtip);
        setRotationAngle(tailtip, -0.1745F, 0.0F, 0.0F);
        tailtip.texOffs(0, 20).addBox(-2.5F, -2.0F, -2.0F, 5.0F, 3.0F, 4.0F, 0.0F, false);
        tailtip.texOffs(18, 20).addBox(-2.5F, -2.0F, -2.0F, 5.0F, 3.0F, 4.0F, 0.25F, false);
        
        ModelRenderer tailtip1 = new ModelRenderer(this);
        tailtip1.setPos(0.0F, -0.5F, 0.0F);
        tailtip.addChild(tailtip1);
        setRotationAngle(tailtip1, 0.0F, -0.7854F, 0.0F);
        tailtip1.texOffs(0, 22).addBox(0.0F, -5.0F, -2.5F, 0.0F, 5.0F, 5.0F, 0.0F, false);
        
        ModelRenderer tailtip2 = new ModelRenderer(this);
        tailtip2.setPos(0.0F, -0.5F, 0.0F);
        tailtip.addChild(tailtip2);
        setRotationAngle(tailtip2, 0.0F, 0.7767F, 0.0F);
        tailtip2.texOffs(0, 22).addBox(0.0F, -5.0F, -2.5F, 0.0F, 5.0F, 5.0F, 0.0F, true);
    }
    
    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
    
    }
    
    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        tail.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
    
    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}