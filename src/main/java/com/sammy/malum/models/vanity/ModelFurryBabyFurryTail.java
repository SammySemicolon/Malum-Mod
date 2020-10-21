package com.sammy.malum.models.vanity;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelFurryBabyFurryTail<T extends LivingEntity> extends BipedModel<T>
{
	private final ModelRenderer tail;
	
	public ModelFurryBabyFurryTail() {
		super(1);
		textureWidth = 32;
		textureHeight = 32;
		
		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 10.75F, 1.75F);
		bipedBody.addChild(tail);
		setRotationAngle(tail, -0.6981F, 0.0F, 0.0F);
		tail.setTextureOffset(0, 9).addBox(-0.5F, -6.0F, -0.5F, 1.0F, 6.0F, 1.0F, 0.2F, false);
		
		ModelRenderer tail2 = new ModelRenderer(this);
		tail2.setRotationPoint(0.0F, 0.0F, 0.0F);
		tail.addChild(tail2);
		setRotationAngle(tail2, 0.3927F, 0.0F, 0.0F);
		tail2.setTextureOffset(4, 9).addBox(-0.5F, -9.6602F, 1.8194F, 1.0F, 4.0F, 1.0F, 0.2F, false);
		
		ModelRenderer tail3 = new ModelRenderer(this);
		tail3.setRotationPoint(0.0F, 0.0F, 0.0F);
		tail2.addChild(tail3);
		setRotationAngle(tail3, -0.5236F, 0.0F, 0.0F);
		tail3.setTextureOffset(8, 9).addBox(-0.5F, -13.5488F, -3.3277F, 1.0F, 4.0F, 1.0F, 0.2F, false);
		
		ModelRenderer tail4 = new ModelRenderer(this);
		tail4.setRotationPoint(0.0F, 0.0F, 0.0F);
		tail3.addChild(tail4);
		setRotationAngle(tail4, -0.4363F, 0.0F, 0.0F);
		tail4.setTextureOffset(12, 9).addBox(-0.5F, -13.1698F, -8.8077F, 1.0F, 2.0F, 1.0F, 0.2F, false);}
	
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