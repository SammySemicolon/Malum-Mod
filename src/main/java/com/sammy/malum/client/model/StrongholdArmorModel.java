package com.sammy.malum.client.model;// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlot;

public class StrongholdArmorModel extends ArmorModel
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

	public StrongholdArmorModel(EquipmentSlot slot) {
		super(slot, 64, 64);
		
		torso = new ModelRenderer(this);
		torso.setPos(0.0F, 0.0F, 0.0F);
		torso.texOffs(0, 25).addBox(-5.0F, 1.0F, -3.0F, 10.0F, 5.0F, 6.0F, 0.0F, false);
		torso.texOffs(0, 36).addBox(-4.5F, 5.5F, -2.5F, 9.0F, 6.0F, 5.0F, 0.0F, false);
		torso.texOffs(26, 23).addBox(-5.0F, -1.0F, -3.0F, 3.0F, 2.0F, 6.0F, 0.0F, true);
		torso.texOffs(26, 23).addBox(2.0F, -1.0F, -3.0F, 3.0F, 2.0F, 6.0F, 0.0F, false);
		torso.texOffs(30, 16).addBox(2.0F, -1.0F, 3.0F, 3.0F, 5.0F, 2.0F, 0.0F, false);
		torso.texOffs(30, 16).addBox(-5.0F, -1.0F, 3.0F, 3.0F, 5.0F, 2.0F, 0.0F, true);
		
		chestplate_slope = new ModelRenderer(this);
		chestplate_slope.setPos(-8.0F, 6.0F, 5.0F);
		torso.addChild(chestplate_slope);
		setRotationAngle(chestplate_slope, 0.2618F, 0.0F, 0.0F);
		chestplate_slope.texOffs(19, 22).addBox(5.5F, -2.0706F, -7.7274F, 5.0F, 2.0F, 1.0F, 0.0F, false);
		
		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(-4.5F, -9.0F, -5.0F, 9.0F, 4.0F, 6.0F, 0.0F, false);
		head.texOffs(21, 1).addBox(-1.5F, -10.0F, -6.0F, 3.0F, 6.0F, 9.0F, 0.0F, false);
		head.texOffs(36, 0).addBox(-5.0F, -5.0F, -5.0F, 2.0F, 6.0F, 4.0F, 0.0F, true);
		head.texOffs(0, 10).addBox(3.0F, -10.0F, -1.0F, 2.0F, 7.0F, 7.0F, 0.0F, false);
		head.texOffs(0, 10).addBox(-5.0F, -10.0F, -1.0F, 2.0F, 7.0F, 7.0F, 0.0F, true);
		head.texOffs(11, 10).addBox(3.0F, -10.0F, 6.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		head.texOffs(11, 10).addBox(-5.0F, -10.0F, 6.0F, 2.0F, 4.0F, 2.0F, 0.0F, true);
		head.texOffs(36, 0).addBox(3.0F, -5.0F, -5.0F, 2.0F, 6.0F, 4.0F, 0.0F, false);
		
		arm_r = new ModelRenderer(this);
		arm_r.setPos(-5.0F, 2.0F, 0.0F);
		arm_r.texOffs(44, 14).addBox(-4.0F, -4.0F, -3.0F, 4.0F, 6.0F, 6.0F, 0.0F, true);
		arm_r.texOffs(42, 26).addBox(-3.5F, 5.0F, -3.0F, 5.0F, 6.0F, 6.0F, 0.0F, true);
		arm_r.texOffs(48, 7).addBox(-3.5F, 3.0F, -2.5F, 3.0F, 2.0F, 5.0F, 0.0F, true);
		
		arm_l = new ModelRenderer(this);
		arm_l.setPos(5.0F, 2.0F, 0.0F);
		arm_l.texOffs(44, 14).addBox(0.0F, -4.0F, -3.0F, 4.0F, 6.0F, 6.0F, 0.025F, false);
		arm_l.texOffs(42, 26).addBox(-1.5F, 5.0F, -3.0F, 5.0F, 6.0F, 6.0F, 0.025F, false);
		arm_l.texOffs(48, 7).addBox(0.5F, 3.0F, -2.5F, 3.0F, 2.0F, 5.0F, 0.025F, false);
		
		leg_r = new ModelRenderer(this);
		leg_r.setPos(-2.0F, 12.0F, 0.0F);
		leg_r.texOffs(12, 47).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 7.0F, 5.0F, 0.025F, true);
		
		boot_r = new ModelRenderer(this);
		boot_r.setPos(0.0F, 0.0F, 0.0F);
		leg_r.addChild(boot_r);
		
		
		ModelRenderer boot_r_r1 = new ModelRenderer(this);
		boot_r_r1.setPos(2.0F, 12.0F, 0.0F);
		boot_r.addChild(boot_r_r1);
		setRotationAngle(boot_r_r1, 0.0F, -0.0436F, 0.0F);
		boot_r_r1.texOffs(29, 39).addBox(-5.0F, -6.0F, -3.0F, 6.0F, 7.0F, 6.0F, 0.0F, true);
		
		boot_wing_r = new ModelRenderer(this);
		boot_wing_r.setPos(0.0F, 0.0F, 0.0F);
		boot_r.addChild(boot_wing_r);
		setRotationAngle(boot_wing_r, 0.7854F, 0.0F, 0.0F);
		boot_wing_r.texOffs(25, 53).addBox(-4.0F, 4.3639F, -8.364F, 1.0F, 4.0F, 7.0F, 0.025F, true);
		
		thigh_guard_r = new ModelRenderer(this);
		thigh_guard_r.setPos(0.0F, 0.0F, 0.0F);
		leg_r.addChild(thigh_guard_r);
		setRotationAngle(thigh_guard_r, 0.0F, 0.0F, 0.1745F);
		thigh_guard_r.texOffs(0, 53).addBox(-3.0F, -1.0003F, -3.0F, 3.0F, 5.0F, 6.0F, 0.05F, true);
		
		leg_l = new ModelRenderer(this);
		leg_l.setPos(2.0F, 12.0F, 0.0F);
		leg_l.texOffs(12, 47).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 7.0F, 5.0F, 0.0F, false);
		
		thigh_guard_l = new ModelRenderer(this);
		thigh_guard_l.setPos(0.0F, 0.0F, 0.0F);
		leg_l.addChild(thigh_guard_l);
		setRotationAngle(thigh_guard_l, 0.0F, 0.0F, -0.1745F);
		thigh_guard_l.texOffs(0, 53).addBox(0.0F, -0.9997F, -3.0F, 3.0F, 5.0F, 6.0F, 0.0F, false);
		
		boot_l = new ModelRenderer(this);
		boot_l.setPos(0.0F, 0.0F, 0.0F);
		leg_l.addChild(boot_l);
		
		
		ModelRenderer boot_l_r1 = new ModelRenderer(this);
		boot_l_r1.setPos(-2.0F, 12.0F, 0.0F);
		boot_l.addChild(boot_l_r1);
		setRotationAngle(boot_l_r1, 0.0F, 0.0436F, 0.0F);
		boot_l_r1.texOffs(29, 39).addBox(-1.0F, -6.0F, -3.0F, 6.0F, 7.0F, 6.0F, 0.0F, false);
		
		boot_wing_l = new ModelRenderer(this);
		boot_wing_l.setPos(0.0F, 0.0F, 0.0F);
		boot_l.addChild(boot_wing_l);
		setRotationAngle(boot_wing_l, 0.7854F, 0.0F, 0.0F);
		boot_wing_l.texOffs(25, 53).addBox(3.0F, 4.3639F, -8.364F, 1.0F, 4.0F, 7.0F, 0.0F, false);
	}
	
	@Override
	public void renderToBuffer(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a)
	{
		
		head.visible = slot == EquipmentSlot.HEAD;
		
		leg_r.visible = slot == EquipmentSlot.LEGS;
		leg_l.visible = slot == EquipmentSlot.LEGS;
		thigh_guard_r.visible = slot == EquipmentSlot.LEGS;
		thigh_guard_l.visible = slot == EquipmentSlot.LEGS;
		
		torso.visible = slot == EquipmentSlot.CHEST;
		chestplate_slope.visible = slot == EquipmentSlot.CHEST;
		arm_r.visible = slot == EquipmentSlot.CHEST;
		arm_l.visible = slot == EquipmentSlot.CHEST;
		
		boot_r.visible = slot == EquipmentSlot.FEET;
		boot_l.visible = slot == EquipmentSlot.FEET;
		
		boot_wing_r.visible = slot == EquipmentSlot.FEET;
		boot_wing_l.visible = slot == EquipmentSlot.FEET;
		
		hat.visible = false;
		head = head;
		
		body = torso;
		rightArm = arm_r;
		leftArm = arm_l;
		
		if (slot == EquipmentSlot.LEGS)
		{
			rightLeg = leg_r;
			leftLeg = leg_l;
		}
		else
		{
			rightLeg = boot_r;
			leftLeg = boot_l;
		}
		super.renderToBuffer(ms, buffer, light, overlay, r, g, b, a);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}