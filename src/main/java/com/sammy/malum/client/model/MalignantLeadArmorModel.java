package com.sammy.malum.client.model;
// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.model.*;

public class MalignantLeadArmorModel extends LodestoneArmorModel {
    public static ModelLayerLocation LAYER = new ModelLayerLocation(MalumMod.malumPath("malignant_lead_armor"), "main");

    private final ModelPart right_arm_glow;
    private final ModelPart left_arm_glow;

    public MalignantLeadArmorModel(ModelPart root) {
        super(root);
        this.right_arm_glow = root.getChild("right_arm_glow");
        this.left_arm_glow = root.getChild("left_arm_glow");
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        super.renderToBuffer(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
        if (!this.young) {
            if (slot == EquipmentSlot.CHEST) {
                pPoseStack.pushPose();
                final ResourceLocation texture = MalumMod.malumPath("textures/armor/malignant_stronghold.png");
                final VertexConsumer additive = RenderHandler.DELAYED_RENDER.getBuffer(RenderTypeRegistry.MALIGNANT_GLOW.applyAndCache(texture));
                final VertexConsumer transparent = RenderHandler.DELAYED_RENDER.getBuffer(LodestoneRenderTypeRegistry.TRANSPARENT_TEXTURE.applyAndCache(texture));

                right_arm_glow.render(pPoseStack, transparent, RenderHelper.FULL_BRIGHT, pPackedOverlay, pRed, pGreen, pBlue, 0.25f);
                left_arm_glow.render(pPoseStack, transparent, RenderHelper.FULL_BRIGHT, pPackedOverlay, pRed, pGreen, pBlue, 0.25f);

                float gameTime = Minecraft.getInstance().level.getGameTime()+Minecraft.getInstance().getPartialTick();
                int time = 160;
                float distance = 0.01f;
                float alpha = 0.15f;
                for (int i = 0; i < 4; i++) {
                    double angle = i / 4f * (Math.PI * 2);
                    angle += ((gameTime % time) / time) * (Math.PI * 2);
                    double xOffset = (distance * Math.cos(angle));
                    double zOffset = (distance * Math.sin(angle));
                    pPoseStack.translate(xOffset, zOffset / 2f, zOffset);
                    right_arm_glow.render(pPoseStack, additive, RenderHelper.FULL_BRIGHT, pPackedOverlay, pRed, pGreen, pBlue, alpha);
                    left_arm_glow.render(pPoseStack, additive, RenderHelper.FULL_BRIGHT, pPackedOverlay, pRed, pGreen, pBlue, alpha);
                    pPoseStack.translate(-xOffset, -zOffset/2f, -zOffset);
                    distance+=0.0125f;
                }
                pPoseStack.popPose();
            }
        }
    }


    @Override
    public void copyFromDefault(HumanoidModel model) {
        super.copyFromDefault(model);
        right_arm_glow.copyFrom(model.rightArm);
        left_arm_glow.copyFrom(model.leftArm);
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
        PartDefinition root = createHumanoidAlias(mesh);
        PartDefinition right_arm_glow = root.addOrReplaceChild("right_arm_glow", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition left_arm_glow = root.addOrReplaceChild("left_arm_glow", new CubeListBuilder(), PartPose.ZERO);

        PartDefinition body = root.getChild("body");
        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(6, 37).addBox(-5.0F, 2.0F, -3.0F, 10.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(6, 49).addBox(-4.5F, 6.5F, -2.5F, 9.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition thing = torso.addOrReplaceChild("thing", CubeListBuilder.create().texOffs(60, 40).addBox(-6.0F, -1.0F, -5.0F, 12.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition leggings = root.getChild("leggings");
        PartDefinition codpiece = leggings.addOrReplaceChild("codpiece", CubeListBuilder.create().texOffs(6, 61).addBox(-4.0F, -14.5F, -3.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.01F))
                .texOffs(42, 58).addBox(-2.0F, -12.5F, -3.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition right_legging = root.getChild("right_legging");
        PartDefinition right_leg = right_legging.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(53, 88).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));
        PartDefinition right_thigh_guard = right_leg.addOrReplaceChild("right_thigh_guard", CubeListBuilder.create().texOffs(19, 108).addBox(-4.0F, -2.0F, -3.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.0F, 1.1345F));
        PartDefinition right_thigh_guard2 = right_leg.addOrReplaceChild("right_thigh_guard2", CubeListBuilder.create().texOffs(40, 109).addBox(-3.0F, -3.0F, -3.0F, 3.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 4.0F, 0.0F, 0.0F, 0.0F, 1.1345F));

        PartDefinition right_foot = root.getChild("right_foot");
        PartDefinition right_boot = right_foot.addOrReplaceChild("right_boot", CubeListBuilder.create().texOffs(63, 107).mirror().addBox(-3.0F, 6.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_arm = root.getChild("right_arm");
        PartDefinition right_shoulder = right_arm.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(36, 75).mirror().addBox(-7.0F, -4.0F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(7, 88).mirror().addBox(-5.5F, 5.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-3.0F, 2.0F, 0.0F));
        PartDefinition right_shoulder_pad = right_shoulder.addOrReplaceChild("right_shoulder_pad", CubeListBuilder.create().texOffs(4, 73).mirror().addBox(-8.0F, 0.0F, -4.0F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.5F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition glow_right_arm = right_arm_glow.addOrReplaceChild("glow_right_arm", CubeListBuilder.create().texOffs(100, 88).mirror().addBox(-9.0F, -6.0F, -1.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(89, 88).mirror().addBox(-9.0F, -6.0F, -1.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.26F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.1309F));
        PartDefinition lower_glow_right_arm = right_arm_glow.addOrReplaceChild("lower_glow_right_arm", CubeListBuilder.create().texOffs(89, 77).mirror().addBox(-6.5F, 4.0F, -1.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.27F)).mirror(false)
                .texOffs(100, 77).mirror().addBox(-6.5F, 4.0F, -1.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_legging = root.getChild("left_legging");
        PartDefinition left_leg = left_legging.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(53, 88).mirror().addBox(-2.5F, -0.5F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.0F));
        PartDefinition thigh_guard_l = left_leg.addOrReplaceChild("left_thigh_guard1", CubeListBuilder.create().texOffs(19, 108).mirror().addBox(1.0F, -2.0F, -3.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.0F, -1.1345F));
        PartDefinition thigh_guard_l2 = left_leg.addOrReplaceChild("left_thigh_guard2", CubeListBuilder.create().texOffs(40, 109).mirror().addBox(0.0F, -3.0F, -3.0F, 3.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 4.0F, 0.0F, 0.0F, 0.0F, -1.1345F));

        PartDefinition left_foot = root.getChild("left_foot");
        PartDefinition left_boot = left_foot.addOrReplaceChild("left_boot", CubeListBuilder.create().texOffs(63, 107).addBox(-3.0F, 6.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_arm = root.getChild("left_arm");
        PartDefinition left_shoulder = left_arm.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(36, 75).addBox(2.0F, -4.0F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.01F))
                .texOffs(7, 88).addBox(0.5F, 5.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.02F)), PartPose.offset(3.0F, 2.0F, 0.0F));
        PartDefinition left_shoulder_pad = left_shoulder.addOrReplaceChild("left_shoulder_pad", CubeListBuilder.create().texOffs(4, 73).addBox(2.0F, -1.0F, -4.0F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, 0.0F, 0.2182F));

        PartDefinition glow_left_arm = left_arm_glow.addOrReplaceChild("glow_left_arm", CubeListBuilder.create().texOffs(100, 88).addBox(6.0F, -5.0F, -1.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.01F))
                .texOffs(89, 88).addBox(6.0F, -5.0F, -1.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.26F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1309F));
        PartDefinition lower_glow_left_arm = left_arm_glow.addOrReplaceChild("lower_glow_left_arm", CubeListBuilder.create().texOffs(100, 77).addBox(3.5F, 4.0F, -1.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.02F))
                .texOffs(89, 77).addBox(3.5F, 4.0F, -1.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.27F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = root.getChild("head");
        PartDefinition helmet = head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(59, 3).addBox(-4.5F, -9.0F, -5.0F, 3.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(-4.5F, -5.0F, 1.0F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(59, 3).mirror().addBox(1.5F, -9.0F, -5.0F, 3.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 16).addBox(-1.5F, -11.0F, -6.0F, 3.0F, 7.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(40, 25).addBox(-5.5F, -12.0F, -3.0F, 2.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(40, 25).addBox(3.5F, -12.0F, -3.0F, 2.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(40, 16).addBox(-5.0F, -5.0F, -5.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 16).mirror().addBox(2.0F, -5.0F, -5.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }
}