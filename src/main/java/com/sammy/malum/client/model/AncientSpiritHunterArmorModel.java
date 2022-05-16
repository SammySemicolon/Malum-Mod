package com.sammy.malum.client.model;
// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.sammy.malum.MalumMod;
import com.sammy.ortus.systems.model.OrtusArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class AncientSpiritHunterArmorModel extends OrtusArmorModel {
	public static ModelLayerLocation LAYER = new ModelLayerLocation(MalumMod.prefix("textures/armor/spirit_hunter"), "main");

	public AncientSpiritHunterArmorModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
		PartDefinition root = createHumanoidAlias(mesh);

		PartDefinition body = root.getChild("body");
		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(40, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 13.0F, 4.0F, new CubeDeformation(0.95F)).texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 13.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leggings = root.getChild("leggings");
		PartDefinition codpiece = leggings.addOrReplaceChild("codpiece", CubeListBuilder.create().texOffs(16, 33).addBox(-4.0F, 9.0F, -2.0F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_legging = root.getChild("right_legging");
		PartDefinition right_leg = right_legging.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 42).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.45F)), PartPose.offset(0, 0, 0.0F));
		PartDefinition right_foot = root.getChild("right_foot");
		PartDefinition right_boot = right_foot.addOrReplaceChild("right_boot", CubeListBuilder.create().texOffs(0, 55).addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.9F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_arm = root.getChild("right_arm");
		PartDefinition right_shoulder = right_arm.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(0, 29).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false).texOffs(0, 16).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.7F)).mirror(false), PartPose.offset(0, 0, 0.0F));

		PartDefinition left_legging = root.getChild("left_legging");
		PartDefinition left_leg = left_legging.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.45F)).mirror(false), PartPose.offset(0, 0, 0));
		PartDefinition left_foot = root.getChild("left_foot");
		PartDefinition left_boot = left_foot.addOrReplaceChild("left_boot", CubeListBuilder.create().texOffs(0, 55).mirror().addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.9001F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = root.getChild("left_arm");
		PartDefinition left_shoulder = left_arm.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(0, 29).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.5F)).texOffs(0, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.7F)), PartPose.offset(0, 0, 0.0F));

		PartDefinition head = root.getChild("head");
		PartDefinition helmet = head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)).texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.7F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(mesh, 64, 64);
	}
}