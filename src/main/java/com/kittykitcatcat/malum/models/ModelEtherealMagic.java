package com.kittykitcatcat.malum.models;// Made with Blockbench 3.6.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class ModelEtherealMagic extends EntityModel<LivingEntity>
{
	private final ModelRenderer shieldA;
	private final ModelRenderer shieldB;
	private final ModelRenderer shieldC;

	public ModelEtherealMagic() {
		textureWidth = 32;
		textureHeight = 32;
		
		shieldA = new ModelRenderer(this);
		shieldA.setRotationPoint(0.0F, 0.0F, 0.0F);
		shieldA.setTextureOffset(0, 0).addBox(-6.0F, 0.0F, -15.0F, 12.0F, 12.0F, 1.0F, 0.0F, false);
		shieldA.setTextureOffset(0, 13).addBox(-4.0F, 12.0F, -15.0F, 8.0F, 3.0F, 1.0F, 0.0F, false);
		
		shieldB = new ModelRenderer(this);
		shieldB.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(shieldB, 0.0F, 2.0944F, 0.0F);
		shieldB.setTextureOffset(0, 0).addBox(-6.0F, 0.0F, -15.0F, 12.0F, 12.0F, 1.0F, 0.0F, false);
		shieldB.setTextureOffset(0, 13).addBox(-4.0F, 12.0F, -15.0F, 8.0F, 3.0F, 1.0F, 0.0F, false);
		
		shieldC = new ModelRenderer(this);
		shieldC.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(shieldC, 0.0F, -2.0944F, 0.0F);
		shieldC.setTextureOffset(0, 0).addBox(-6.0F, 0.0F, -15.0F, 12.0F, 12.0F, 1.0F, 0.0F, false);
		shieldC.setTextureOffset(0, 13).addBox(-4.0F, 12.0F, -15.0F, 8.0F, 3.0F, 1.0F, 0.0F, false);
		
	}
	
	@Override
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
	
	}
	
	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		
		shieldA.render(matrixStack, buffer, packedLight, packedOverlay, red,green,blue,alpha);
		shieldB.render(matrixStack, buffer, packedLight, packedOverlay, red,green,blue,alpha);
		shieldC.render(matrixStack, buffer, packedLight, packedOverlay, red,green,blue,alpha);
	}
	
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}