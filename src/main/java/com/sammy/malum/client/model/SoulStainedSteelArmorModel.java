package com.sammy.malum.client.model;
// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.sammy.malum.MalumMod;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class SoulStainedSteelArmorModel extends LodestoneArmorModel {
	public static ModelLayerLocation LAYER = new ModelLayerLocation(MalumMod.malumPath("soul_stained_steel_armor"), "main");

	public SoulStainedSteelArmorModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
		PartDefinition root = createHumanoidAlias(mesh);

		PartDefinition body = root.getChild("body");
		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 39).addBox(-5.0F, 1.0F, -3.0F, 10.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(32, 39).addBox(-5.0F, 1.0F, -3.0F, 10.0F, 6.0F, 6.0F, new CubeDeformation(0.25F))
				.texOffs(0, 51).addBox(-4.5F, 3.5F, -2.5F, 9.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(28, 51).addBox(-4.5F, 3.5F, -2.5F, 9.0F, 7.0F, 5.0F, new CubeDeformation(0.35F))
				.texOffs(0, 63).mirror().addBox(-5.0F, -1.0F, -3.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(36, 31).addBox(2.0F, -1.0F, -3.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(54, 31).mirror().addBox(-5.0F, -1.0F, -3.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(54, 31).addBox(2.0F, -1.0F, -3.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.25F))
				.texOffs(11, 25).addBox(2.0F, -1.0F, 3.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(11, 25).mirror().addBox(-5.0F, -1.0F, 3.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(29, 25).addBox(2.0F, -1.0F, 3.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.25F))
				.texOffs(29, 25).mirror().addBox(-5.0F, -1.0F, 3.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leggings = root.getChild("leggings");
		PartDefinition codpiece = leggings.addOrReplaceChild("codpiece", CubeListBuilder.create().texOffs(0, 63).addBox(-4.0F, -14.5F, -3.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.01F))
				.texOffs(28, 63).addBox(-4.0F, -14.5F, -3.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.26F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition right_legging = root.getChild("right_legging");
		PartDefinition right_leg = right_legging.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 105).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0, 0, 0.0F));
		PartDefinition right_thigh_guard = right_leg.addOrReplaceChild("right_thigh_guard", CubeListBuilder.create().texOffs(0, 117).addBox(-3.0F, -1.0003F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(18, 117).addBox(-3.0F, -1.0003F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition right_foot = root.getChild("right_foot");
		PartDefinition right_boot = right_foot.addOrReplaceChild("right_boot", CubeListBuilder.create().texOffs(36, 115).mirror().addBox(-3.0F, 6.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(60, 115).mirror().addBox(-3.0F, 6.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition right_boot_wing = right_boot.addOrReplaceChild("right_boot_wing", CubeListBuilder.create().texOffs(84, 117).mirror().addBox(-4.0F, 4.3639F, -8.364F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(100, 117).mirror().addBox(-4.0F, 4.3639F, -8.364F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition right_arm = root.getChild("right_arm");
		PartDefinition right_shoulder = right_arm.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(0, 71).mirror().addBox(-6.0F, -4.0F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.01F)).mirror(false)
				.texOffs(20, 71).mirror().addBox(-6.0F, -4.0F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.26F)).mirror(false)
				.texOffs(0, 83).mirror().addBox(-5.5F, 5.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.02F)).mirror(false)
				.texOffs(22, 83).mirror().addBox(-5.5F, 5.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.27F)).mirror(false)
				.texOffs(0, 95).mirror().addBox(-6.5F, 4.0F, -2.5F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.01F)).mirror(false)
				.texOffs(18, 95).mirror().addBox(-6.5F, 4.0F, -2.5F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.26F)).mirror(false), PartPose.offset(2, 0, 0.0F));

		PartDefinition left_legging = root.getChild("left_legging");
		PartDefinition left_leg = left_legging.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 105).mirror().addBox(-2.5F, -0.5F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0, 0, 0.0F));
		PartDefinition left_thigh_guard = left_leg.addOrReplaceChild("left_thigh_guard", CubeListBuilder.create().texOffs(0, 117).mirror().addBox(0.0F, -0.9997F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(18, 117).mirror().addBox(0.0F, -0.9997F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition left_foot = root.getChild("left_foot");
		PartDefinition left_boot = left_foot.addOrReplaceChild("left_boot", CubeListBuilder.create().texOffs(36, 115).addBox(-3.0F, 6.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.01F))
				.texOffs(60, 115).addBox(-3.0F, 6.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.26F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition left_boot_wing = left_boot.addOrReplaceChild("left_boot_wing", CubeListBuilder.create().texOffs(84, 117).addBox(3.0F, 4.3639F, -8.364F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(100, 117).addBox(3.0F, 4.3639F, -8.364F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition left_arm = root.getChild("left_arm");
		PartDefinition left_shoulder = left_arm.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(0, 71).addBox(2.0F, -4.0F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.01F))
				.texOffs(20, 71).addBox(2.0F, -4.0F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.26F))
				.texOffs(0, 83).addBox(0.5F, 5.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.02F))
				.texOffs(22, 83).addBox(0.5F, 5.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.27F))
				.texOffs(0, 95).addBox(2.5F, 4.0F, -2.5F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.01F))
				.texOffs(18, 95).addBox(2.5F, 4.0F, -2.5F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.26F)), PartPose.offset(-2, 0, 0.0F));

		PartDefinition head = root.getChild("head");
		PartDefinition helmet = head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -9.0F, -5.0F, 3.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).mirror().addBox(1.5F, -9.0F, -5.0F, 3.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(30, 0).addBox(-4.5F, -9.0F, -5.0F, 3.0F, 4.0F, 6.0F, new CubeDeformation(0.25F))
				.texOffs(30, 0).mirror().addBox(1.5F, -9.0F, -5.0F, 3.0F, 4.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(0, 10).addBox(-1.5F, -10.0F, -6.0F, 3.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(24, 10).addBox(-1.5F, -10.0F, -6.0F, 3.0F, 6.0F, 9.0F, new CubeDeformation(0.25F))
				.texOffs(18, 0).addBox(-5.0F, -5.0F, -5.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 25).mirror().addBox(-5.0F, -10.0F, -1.0F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 25).addBox(3.0F, -10.0F, -1.0F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(18, 25).mirror().addBox(-5.0F, -10.0F, -1.0F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(18, 25).addBox(3.0F, -10.0F, -1.0F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.25F))
				.texOffs(15, 13).addBox(3.0F, -10.0F, 6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(15, 13).mirror().addBox(-5.0F, -10.0F, 6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(23, 13).addBox(3.0F, -10.0F, 6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F))
				.texOffs(23, 13).mirror().addBox(-5.0F, -10.0F, 6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(18, 0).mirror().addBox(3.0F, -5.0F, -5.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(48, 0).addBox(-5.0F, -5.0F, -5.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(48, 0).mirror().addBox(3.0F, -5.0F, -5.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(mesh, 128, 128);
	}
}