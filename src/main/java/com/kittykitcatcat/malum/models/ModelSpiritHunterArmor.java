package com.kittykitcatcat.malum.models;
// Made with Blockbench, By KittyKitCatCat


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class ModelSpiritHunterArmor extends ModelArmor {
	private final ModelRenderer body;
	private final ModelRenderer rotatedBit;
	private final ModelRenderer rightCloak;
	private final ModelRenderer leftCloak;
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
	private final ModelRenderer rightRobes;
	private final ModelRenderer belt;

	public ModelSpiritHunterArmor(EquipmentSlotType slot) {
		super(slot, 64, 64);
		textureWidth = 64;
		textureHeight = 64;
		
		
		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.setTextureOffset(0, 25).addBox(-5.0F, 0.0F, -2.0F, 10.0F, 12.0F, 4.0F, 0.05F, false);
		body.setTextureOffset(0, 16).addBox(-5.0F, 0.0F, -2.5F, 10.0F, 4.0F, 5.0F, 0.151F, false);
		body.setTextureOffset(27, 13).addBox(-5.0F, 4.0F, -2.5F, 4.0F, 2.0F, 5.0F, 0.15F, false);
		body.setTextureOffset(27, 13).addBox(1.0F, 4.0F, -2.5F, 4.0F, 2.0F, 5.0F, 0.15F, false);
		
		rotatedBit = new ModelRenderer(this);
		rotatedBit.setRotationPoint(0.0F, 21.975F, 6.75F);
		body.addChild(rotatedBit);
		setRotationAngle(rotatedBit, 0.3491F, 0.0F, 0.0F);
		rotatedBit.setTextureOffset(42, 24).addBox(-1.0F, -19.9317F, -2.6047F, 2.0F, 2.0F, 1.0F, 0.1F, false);
		
		rightCloak = new ModelRenderer(this);
		rightCloak.setRotationPoint(0.0F, 6.0F, 0.0F);
		body.addChild(rightCloak);
		setRotationAngle(rightCloak, 0.0F, 0.0F, 0.0698F);
		
		
		leftCloak = new ModelRenderer(this);
		leftCloak.setRotationPoint(0.0F, 6.0F, 0.0F);
		body.addChild(leftCloak);
		setRotationAngle(leftCloak, 0.0F, 0.0F, -0.0698F);
		
		
		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.05F, false);
		head.setTextureOffset(32, 0).addBox(-5.0F, -8.5F, -5.0F, 2.0F, 9.0F, 4.0F, 0.0F, false);
		head.setTextureOffset(40, 9).addBox(-2.0F, -8.5F, -5.0F, 4.0F, 2.0F, 5.0F, 0.0F, false);
		head.setTextureOffset(32, 0).addBox(3.0F, -8.5F, -5.0F, 2.0F, 9.0F, 4.0F, 0.0F, false);
		head.setTextureOffset(44, 0).addBox(3.0F, -8.5F, -1.0F, 2.0F, 3.0F, 6.0F, 0.0F, false);
		head.setTextureOffset(44, 0).addBox(-5.0F, -8.5F, -1.0F, 2.0F, 3.0F, 6.0F, 0.0F, false);
		
		leftarm = new ModelRenderer(this);
		leftarm.setRotationPoint(2.0F, 2.0F, 0.0F);
		leftarm.setTextureOffset(38, 20).addBox(4.0F, -4.0F, -3.0F, 1.0F, 6.0F, 1.0F, 0.15F, true);
		leftarm.setTextureOffset(30, 20).addBox(3.0F, -5.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.25F, true);
		leftarm.setTextureOffset(41, 16).addBox(1.0F, -3.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.1F, true);
		leftarm.setTextureOffset(38, 20).addBox(4.0F, -4.0F, 2.0F, 1.0F, 6.0F, 1.0F, 0.15F, true);
		
		leftShoulderPad = new ModelRenderer(this);
		leftShoulderPad.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftarm.addChild(leftShoulderPad);
		setRotationAngle(leftShoulderPad, 0.0F, 0.0F, -0.3491F);
		
		
		rightarm = new ModelRenderer(this);
		rightarm.setRotationPoint(-2.0F, 2.0F, 0.0F);
		rightarm.setTextureOffset(30, 20).addBox(-5.0F, -5.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.25F, false);
		rightarm.setTextureOffset(38, 20).addBox(-5.0F, -4.0F, 2.0F, 1.0F, 6.0F, 1.0F, 0.15F, false);
		rightarm.setTextureOffset(38, 20).addBox(-5.0F, -4.0F, -3.0F, 1.0F, 6.0F, 1.0F, 0.15F, false);
		rightarm.setTextureOffset(36, 22).addBox(-4.0F, -4.0F, 2.0F, 1.0F, 6.0F, 1.0F, 0.15F, false);
		rightarm.setTextureOffset(41, 16).addBox(-5.0F, -3.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.1F, false);
		
		rightShoulderPad = new ModelRenderer(this);
		rightShoulderPad.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightarm.addChild(rightShoulderPad);
		setRotationAngle(rightShoulderPad, 0.0F, 0.0F, 0.3491F);
		
		
		leftboot = new ModelRenderer(this);
		leftboot.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftboot.setTextureOffset(32, 43).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.241F, true);
		
		rightboot = new ModelRenderer(this);
		rightboot.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightboot.setTextureOffset(32, 43).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.24F, false);
		
		leftleg = new ModelRenderer(this);
		leftleg.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftleg.setTextureOffset(32, 33).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.12F, false);
		
		leftRobes = new ModelRenderer(this);
		leftRobes.setRotationPoint(1.0F, 0.0F, 0.0F);
		leftleg.addChild(leftRobes);
		setRotationAngle(leftRobes, 0.0F, 0.0F, -0.2618F);
		leftRobes.setTextureOffset(16, 41).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.245F, false);
		leftRobes.setTextureOffset(43, 47).addBox(-2.25F, 7.26F, -2.5F, 5.0F, 1.0F, 5.0F, 0.0F, true);
		leftRobes.setTextureOffset(48, 33).addBox(-2.25F, -1.25F, -2.5F, 1.0F, 9.0F, 5.0F, 0.01F, true);
		
		rightleg = new ModelRenderer(this);
		rightleg.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightleg.setTextureOffset(32, 33).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.12F, true);
		
		rightRobes = new ModelRenderer(this);
		rightRobes.setRotationPoint(-1.0F, 0.0F, 0.0F);
		rightleg.addChild(rightRobes);
		setRotationAngle(rightRobes, 0.0F, 3.1416F, 0.2618F);
		rightRobes.setTextureOffset(16, 41).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.245F, false);
		rightRobes.setTextureOffset(43, 47).addBox(-2.25F, 7.26F, -2.5F, 5.0F, 1.0F, 5.0F, 0.0F, true);
		rightRobes.setTextureOffset(48, 33).addBox(-2.25F, -1.25F, -2.5F, 1.0F, 9.0F, 5.0F, 0.01F, true);
		
		belt = new ModelRenderer(this);
		belt.setRotationPoint(0.0F, 0.0F, 0.0F);
		belt.setTextureOffset(36, 27).addBox(-5.0F, 10.0F, -2.0F, 10.0F, 2.0F, 4.0F, 0.25F, false);
		belt.setTextureOffset(28, 30).addBox(-0.5F, 9.0F, -2.5F, 1.0F, 4.0F, 1.0F, 0.24F, false);
		belt.setTextureOffset(32, 30).addBox(-2.0F, 9.5F, -2.75F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		belt.setTextureOffset(32, 30).addBox(1.0F, 9.5F, -2.75F, 1.0F, 3.0F, 1.0F, 0.0F, false);
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