package com.kittykitcatcat.malum.models;
// Made with Blockbench, By KittyKitCatCat


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class ModelSpiritHunterArmor extends ModelArmor {
	private final ModelRenderer body;
	private final ModelRenderer rightCloak;
	private final ModelRenderer leftCloak;
	private final ModelRenderer head;
	private final ModelRenderer backpiece;
	private final ModelRenderer topPiece;
	private final ModelRenderer leftarm;
	private final ModelRenderer leftShoulderPad;
	private final ModelRenderer rightarm;
	private final ModelRenderer rightShoulderPad;
	private final ModelRenderer leftboot;
	private final ModelRenderer rightboot;
	private final ModelRenderer leftleg;
	private final ModelRenderer leftRobes;
	private final ModelRenderer rightleg;
	private final ModelRenderer rightRobes;
	private final ModelRenderer belt;
	private final ModelRenderer beltDiamond;

	public ModelSpiritHunterArmor(EquipmentSlotType slot) {
		super(slot, 64, 64);
		textureWidth = 64;
		textureHeight = 64;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.setTextureOffset(50, 22).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 12.0F, 4.0F, 0.02F, false);

		rightCloak = new ModelRenderer(this);
		rightCloak.setRotationPoint(0.0F, 6.0F, 0.0F);
		body.addChild(rightCloak);
		setRotationAngle(rightCloak, 0.0F, 0.0F, 0.0698F);
		rightCloak.setTextureOffset(32, 31).addBox(-4.5F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.17F, false);

		leftCloak = new ModelRenderer(this);
		leftCloak.setRotationPoint(0.0F, 6.0F, 0.0F);
		body.addChild(leftCloak);
		setRotationAngle(leftCloak, 0.0F, 0.0F, -0.0698F);
		leftCloak.setTextureOffset(32, 31).addBox(0.5F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.17F, true);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.setTextureOffset(32, 48).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.2F, false);
		head.setTextureOffset(12, 42).addBox(-3.25F, -5.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.19F, false);
		head.setTextureOffset(12, 42).addBox(3.25F, -5.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.19F, false);

		backpiece = new ModelRenderer(this);
		backpiece.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(backpiece);
		setRotationAngle(backpiece, 0.0F, -1.5708F, 0.0F);
		backpiece.setTextureOffset(28, 44).addBox(4.0F, -6.0F, -3.0F, 0.0F, 6.0F, 6.0F, 0.19F, false);

		topPiece = new ModelRenderer(this);
		topPiece.setRotationPoint(8.0F, 0.0F, 0.0F);
		head.addChild(topPiece);
		setRotationAngle(topPiece, 0.0F, 0.0F, -1.5708F);
		topPiece.setTextureOffset(14, 36).addBox(6.25F, -10.0F, -4.0F, 1.0F, 4.0F, 8.0F, 0.19F, false);
		topPiece.setTextureOffset(12, 55).addBox(5.25F, -11.25F, -4.0F, 2.0F, 1.0F, 8.0F, 0.19F, false);
		topPiece.setTextureOffset(12, 55).addBox(5.25F, -5.75F, -4.0F, 2.0F, 1.0F, 8.0F, 0.19F, false);

		leftarm = new ModelRenderer(this);
		leftarm.setRotationPoint(4.0F, 2.0F, 0.0F);
		leftarm.setTextureOffset(36, 22).addBox(1.0F, 1.0F, -2.0F, 3.0F, 5.0F, 4.0F, 0.25F, true);
		leftarm.setTextureOffset(44, 31).addBox(-1.0F, 5.0F, -2.0F, 2.0F, 1.0F, 1.0F, 0.25F, true);
		leftarm.setTextureOffset(44, 31).addBox(-1.0F, 2.0F, -2.0F, 2.0F, 1.0F, 1.0F, 0.25F, true);
		leftarm.setTextureOffset(44, 31).addBox(-1.0F, 2.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.25F, false);
		leftarm.setTextureOffset(44, 31).addBox(-1.0F, 5.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.25F, false);

		leftShoulderPad = new ModelRenderer(this);
		leftShoulderPad.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftarm.addChild(leftShoulderPad);
		setRotationAngle(leftShoulderPad, 0.0F, 0.0F, -0.3491F);
		leftShoulderPad.setTextureOffset(18, 22).addBox(0.0F, -2.0F, -2.0F, 5.0F, 4.0F, 4.0F, 0.4F, false);

		rightarm = new ModelRenderer(this);
		rightarm.setRotationPoint(-4.0F, 2.0F, 0.0F);
		rightarm.setTextureOffset(44, 31).addBox(-1.0F, 5.0F, -2.0F, 2.0F, 1.0F, 1.0F, 0.25F, false);
		rightarm.setTextureOffset(44, 31).addBox(-1.0F, 2.0F, -2.0F, 2.0F, 1.0F, 1.0F, 0.25F, false);
		rightarm.setTextureOffset(44, 31).addBox(-1.0F, 2.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.25F, true);
		rightarm.setTextureOffset(44, 31).addBox(-1.0F, 5.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.25F, true);
		rightarm.setTextureOffset(36, 22).addBox(-4.0F, 1.0F, -2.0F, 3.0F, 5.0F, 4.0F, 0.25F, false);

		rightShoulderPad = new ModelRenderer(this);
		rightShoulderPad.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightarm.addChild(rightShoulderPad);
		setRotationAngle(rightShoulderPad, 0.0F, 0.0F, 0.3491F);
		rightShoulderPad.setTextureOffset(18, 22).addBox(-5.0F, -2.05F, -2.0F, 5.0F, 4.0F, 4.0F, 0.4F, true);

		leftboot = new ModelRenderer(this);
		leftboot.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftboot.setTextureOffset(20, 0).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.241F, true);

		rightboot = new ModelRenderer(this);
		rightboot.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightboot.setTextureOffset(20, 0).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.24F, false);

		leftleg = new ModelRenderer(this);
		leftleg.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftleg.setTextureOffset(48, 38).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.12F, false);

		leftRobes = new ModelRenderer(this);
		leftRobes.setRotationPoint(1.0F, 0.0F, 0.0F);
		leftleg.addChild(leftRobes);
		setRotationAngle(leftRobes, 0.0F, 0.0F, -0.2618F);
		leftRobes.setTextureOffset(36, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.245F, false);
		leftRobes.setTextureOffset(18, 14).addBox(-2.25F, 7.26F, -2.5F, 5.0F, 1.0F, 5.0F, 0.0F, true);
		leftRobes.setTextureOffset(52, 0).addBox(-2.25F, -1.25F, -2.5F, 1.0F, 9.0F, 5.0F, 0.01F, true);

		rightleg = new ModelRenderer(this);
		rightleg.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightleg.setTextureOffset(48, 38).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.12F, true);

		rightRobes = new ModelRenderer(this);
		rightRobes.setRotationPoint(-1.0F, 0.0F, 0.0F);
		rightleg.addChild(rightRobes);
		setRotationAngle(rightRobes, 0.0F, 3.1416F, 0.2618F);
		rightRobes.setTextureOffset(36, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.245F, false);
		rightRobes.setTextureOffset(18, 14).addBox(-2.25F, 7.26F, -2.5F, 5.0F, 1.0F, 5.0F, 0.0F, true);
		rightRobes.setTextureOffset(52, 0).addBox(-2.25F, -1.25F, -2.5F, 1.0F, 9.0F, 5.0F, 0.01F, true);

		belt = new ModelRenderer(this);
		belt.setRotationPoint(0.0F, 0.0F, 0.0F);
		belt.setTextureOffset(34, 16).addBox(-4.5F, 10.0F, -2.0F, 9.0F, 2.0F, 4.0F, 0.24F, false);

		beltDiamond = new ModelRenderer(this);
		beltDiamond.setRotationPoint(0.0F, -4.5F, 0.0F);
		belt.addChild(beltDiamond);
		setRotationAngle(beltDiamond, 0.0F, 0.0F, -0.7854F);
		beltDiamond.setTextureOffset(56, 16).addBox(-12.2353F, 9.2353F, -2.5F, 3.0F, 3.0F, 1.0F, 0.12F, false);
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
			belt.render(ms,buffer,light,overlay);
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