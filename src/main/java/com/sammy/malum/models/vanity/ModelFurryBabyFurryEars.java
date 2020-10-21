package com.sammy.malum.models.vanity;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelFurryBabyFurryEars<T extends LivingEntity> extends BipedModel<T>
{
	public final ModelRenderer ears;
	
	public ModelFurryBabyFurryEars()
	{
		super(1);
		textureWidth = 32;
		textureHeight = 32;
		
		ears = new ModelRenderer(this);
		ears.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedHead.addChild(ears);
		
		
		ModelRenderer ear1 = new ModelRenderer(this);
		ear1.setRotationPoint(4.25F, -5.5F, 0.0F);
		ears.addChild(ear1);
		setRotationAngle(ear1, -0.1309F, 0.0F, -0.0873F);
		ear1.setTextureOffset(0, 0).addBox(-0.3693F, -5.9815F, -2.195F, 1.0F, 5.0F, 4.0F, -0.1F, true);
		
		ModelRenderer ear2 = new ModelRenderer(this);
		ear2.setRotationPoint(-4.25F, -5.5F, 0.0F);
		ears.addChild(ear2);
		setRotationAngle(ear2, -0.1309F, 0.0F, 0.0873F);
		ear2.setTextureOffset(0, 0).addBox(-0.6307F, -5.9815F, -2.195F, 1.0F, 5.0F, 4.0F, -0.1F, false);
	}
	
	@Override
	public void setRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		//previously the render function, render code was moved to a method below
	}
	
	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		bipedHead = ears;
		ears.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}