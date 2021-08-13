package com.sammy.malum.client.model;// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ComicallyLargeTophatModel<T extends LivingEntity> extends BipedModel<T>
{
	public final ModelRenderer tophat;
	
	public ComicallyLargeTophatModel()
	{
		super(1);
		textureWidth = 64;
		textureHeight = 64;
		
		tophat = new ModelRenderer(this);
		tophat.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedHead.addChild(tophat);
		tophat.setTextureOffset(0, 0).addBox(-4.0F, -28.0F, -4.0F, 8.0F, 20.0F, 8.0F, -0.1F, false);
		tophat.setTextureOffset(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 3.0F, 8.0F, 0.9F, false);
	}
	
	@Override
	public void setRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
	
	}
	
	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		tophat.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}