package com.sammy.malum.client.model;// Made with Blockbench 4.2.5
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.sammy.malum.MalumMod;
import com.sammy.ortus.systems.model.OrtusArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class GenericSlimArmorModel extends OrtusArmorModel {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(MalumMod.malumPath("generic_slim_armor_model"), "main");

	public GenericSlimArmorModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
		PartDefinition root = createHumanoidAlias(mesh);

		PartDefinition head = root.getChild("head");
		PartDefinition helmet = head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.525F))
				.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = root.getChild("body");
		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.525F))
				.texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_arm = root.getChild("right_arm");
		PartDefinition right_shoulder = right_arm.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.525F))
				.texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0, 0, 0.0F));

		PartDefinition left_arm = root.getChild("left_arm");
		PartDefinition left_shoulder = left_arm.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.525F))
				.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0, 0, 0.0F));

		PartDefinition right_legging = root.getChild("right_legging");
		PartDefinition right_leg = right_legging.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.525F))
				.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0, 0, 0.0F));

		PartDefinition left_legging = root.getChild("left_legging");
		PartDefinition left_leg = left_legging.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.525F))
				.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0, 0, 0.0F));

		return LayerDefinition.create(mesh, 64, 64);
	}
}