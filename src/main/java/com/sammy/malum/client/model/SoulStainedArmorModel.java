package com.sammy.malum.client.model;// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class SoulStainedArmorModel extends ArmorModel
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
	
	public SoulStainedArmorModel(EquipmentSlotType slot) {
		super(slot, 64, 64);
		leg_r = new ModelRenderer(this);
		leg_r.setPos(-2.0F, 12.0F, 0.0F);
		leg_r.texOffs(0, 33).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.5F, false);
		leg_r.texOffs(0, 45).addBox(-2.0F, 1.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.75F, false);

		boot_r = new ModelRenderer(this);
		boot_r.setPos(0.0F, 0.0F, 0.0F);
		leg_r.addChild(boot_r);
		boot_r.texOffs(16, 39).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.5F, false);
		boot_r.texOffs(16, 46).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.75F, false);

		leg_l = new ModelRenderer(this);
		leg_l.setPos(2.0F, 12.0F, 0.0F);
		leg_l.texOffs(0, 33).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.5001F, true);
		leg_l.texOffs(0, 45).addBox(-2.0F, 1.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.7501F, true);

		boot_l = new ModelRenderer(this);
		boot_l.setPos(0.0F, 0.0F, 0.0F);
		leg_l.addChild(boot_l);
		boot_l.texOffs(16, 39).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.5001F, true);
		boot_l.texOffs(16, 46).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.7501F, true);

		torso = new ModelRenderer(this);
		torso.setPos(0.0F, 0.0F, 0.0F);
		torso.texOffs(24, 17).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 1.001F, false);
		torso.texOffs(0, 17).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.501F, false);

		arm_r = new ModelRenderer(this);
		arm_r.setPos(-6.0F, 2.0F, 0.0F);
		arm_r.texOffs(48, 17).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.5F, true);
		arm_r.texOffs(48, 30).addBox(-3.5F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.75F, true);

		arm_l = new ModelRenderer(this);
		arm_l.setPos(6.0F, 2.0F, 0.0F);
		arm_l.texOffs(48, 30).addBox(-0.5F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.75F, false);
		arm_l.texOffs(48, 17).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.5F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 9.0F, 8.0F, 0.5F, false);
		head.texOffs(32, 0).addBox(-4.0F, -8.5F, -4.0F, 8.0F, 9.0F, 8.0F, 0.75F, false);

		leggings = new ModelRenderer(this);
		leggings.setPos(0.0F, 0.0F, 0.0F);
		leggings.texOffs(16, 33).addBox(-4.0F, 10.0F, -2.0F, 8.0F, 2.0F, 4.0F, 0.5002F, false);
	}
	
	@Override
	public void renderToBuffer(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a)
	{
		head.visible = slot == EquipmentSlotType.HEAD;
		
		leggings.visible = slot == EquipmentSlotType.LEGS;
		leg_r.visible = slot == EquipmentSlotType.LEGS;
		leg_l.visible = slot == EquipmentSlotType.LEGS;
		
		torso.visible = slot == EquipmentSlotType.CHEST;
		arm_r.visible = slot == EquipmentSlotType.CHEST;
		arm_l.visible = slot == EquipmentSlotType.CHEST;
		
		boot_r.visible = slot == EquipmentSlotType.FEET;
		boot_l.visible = slot == EquipmentSlotType.FEET;
		
		hat.visible = false;
		head = head;
		
		body = torso;
		rightArm = arm_r;
		leftArm = arm_l;
		
		if (slot == EquipmentSlotType.LEGS)
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