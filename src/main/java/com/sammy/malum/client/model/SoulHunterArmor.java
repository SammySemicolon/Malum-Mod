package com.sammy.malum.client.model;
// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.client.model.ArmorModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.world.entity.EquipmentSlot;

public class SoulHunterArmor extends ArmorModel
{
	private final ModelRenderer leg_r;
	private final ModelRenderer boot_r;
	private final ModelRenderer leg_l;
	private final ModelRenderer boot_l;
	private final ModelRenderer torso;
	private final ModelRenderer arm_r;
	private final ModelRenderer arm_l;
	private final ModelRenderer head;
	private final ModelRenderer leggings;

	public SoulHunterArmor(EquipmentSlot slot) {
		super(slot, 64, 64);

		leg_r = new ModelRenderer(this);
		leg_r.setPos(-2.0F, 12.0F, 0.0F);
		leg_r.texOffs(0, 42).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.45F, false);

		boot_r = new ModelRenderer(this);
		boot_r.setPos(0.0F, 0.0F, 0.0F);
		leg_r.addChild(boot_r);
		boot_r.texOffs(0, 56).addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.9F, false);

		leg_l = new ModelRenderer(this);
		leg_l.setPos(2.0F, 12.0F, 0.0F);
		leg_l.texOffs(0, 42).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.45F, true);

		boot_l = new ModelRenderer(this);
		boot_l.setPos(0.0F, 0.0F, 0.0F);
		leg_l.addChild(boot_l);
		boot_l.texOffs(0, 56).addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.9001F, true);

		torso = new ModelRenderer(this);
		torso.setPos(0.0F, 0.0F, 0.0F);
		torso.texOffs(40, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 13.0F, 4.0F, 0.95F, false);
		torso.texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 13.0F, 4.0F, 0.75F, false);

		arm_r = new ModelRenderer(this);
		arm_r.setPos(-6.0F, 2.0F, 0.0F);
		arm_r.texOffs(0, 29).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.5F, true);
		arm_r.texOffs(0, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.7F, true);

		arm_l = new ModelRenderer(this);
		arm_l.setPos(6.0F, 2.0F, 0.0F);
		arm_l.texOffs(0, 29).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.5F, false);
		arm_l.texOffs(0, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.7F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.5F, false);
		head.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.7F, false);

		leggings = new ModelRenderer(this);
		leggings.setPos(0.0F, 0.0F, 0.0F);
		leggings.texOffs(16, 33).addBox(-4.0F, 9.0F, -2.0F, 8.0F, 3.0F, 4.0F, 0.5F, false);
	}

	@Override
	public void renderToBuffer(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a)
	{
		head.visible = slot == EquipmentSlot.HEAD;

		leggings.visible = slot == EquipmentSlot.LEGS;
		leg_r.visible = slot == EquipmentSlot.LEGS;
		leg_l.visible = slot == EquipmentSlot.LEGS;

		torso.visible = slot == EquipmentSlot.CHEST;
		arm_r.visible = slot == EquipmentSlot.CHEST;
		arm_l.visible = slot == EquipmentSlot.CHEST;

		boot_r.visible = slot == EquipmentSlot.FEET;
		boot_l.visible = slot == EquipmentSlot.FEET;

		hat.visible = false;
		head = head;

		body = torso;
		rightArm = arm_r;
		leftArm = arm_l;

		if (slot == EquipmentSlot.LEGS)
		{
			body = leggings;
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