package com.kittykitcatcat.malum.models;
// Made with Blockbench 3.5.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class ModelSpiritHunterArmor extends ModelArmor {
	private final ModelRenderer body;
	private final ModelRenderer leftCloak;
	private final ModelRenderer rightCloak;
	private final ModelRenderer head;
	private final ModelRenderer leftarm;
	private final ModelRenderer leftShoulderPad;
	private final ModelRenderer rightarm;
	private final ModelRenderer rightShoulderPad;
	private final ModelRenderer leftboot;
	private final ModelRenderer rightboot;
	private final ModelRenderer leftleg;
	private final ModelRenderer leftRobes;
	private final ModelRenderer rightleg;
	private final ModelRenderer rightLegRobes;
	private final ModelRenderer belt;
	private final ModelRenderer beltDiamond;

	public ModelSpiritHunterArmor(EquipmentSlotType slot) {
		super(slot, 64, 64);
		textureWidth = 64;
		textureHeight = 64;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 6.0F, 0.0F);
		body.setTextureOffset(50, 22).addBox(-1.0F, -6.0F, -2.0F, 2.0F, 12.0F, 4.0F, 0.1F, false);

		leftCloak = new ModelRenderer(this);
		leftCloak.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(leftCloak);
		setRotationAngle(leftCloak, 0.0F, 0.0F, 0.0873F);
		leftCloak.setTextureOffset(0, 32).addBox(-4.5F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.17F, false);

		rightCloak = new ModelRenderer(this);
		rightCloak.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(rightCloak);
		setRotationAngle(rightCloak, 0.0F, 0.0F, -0.0873F);
		rightCloak.setTextureOffset(0, 32).addBox(0.5F, -6.0F, -2.01F, 4.0F, 12.0F, 4.0F, 0.17F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.setTextureOffset(0, 10).addBox(-4.0F, -8.0F, -4.0F, 1.0F, 8.0F, 8.0F, 0.15F, false);
		head.setTextureOffset(0, 10).addBox(3.0F, -8.0F, -4.0F, 1.0F, 8.0F, 8.0F, 0.15F, false);
		head.setTextureOffset(0, 0).addBox(-3.0F, -8.0F, -4.0F, 6.0F, 1.0F, 8.0F, 0.16F, false);
		head.setTextureOffset(0, 0).addBox(-3.0F, -2.0F, -4.0F, 6.0F, 2.0F, 8.0F, 0.16F, false);
		head.setTextureOffset(0, 26).addBox(-3.0F, -7.0F, 3.0F, 6.0F, 5.0F, 1.0F, 0.16F, false);

		leftarm = new ModelRenderer(this);
		leftarm.setRotationPoint(4.0F, 2.0F, 0.0F);
		leftarm.setTextureOffset(36, 22).addBox(1.0F, 1.0F, -2.0F, 3.0F, 5.0F, 4.0F, 0.25F, true);

		leftShoulderPad = new ModelRenderer(this);
		leftShoulderPad.setRotationPoint(-21.0F, 0.0F, 0.0F);
		leftarm.addChild(leftShoulderPad);
		setRotationAngle(leftShoulderPad, 0.0F, 0.0F, 0.3491F);
		leftShoulderPad.setTextureOffset(18, 22).addBox(7.25F, -6.05F, -2.0F, 5.0F, 4.0F, 4.0F, 0.4F, false);

		rightarm = new ModelRenderer(this);
		rightarm.setRotationPoint(-4.0F, 2.0F, 0.0F);
		rightarm.setTextureOffset(36, 22).addBox(-4.0F, 1.0F, -2.0F, 3.0F, 5.0F, 4.0F, 0.25F, false);

		rightShoulderPad = new ModelRenderer(this);
		rightShoulderPad.setRotationPoint(24.0F, 18.0F, 0.0F);
		rightarm.addChild(rightShoulderPad);
		setRotationAngle(rightShoulderPad, 0.0F, 0.0F, -0.3491F);
		rightShoulderPad.setTextureOffset(18, 22).addBox(-9.0F, -24.0F, -2.0F, 5.0F, 4.0F, 4.0F, 0.4F, false);

		leftboot = new ModelRenderer(this);
		leftboot.setRotationPoint(0.0F, 24.0F, 0.0F);
		leftboot.setTextureOffset(20, 0).addBox(0.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.241F, true);

		rightboot = new ModelRenderer(this);
		rightboot.setRotationPoint(0.0F, 24.0F, 0.0F);
		rightboot.setTextureOffset(20, 0).addBox(-4.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.24F, false);

		leftleg = new ModelRenderer(this);
		leftleg.setRotationPoint(2.0F, 12.0F, 0.0F);
		

		leftRobes = new ModelRenderer(this);
		leftRobes.setRotationPoint(-5.0F, 0.0F, 0.0F);
		leftleg.addChild(leftRobes);
		setRotationAngle(leftRobes, 0.0F, 3.1416F, 0.2618F);
		leftRobes.setTextureOffset(36, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.25F, true);
		leftRobes.setTextureOffset(10, 10).addBox(-2.25F, 7.26F, -2.5F, 5.0F, 1.0F, 5.0F, 0.0F, true);
		leftRobes.setTextureOffset(52, 0).addBox(-2.25F, -1.25F, -2.5F, 1.0F, 9.0F, 5.0F, 0.01F, true);

		rightleg = new ModelRenderer(this);
		rightleg.setRotationPoint(-2.0F, 12.0F, 0.0F);
		

		rightLegRobes = new ModelRenderer(this);
		rightLegRobes.setRotationPoint(5.0F, 0.0F, 0.0F);
		rightleg.addChild(rightLegRobes);
		setRotationAngle(rightLegRobes, 0.0F, 0.0F, -0.2618F);
		rightLegRobes.setTextureOffset(36, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.25F, true);
		rightLegRobes.setTextureOffset(10, 10).addBox(-2.25F, 7.26F, -2.5F, 5.0F, 1.0F, 5.0F, 0.0F, true);
		rightLegRobes.setTextureOffset(52, 0).addBox(-2.25F, -1.25F, -2.5F, 1.0F, 9.0F, 5.0F, 0.01F, true);

		belt = new ModelRenderer(this);
		belt.setRotationPoint(0.0F, 24.0F, 0.0F);
		belt.setTextureOffset(18, 16).addBox(-4.5F, -14.0F, -2.0F, 9.0F, 2.0F, 4.0F, 0.24F, false);

		beltDiamond = new ModelRenderer(this);
		beltDiamond.setRotationPoint(-3.5F, -16.5F, 0.0F);
		belt.addChild(beltDiamond);
		setRotationAngle(beltDiamond, 0.0F, 0.0F, -0.7854F);
		beltDiamond.setTextureOffset(48, 14).addBox(-1.5F, 3.5F, -2.5F, 3.0F, 3.0F, 5.0F, 0.02F, false);
	}
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		this.head.copyModelAngles(this.bipedHead);
		this.body.copyModelAngles(this.bipedBody);
		this.belt.copyModelAngles(this.bipedBody);
		this.leftarm.copyModelAngles(this.bipedLeftArm);
		this.rightarm.copyModelAngles(this.bipedRightArm);
		this.leftleg.copyModelAngles(this.bipedLeftLeg);
		this.rightleg.copyModelAngles(this.bipedRightLeg);
		this.leftboot.copyModelAngles(this.bipedLeftLeg);
		this.rightboot.copyModelAngles(this.bipedRightLeg);
	}
	@Override
	public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a) {

		head.showModel = slot == EquipmentSlotType.HEAD;

		leftleg.showModel = slot == EquipmentSlotType.LEGS;
		rightleg.showModel = slot == EquipmentSlotType.LEGS;

		body.showModel = slot == EquipmentSlotType.CHEST;
		leftarm.showModel = slot == EquipmentSlotType.CHEST;
		rightarm.showModel = slot == EquipmentSlotType.CHEST;

		leftboot.showModel = slot == EquipmentSlotType.FEET;
		rightboot.showModel = slot == EquipmentSlotType.FEET;

		bipedHeadwear.showModel = false;
		bipedHead = head;

		bipedBody = body;
		bipedRightArm = rightarm;
		bipedLeftArm = leftarm;

		if (slot == EquipmentSlotType.LEGS)
		{
			bipedRightLeg = rightleg;
			bipedLeftLeg = leftleg;
		}
		else
		{
			bipedRightLeg = rightboot;
			bipedLeftLeg = leftboot;
		}
		super.render(ms, buffer, light, overlay, r, g, b, a);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}