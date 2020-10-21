package com.sammy.malum.models.vanity;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelFlowerFriend<T extends LivingEntity> extends BipedModel<T>
{
	public final ModelRenderer flower;
	
	public ModelFlowerFriend()
	{
		super(1);
		textureWidth = 16;
		textureHeight = 16;
		
		flower = new ModelRenderer(this);
		flower.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedHead.addChild(flower);
		
		ModelRenderer flower1 = new ModelRenderer(this);
		flower1.setRotationPoint(0.0F, 0.0F, 0.0F);
		flower.addChild(flower1);
		setRotationAngle(flower1, 0.0F, -0.7854F, 0.0F);
		flower1.setTextureOffset(5, 3).addBox(-2.5F, -19.0F, 0.0F, 5.0F, 11.0F, 0.0F, 0.0F, false);
		
		ModelRenderer flower2 = new ModelRenderer(this);
		flower2.setRotationPoint(0.0F, 0.0F, 0.0F);
		flower.addChild(flower2);
		setRotationAngle(flower2, 0.0F, 0.7854F, 0.0F);
		flower2.setTextureOffset(5, 3).addBox(-2.5F, -19.0F, 0.0F, 5.0F, 11.0F, 0.0F, 0.0F, false);
	}
	
	@Override
	public void setRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		//previously the render function, render code was moved to a method below
	}
	
	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		bipedHead = flower;
		flower.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}