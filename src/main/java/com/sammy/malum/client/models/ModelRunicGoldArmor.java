package com.sammy.malum.client.models;// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class ModelRunicGoldArmor extends ModelArmor
{
	private final ModelRenderer torso;
	private final ModelRenderer chestplate_slope;
	private final ModelRenderer head;
	private final ModelRenderer arm_r;
	private final ModelRenderer arm_l;
	private final ModelRenderer leg_r;
	private final ModelRenderer boot_r;
	private final ModelRenderer boot_wing_r;
	private final ModelRenderer thigh_guard_r;
	private final ModelRenderer leg_l;
	private final ModelRenderer thigh_guard_l;
	private final ModelRenderer boot_l;
	private final ModelRenderer boot_wing_l;

	public ModelRunicGoldArmor(EquipmentSlotType slot) {
		super(slot, 64, 64);
		textureWidth = 64;
		textureHeight = 64;
		
		torso = new ModelRenderer(this);
		torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		torso.setTextureOffset(0, 25).addBox(-5.0F, 1.0F, -3.0F, 10.0F, 5.0F, 6.0F, 0.0F, false);
		torso.setTextureOffset(0, 36).addBox(-4.5F, 5.5F, -2.5F, 9.0F, 6.0F, 5.0F, 0.0F, false);
		torso.setTextureOffset(26, 23).addBox(-5.0F, -1.0F, -3.0F, 3.0F, 2.0F, 6.0F, 0.0F, true);
		torso.setTextureOffset(26, 23).addBox(2.0F, -1.0F, -3.0F, 3.0F, 2.0F, 6.0F, 0.0F, false);
		torso.setTextureOffset(30, 16).addBox(2.0F, -1.0F, 3.0F, 3.0F, 5.0F, 2.0F, 0.0F, false);
		torso.setTextureOffset(30, 16).addBox(-5.0F, -1.0F, 3.0F, 3.0F, 5.0F, 2.0F, 0.0F, true);
		
		chestplate_slope = new ModelRenderer(this);
		chestplate_slope.setRotationPoint(-8.0F, 6.0F, 5.0F);
		torso.addChild(chestplate_slope);
		setRotationAngle(chestplate_slope, 0.2618F, 0.0F, 0.0F);
		chestplate_slope.setTextureOffset(19, 22).addBox(6.0F, -2.0706F, -7.7274F, 4.0F, 2.0F, 1.0F, 0.0F, false);
		
		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.setTextureOffset(0, 0).addBox(-4.5F, -9.0F, -5.0F, 9.0F, 4.0F, 6.0F, 0.0F, false);
		head.setTextureOffset(21, 1).addBox(-1.5F, -10.0F, -6.0F, 3.0F, 6.0F, 9.0F, 0.0F, false);
		head.setTextureOffset(36, 0).addBox(-5.0F, -5.0F, -5.0F, 2.0F, 6.0F, 4.0F, 0.0F, true);
		head.setTextureOffset(0, 10).addBox(-5.0F, -10.0F, -1.0F, 2.0F, 7.0F, 7.0F, 0.0F, true);
		head.setTextureOffset(0, 10).addBox(3.0F, -10.0F, -1.0F, 2.0F, 7.0F, 7.0F, 0.0F, false);
		head.setTextureOffset(11, 10).addBox(3.0F, -10.0F, 6.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		head.setTextureOffset(11, 10).addBox(-5.0F, -10.0F, 6.0F, 2.0F, 4.0F, 2.0F, 0.0F, true);
		head.setTextureOffset(36, 0).addBox(3.0F, -5.0F, -5.0F, 2.0F, 6.0F, 4.0F, 0.0F, false);
		
		arm_r = new ModelRenderer(this);
		arm_r.setRotationPoint(-4.0F, 2.0F, 0.0F);
		arm_r.setTextureOffset(44, 14).addBox(-5.0F, -4.0F, -3.0F, 4.0F, 6.0F, 6.0F, 0.0F, true);
		arm_r.setTextureOffset(42, 26).addBox(-4.5F, 5.0F, -3.0F, 5.0F, 6.0F, 6.0F, 0.0F, true);
		arm_r.setTextureOffset(48, 7).addBox(-4.5F, 3.0F, -2.5F, 3.0F, 2.0F, 5.0F, 0.0F, true);
		
		arm_l = new ModelRenderer(this);
		arm_l.setRotationPoint(4.0F, 2.0F, 0.0F);
		arm_l.setTextureOffset(44, 14).addBox(1.0F, -4.0F, -3.0F, 4.0F, 6.0F, 6.0F, 0.0F, false);
		arm_l.setTextureOffset(42, 26).addBox(-0.5F, 5.0F, -3.0F, 5.0F, 6.0F, 6.0F, 0.0F, false);
		arm_l.setTextureOffset(48, 7).addBox(1.5F, 3.0F, -2.5F, 3.0F, 2.0F, 5.0F, 0.0F, false);
		
		leg_r = new ModelRenderer(this);
		leg_r.setRotationPoint(-2.0F, 12.0F, 0.0F);
		leg_r.setTextureOffset(12, 47).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 7.0F, 5.0F, 0.0F, true);
		
		boot_r = new ModelRenderer(this);
		boot_r.setRotationPoint(0.0F, 0.0F, 0.0F);
		leg_r.addChild(boot_r);
		boot_r.setTextureOffset(29, 39).addBox(-3.0F, 6.0F, -3.0F, 6.0F, 7.0F, 6.0F, 0.0F, true);
		
		boot_wing_r = new ModelRenderer(this);
		boot_wing_r.setRotationPoint(0.0F, 0.0F, 0.0F);
		boot_r.addChild(boot_wing_r);
		setRotationAngle(boot_wing_r, 0.7854F, 0.0F, 0.0F);
		boot_wing_r.setTextureOffset(25, 53).addBox(-4.0F, 4.3639F, -8.364F, 1.0F, 4.0F, 7.0F, 0.0F, true);
		
		thigh_guard_r = new ModelRenderer(this);
		thigh_guard_r.setRotationPoint(0.0F, 0.0F, 0.0F);
		leg_r.addChild(thigh_guard_r);
		setRotationAngle(thigh_guard_r, 0.0F, 0.0F, 0.1745F);
		thigh_guard_r.setTextureOffset(0, 53).addBox(-3.0F, -1.0003F, -3.0F, 3.0F, 5.0F, 6.0F, 0.0F, true);
		
		leg_l = new ModelRenderer(this);
		leg_l.setRotationPoint(2.0F, 12.0F, 0.0F);
		leg_l.setTextureOffset(12, 47).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 7.0F, 5.0F, 0.0F, false);
		
		thigh_guard_l = new ModelRenderer(this);
		thigh_guard_l.setRotationPoint(0.0F, 0.0F, 0.0F);
		leg_l.addChild(thigh_guard_l);
		setRotationAngle(thigh_guard_l, 0.0F, 0.0F, -0.1745F);
		thigh_guard_l.setTextureOffset(0, 53).addBox(0.0F, -0.9997F, -3.0F, 3.0F, 5.0F, 6.0F, 0.0F, false);
		
		boot_l = new ModelRenderer(this);
		boot_l.setRotationPoint(0.0F, 0.0F, 0.0F);
		leg_l.addChild(boot_l);
		boot_l.setTextureOffset(29, 39).addBox(-3.0F, 6.0F, -3.0F, 6.0F, 7.0F, 6.0F, 0.0F, false);
		
		boot_wing_l = new ModelRenderer(this);
		boot_wing_l.setRotationPoint(0.0F, 0.0F, 0.0F);
		boot_l.addChild(boot_wing_l);
		setRotationAngle(boot_wing_l, 0.7854F, 0.0F, 0.0F);
		boot_wing_l.setTextureOffset(25, 53).addBox(3.0F, 4.3639F, -8.364F, 1.0F, 4.0F, 7.0F, 0.0F, false);
	}
	
	@Override
	public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a)
	{
		
		head.showModel = slot == EquipmentSlotType.HEAD;
		
		leg_r.showModel = slot == EquipmentSlotType.LEGS;
		leg_l.showModel = slot == EquipmentSlotType.LEGS;
		thigh_guard_r.showModel = slot == EquipmentSlotType.LEGS;
		thigh_guard_l.showModel = slot == EquipmentSlotType.LEGS;
		
		torso.showModel = slot == EquipmentSlotType.CHEST;
		chestplate_slope.showModel = slot == EquipmentSlotType.CHEST;
		arm_r.showModel = slot == EquipmentSlotType.CHEST;
		arm_l.showModel = slot == EquipmentSlotType.CHEST;
		
		boot_r.showModel = slot == EquipmentSlotType.FEET;
		boot_l.showModel = slot == EquipmentSlotType.FEET;
		
		boot_wing_r.showModel = slot == EquipmentSlotType.FEET;
		boot_wing_l.showModel = slot == EquipmentSlotType.FEET;
		
		bipedHeadwear.showModel = false;
		bipedHead = head;
		
		bipedBody = torso;
		bipedRightArm = arm_r;
		bipedLeftArm = arm_l;
		
		if (slot == EquipmentSlotType.LEGS)
		{
			bipedRightLeg = leg_r;
			bipedLeftLeg = leg_l;
		}
		else
		{
			bipedRightLeg = boot_r;
			bipedLeftLeg = boot_l;
		}
		super.render(ms, buffer, light, overlay, r, g, b, a);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}