package com.sammy.malum.models;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelKittysTail<T extends LivingEntity> extends BipedModel<T>
{	private final ModelRenderer tail;
	private final ModelRenderer tail1;
	private final ModelRenderer tail2;
	private final ModelRenderer tail3;
	private final ModelRenderer tail4;
	private final ModelRenderer tail5;
	
	public ModelKittysTail() {
		super(1);
		textureWidth = 64;
		textureHeight = 64;
		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 10.625F, 1.625F);
		bipedBody.addChild(tail);
		setRotationAngle(tail, 0.7854F, 0.0F, 0.0F);
		tail.setTextureOffset(0, 31).addBox(-2.0F, -2.125F, -0.125F, 4.0F, 3.0F, 3.0F, 0.0F, true);
		tail.setTextureOffset(0, 0).addBox(-2.0F, -2.125F, -0.125F, 4.0F, 3.0F, 3.0F, -0.1F, true);
		tail.setTextureOffset(26, 0).addBox(-2.0F, -2.125F, -0.125F, 4.0F, 3.0F, 3.0F, -0.3F, true);
		
		tail1 = new ModelRenderer(this);
		tail1.setRotationPoint(2.0F, 0.875F, -11.125F);
		tail.addChild(tail1);
		tail1.setTextureOffset(26, 0).addBox(-4.0F, -3.0F, 14.0F, 4.0F, 3.0F, 3.0F, 0.0F, false);
		tail1.setTextureOffset(0, 0).addBox(-4.0F, -3.0F, 14.0F, 4.0F, 3.0F, 3.0F, 0.2F, false);
		tail1.setTextureOffset(0, 31).addBox(-4.0F, -3.0F, 14.0F, 4.0F, 3.0F, 3.0F, 0.3F, false);
		
		tail2 = new ModelRenderer(this);
		tail2.setRotationPoint(0.0F, 0.0F, 3.0F);
		tail1.addChild(tail2);
		setRotationAngle(tail2, 0.2182F, 0.0F, 0.0F);
		tail2.setTextureOffset(26, 6).addBox(-4.0F, -0.0278F, 13.5221F, 4.0F, 3.0F, 3.0F, 0.1F, false);
		tail2.setTextureOffset(0, 6).addBox(-4.0F, -0.0278F, 13.5221F, 4.0F, 3.0F, 3.0F, 0.3F, false);
		tail2.setTextureOffset(0, 36).addBox(-4.0F, -0.0278F, 13.5221F, 4.0F, 3.0F, 3.0F, 0.4F, false);
		
		tail3 = new ModelRenderer(this);
		tail3.setRotationPoint(0.0F, 0.0F, 0.0F);
		tail2.addChild(tail3);
		setRotationAngle(tail3, 0.2182F, 0.0F, 0.0F);
		tail3.setTextureOffset(26, 12).addBox(-4.5F, 2.8654F, 15.5922F, 5.0F, 4.0F, 8.0F, 0.15F, false);
		tail3.setTextureOffset(0, 12).addBox(-4.5F, 2.8654F, 15.5922F, 5.0F, 4.0F, 8.0F, 0.35F, false);
		tail3.setTextureOffset(0, 44).addBox(-4.5F, 2.8654F, 15.5922F, 5.0F, 4.0F, 8.0F, 0.45F, false);
		
		tail4 = new ModelRenderer(this);
		tail4.setRotationPoint(0.0F, 0.0F, 0.0F);
		tail3.addChild(tail4);
		setRotationAngle(tail4, -0.1745F, 0.0F, 0.0F);
		tail4.setTextureOffset(26, 24).addBox(-4.0F, -0.701F, 24.0179F, 4.0F, 3.0F, 2.0F, 0.0F, false);
		tail4.setTextureOffset(0, 24).addBox(-4.0F, -0.701F, 24.0179F, 4.0F, 3.0F, 2.0F, 0.2F, false);
		tail4.setTextureOffset(0, 55).addBox(-4.0F, -0.701F, 24.0179F, 4.0F, 3.0F, 2.0F, 0.3F, false);
		
		tail5 = new ModelRenderer(this);
		tail5.setRotationPoint(-0.5F, -0.5F, 2.0F);
		tail4.addChild(tail5);
		setRotationAngle(tail5, -0.2618F, 0.0F, 0.0F);
		tail5.setTextureOffset(26, 29).addBox(-3.0F, -5.8891F, 23.7322F, 3.0F, 2.0F, 1.0F, 0.0F, false);
		tail5.setTextureOffset(0, 29).addBox(-3.0F, -5.8891F, 23.7322F, 3.0F, 2.0F, 1.0F, 0.2F, false);
		tail5.setTextureOffset(0, 61).addBox(-3.0F, -5.8891F, 23.7322F, 3.0F, 2.0F, 1.0F, 0.3F, false);
	}
	
	@Override
	public void setRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){

	}
	
	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		tail.render(matrixStack, buffer, packedLight, packedOverlay, red,green,blue,alpha);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}