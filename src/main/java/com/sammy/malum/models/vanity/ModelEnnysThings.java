package com.sammy.malum.models.vanity;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelEnnysThings<T extends LivingEntity> extends BipedModel<T>
{
	public final ModelRenderer theTHINGS;
	
	public ModelEnnysThings()
	{
		super(1);
		textureWidth = 64;
		textureHeight = 64;
		
		theTHINGS = new ModelRenderer(this);
		theTHINGS.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedHead.addChild(theTHINGS);
		theTHINGS.setTextureOffset(0, 5).addBox(-5.5F, -6.0F, -2.0F, 11.0F, 4.0F, 4.0F, 0.0F, false);
		theTHINGS.setTextureOffset(0, 0).addBox(-5.0F, -9.0F, -1.0F, 10.0F, 3.0F, 2.0F, 0.0F, false);
	}
	
	@Override
	public void setRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		//previously the render function, render code was moved to a method below
	}
	
	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		bipedHead = theTHINGS;
		theTHINGS.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}