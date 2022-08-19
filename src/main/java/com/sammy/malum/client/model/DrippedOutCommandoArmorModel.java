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

public class DrippedOutCommandoArmorModel extends LodestoneArmorModel {
	public static ModelLayerLocation LAYER = new ModelLayerLocation(MalumMod.malumPath("drippy_commando_armor"), "main");

	public DrippedOutCommandoArmorModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
		PartDefinition root = createHumanoidAlias(mesh);
		
		PartDefinition body = root.getChild("body");
		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 18).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 11.0F, 5.0F, new CubeDeformation(0.0F))
			.texOffs(28, 18).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 11.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leggings = root.getChild("leggings");
		PartDefinition codpiece = leggings.addOrReplaceChild("codpiece", CubeListBuilder.create().texOffs(0, 55).addBox(-4.5F, -13.5F, -2.5F, 9.0F, 3.0F, 5.0F, new CubeDeformation(0.015F))
			.texOffs(28, 55).addBox(-4.5F, -13.5F, -2.5F, 9.0F, 3.0F, 5.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition right_legging = root.getChild("right_legging");
		PartDefinition right_leg = right_legging.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 64).mirror().addBox(-2.5F, -0.5F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.01F)).mirror(false)
			.texOffs(20, 64).mirror().addBox(-2.5F, -0.5F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.26F)).mirror(false), PartPose.offset(0, 0, 0.0F));
		PartDefinition right_foot = root.getChild("right_foot");
		PartDefinition right_boot = right_foot.addOrReplaceChild("right_boot", CubeListBuilder.create().texOffs(0, 77).addBox(-3.0F, 7.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
			.texOffs(24, 77).addBox(-3.0F, 7.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_arm = root.getChild("right_arm");
		PartDefinition right_shoulder = right_arm.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(0, 45).mirror().addBox(-4.5F, 5.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.02F)).mirror(false)
			.texOffs(20, 45).mirror().addBox(-4.5F, 5.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.26F)).mirror(false)
			.texOffs(8, 34).addBox(-4.5F, -2.5F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.01F))
			.texOffs(28, 34).addBox(-4.5F, -2.5F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.26F)), PartPose.offset(1, 0, 0.0F));

		PartDefinition left_legging = root.getChild("left_legging");
		PartDefinition left_leg = left_legging.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 64).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
			.texOffs(20, 64).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(0, 0, 0));
		PartDefinition left_foot = root.getChild("left_foot");
		PartDefinition left_boot = left_foot.addOrReplaceChild("left_boot", CubeListBuilder.create().texOffs(0, 77).mirror().addBox(-3.0F, 7.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.01F)).mirror(false)
			.texOffs(24, 77).mirror().addBox(-3.0F, 7.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = root.getChild("left_arm");
		PartDefinition left_shoulder = left_arm.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(8, 34).mirror().addBox(-0.5F, -2.5F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.01F)).mirror(false)
			.texOffs(28, 34).mirror().addBox(-0.5F, -2.5F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.26F)).mirror(false)
			.texOffs(0, 45).addBox(-0.5F, 5.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.02F))
			.texOffs(20, 45).addBox(-0.5F, 5.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.26F)), PartPose.offset(-1, 0, 0.0F));

		PartDefinition head = root.getChild("head");
		PartDefinition helmet = head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -8.5F, -5.0F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.1F))
			.texOffs(36, 0).addBox(-4.5F, -8.5F, -5.0F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.35F)), PartPose.offset(0.0F, 0.0F, 0));

		return LayerDefinition.create(mesh, 128, 128);
	}
}