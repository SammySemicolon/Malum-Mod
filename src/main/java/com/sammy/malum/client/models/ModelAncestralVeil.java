package com.sammy.malum.client.models;// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelAncestralVeil<T extends LivingEntity> extends BipedModel<T>
{
	public final ModelRenderer veil;
	
	public ModelAncestralVeil() {
		super(1);
		textureWidth = 64;
		textureHeight = 64;

		veil = new ModelRenderer(this);
		veil.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedHead.addChild(veil);
		
		ModelRenderer cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, -8.6273F, -2.479F);
		veil.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.1745F, 3.1416F, 0.0F);
		cube_r1.setTextureOffset(24, 12).addBox(2.0F, -2.3246F, 0.1909F, 5.0F, 6.0F, 1.0F, 0.25F, false);
		cube_r1.setTextureOffset(24, 0).addBox(2.0F, -2.3246F, 0.1909F, 5.0F, 6.0F, 1.0F, 0.0F, false);
		
		ModelRenderer cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, -8.8672F, -3.8397F);
		veil.addChild(cube_r2);
		setRotationAngle(cube_r2, -0.1745F, 0.0F, 0.0F);
		cube_r2.setTextureOffset(24, 12).addBox(2.0F, -2.3246F, 0.1909F, 5.0F, 6.0F, 1.0F, 0.25F, false);
		cube_r2.setTextureOffset(24, 0).addBox(2.0F, -2.3246F, 0.1909F, 5.0F, 6.0F, 1.0F, 0.0F, false);
		
		ModelRenderer cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, 0.0F, 0.0F);
		veil.addChild(cube_r3);
		setRotationAngle(cube_r3, -0.1745F, 0.0F, 0.0F);
		cube_r3.setTextureOffset(0, 12).addBox(-5.0F, -8.4933F, -5.8112F, 10.0F, 10.0F, 2.0F, 0.25F, false);
		cube_r3.setTextureOffset(0, 0).addBox(-5.0F, -8.4933F, -5.8112F, 10.0F, 10.0F, 2.0F, 0.0F, false);
	}
	
	@Override
	public void setRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
	
	}
	
	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		veil.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}