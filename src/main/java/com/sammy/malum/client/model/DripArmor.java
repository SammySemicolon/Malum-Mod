package com.sammy.malum.client.model;
// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class DripArmor extends ArmorModel
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

	public DripArmor(EquipmentSlotType slot) {
		super(slot, 64, 64);


		leg_r = new ModelRenderer(this);
		leg_r.setRotationPoint(-2.0F, 12.0F, 0.0F);
		leg_r.setTextureOffset(0, 43).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.45F, false);

		boot_r = new ModelRenderer(this);
		boot_r.setRotationPoint(0.0F, 0.0F, 0.0F);
		leg_r.addChild(boot_r);
		boot_r.setTextureOffset(16, 53).addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.9F, false);

		leg_l = new ModelRenderer(this);
		leg_l.setRotationPoint(2.0F, 12.0F, 0.0F);
		leg_l.setTextureOffset(0, 43).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.45F, true);

		boot_l = new ModelRenderer(this);
		boot_l.setRotationPoint(0.0F, 0.0F, 0.0F);
		leg_l.addChild(boot_l);
		boot_l.setTextureOffset(16, 53).addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.9001F, true);

		torso = new ModelRenderer(this);
		torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		torso.setTextureOffset(40, 17).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 13.0F, 4.0F, 0.95F, false);
		torso.setTextureOffset(16, 17).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 13.0F, 4.0F, 0.75F, false);

		arm_r = new ModelRenderer(this);
		arm_r.setRotationPoint(-6.0F, 2.0F, 0.0F);
		arm_r.setTextureOffset(0, 30).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.5F, true);
		arm_r.setTextureOffset(0, 17).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.7F, true);

		arm_l = new ModelRenderer(this);
		arm_l.setRotationPoint(6.0F, 2.0F, 0.0F);
		arm_l.setTextureOffset(0, 30).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.5F, false);
		arm_l.setTextureOffset(0, 17).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.7F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(head, -0.0436F, 0.0F, 0.0F);
		head.setTextureOffset(32, 0).addBox(-4.0F, -19.0F, -4.0F, 8.0F, 9.0F, 8.0F, 0.35F, false);
		head.setTextureOffset(0, 0).addBox(-4.0F, -18.75F, -4.0F, 8.0F, 9.0F, 8.0F, 0.0F, false);
		head.setTextureOffset(16, 41).addBox(-4.5F, -9.5F, -4.5F, 9.0F, 3.0F, 9.0F, 0.25F, false);

		leggings = new ModelRenderer(this);
		leggings.setRotationPoint(0.0F, 0.0F, 0.0F);
		leggings.setTextureOffset(16, 34).addBox(-4.0F, 9.0F, -2.0F, 8.0F, 3.0F, 4.0F, 0.5F, false);
	}

	@Override
	public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a)
	{
		head.showModel = slot == EquipmentSlotType.HEAD;

		leggings.showModel = slot == EquipmentSlotType.LEGS;
		leg_r.showModel = slot == EquipmentSlotType.LEGS;
		leg_l.showModel = slot == EquipmentSlotType.LEGS;

		torso.showModel = slot == EquipmentSlotType.CHEST;
		arm_r.showModel = slot == EquipmentSlotType.CHEST;
		arm_l.showModel = slot == EquipmentSlotType.CHEST;

		boot_r.showModel = slot == EquipmentSlotType.FEET;
		boot_l.showModel = slot == EquipmentSlotType.FEET;

		bipedHeadwear.showModel = false;
		bipedHead = head;

		bipedBody = torso;
		bipedRightArm = arm_r;
		bipedLeftArm = arm_l;

		if (slot == EquipmentSlotType.LEGS)
		{
			bipedBody = leggings;
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