package com.kittykitcatcat.malum.models;// Made with Blockbench 3.5.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;


public class ModelSkullBulwarkShield extends EntityModel<Entity>
{
	private final ModelRenderer middlePart;
	private final ModelRenderer bottomMiddlePart;
	private final ModelRenderer leftPart;
	private final ModelRenderer bottomLeftPart;
	private final ModelRenderer tinyTopLeftPart;
	private final ModelRenderer rightPart;
	private final ModelRenderer bottomRightPart;
	private final ModelRenderer tinyTopRightPart;

	public ModelSkullBulwarkShield() {
		textureWidth = 32;
		textureHeight = 32;

		middlePart = new ModelRenderer(this);
		middlePart.setRotationPoint(0.0F, 24.0F, 0.0F);
		middlePart.setTextureOffset(14, 11).addBox(-1.0F, -27.0F, -1.05F, 2.0F, 6.0F, 6.0F, 0.0F, false);
		middlePart.setTextureOffset(0, 0).addBox(-1.0F, -33.0F, -3.025F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		bottomMiddlePart = new ModelRenderer(this);
		bottomMiddlePart.setRotationPoint(0.0F, 12.0F, 0.0F);
		middlePart.addChild(bottomMiddlePart);
		setRotationAngle(bottomMiddlePart, 0.2618F, 0.0F, 0.0F);
		bottomMiddlePart.setTextureOffset(0, 20).addBox(-1.0F, -32.7513F, 5.6556F, 2.0F, 6.0F, 2.0F, 0.0F, false);

		leftPart = new ModelRenderer(this);
		leftPart.setRotationPoint(-0.5F, 24.0F, -0.25F);
		setRotationAngle(leftPart, 0.0F, -0.2618F, 0.0F);
		leftPart.setTextureOffset(8, 0).addBox(0.7585F, -31.0F, -2.9353F, 5.0F, 10.0F, 1.0F, 0.0F, true);
		leftPart.setTextureOffset(0, 17).addBox(0.7585F, -32.0F, -3.05F, 5.0F, 1.0F, 2.0F, 0.0F, false);

		bottomLeftPart = new ModelRenderer(this);
		bottomLeftPart.setRotationPoint(7.0F, 10.0F, 0.0F);
		leftPart.addChild(bottomLeftPart);
		setRotationAngle(bottomLeftPart, 0.2618F, 0.0F, 0.0F);
		bottomLeftPart.setTextureOffset(8, 11).addBox(-6.2415F, -30.7333F, 5.2125F, 5.0F, 5.0F, 1.0F, 0.0F, true);

		tinyTopLeftPart = new ModelRenderer(this);
		tinyTopLeftPart.setRotationPoint(12.0F, 0.0F, 0.0F);
		leftPart.addChild(tinyTopLeftPart);
		setRotationAngle(tinyTopLeftPart, 0.0F, -0.2618F, 0.0F);
		tinyTopLeftPart.setTextureOffset(0, 14).addBox(-6.85F, -32.0F, -1.325F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		tinyTopLeftPart.setTextureOffset(20, 0).addBox(-6.85F, -31.0F, -1.21F, 2.0F, 9.0F, 1.0F, 0.0F, false);

		rightPart = new ModelRenderer(this);
		rightPart.setRotationPoint(-5.75F, 24.0F, 1.425F);
		setRotationAngle(rightPart, 0.0F, 0.2618F, 0.0F);
		rightPart.setTextureOffset(8, 0).addBox(0.7585F, -31.0F, -2.9353F, 5.0F, 10.0F, 1.0F, 0.0F, false);
		rightPart.setTextureOffset(0, 17).addBox(0.7585F, -32.0F, -3.05F, 5.0F, 1.0F, 2.0F, 0.0F, false);

		bottomRightPart = new ModelRenderer(this);
		bottomRightPart.setRotationPoint(7.0F, 10.0F, 0.0F);
		rightPart.addChild(bottomRightPart);
		setRotationAngle(bottomRightPart, 0.2618F, 0.0F, 0.0F);
		bottomRightPart.setTextureOffset(8, 11).addBox(-6.2415F, -30.7333F, 5.2125F, 5.0F, 5.0F, 1.0F, 0.0F, false);

		tinyTopRightPart = new ModelRenderer(this);
		tinyTopRightPart.setRotationPoint(12.0F, 0.0F, -4.75F);
		rightPart.addChild(tinyTopRightPart);
		setRotationAngle(tinyTopRightPart, 0.0F, 0.2618F, 0.0F);
		tinyTopRightPart.setTextureOffset(0, 14).addBox(-13.2937F, -32.0F, -1.2645F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		tinyTopRightPart.setTextureOffset(20, 0).addBox(-13.2937F, -31.0F, -1.1495F, 2.0F, 9.0F, 1.0F, 0.0F, true);
	}

	@Override
	public void setRotationAngles(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{

	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		middlePart.render(matrixStack, buffer, packedLight, packedOverlay);
		leftPart.render(matrixStack, buffer, packedLight, packedOverlay);
		rightPart.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}