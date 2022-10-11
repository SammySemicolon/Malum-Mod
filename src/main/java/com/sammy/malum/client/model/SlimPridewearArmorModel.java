package com.sammy.malum.client.model;
// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.sammy.malum.MalumMod;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

public class SlimPridewearArmorModel extends LodestoneArmorModel {
	public static ModelLayerLocation LAYER = new ModelLayerLocation(MalumMod.malumPath("slim_pridewear"), "main");

	public SlimPridewearArmorModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
		PartDefinition root = createHumanoidAlias(mesh);

		PartDefinition body = root.getChild("body");
		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(16, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.525F))
			.texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leggings = root.getChild("leggings");
		PartDefinition codpiece = leggings.addOrReplaceChild("codpiece", CubeListBuilder.create().texOffs(0, 56).addBox(-5.0F, -14.0F, -3.0F, 10.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24f, 0.0F));

		PartDefinition right_legging = root.getChild("right_legging");
		PartDefinition right_leg = right_legging.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 48).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.625F))
			.texOffs(16, 48).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.85F)), PartPose.offset(0, 0, 0.0F));
		PartDefinition right_foot = root.getChild("right_foot");
		PartDefinition right_boot = right_foot.addOrReplaceChild("right_boot", CubeListBuilder.create().texOffs(0, 4).addBox(-1.9F, 4.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.625F))
			.texOffs(0, 20).addBox(-1.9F, 4.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.85F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_arm = root.getChild("right_arm");
		PartDefinition right_shoulder = right_arm.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(40, 0).addBox(-4.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.525F))
				.texOffs(40, 16).addBox(-4.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(2, 0, 0.0F));

		PartDefinition left_legging = root.getChild("left_legging");
		PartDefinition left_leg = left_legging.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(32, 48).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.625F))
			.texOffs(48, 48).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.85F)), PartPose.offset(0, 0, 0.0F));
		PartDefinition left_foot = root.getChild("left_foot");
		PartDefinition left_boot = left_foot.addOrReplaceChild("left_boot", CubeListBuilder.create().texOffs(16, 36).addBox(-2.1F, 4.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.625F))
			.texOffs(0, 36).addBox(-2.1F, 4.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.85F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = root.getChild("left_arm");
		PartDefinition left_shoulder = left_arm.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(32, 32).addBox(1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.525F))
				.texOffs(48, 32).addBox(1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(-2.0F, 0, 0.0F));

		PartDefinition head = root.getChild("head");
		PartDefinition beanie = head.addOrReplaceChild("beanie", CubeListBuilder.create().texOffs(100, 60).addBox(-1.5F, -33.3512F, -8.053F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.2F))
			.texOffs(64, 53).addBox(-4.6F, -32.4238F, -15.2587F, 9.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
			.texOffs(64, 53).addBox(-4.6F, -32.4238F, -15.2587F, 9.0F, 3.0F, 8.0F, new CubeDeformation(0.2F))
			.texOffs(64, 35).addBox(-5.0F, -29.3512F, -16.303F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.2F))
			.texOffs(100, 28).addBox(-1.5F, -33.3512F, -8.053F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(64, 21).addBox(-4.6F, -32.4238F, -15.2587F, 9.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
			.texOffs(64, 3).addBox(-5.0F, -29.3512F, -16.303F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24f, 0.0F, -0.3927F, 0.0F, 0.0F));

		return LayerDefinition.create(mesh, 128, 64);
	}
}