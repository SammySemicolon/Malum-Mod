package com.sammy.malum.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class ArmorModel extends HumanoidModel<LivingEntity> {
    public EquipmentSlot slot;
    ModelPart root, head, body, leftArm, rightArm, leggings, leftLegging, rightLegging, leftFoot, rightFoot;

//	public ModelPart copyWithoutBoxes(ModelPart box) {
//		ModelPart newbox = new ModelPart(this);
//		newbox.setPos(box.x, box.y, box.z);
//		setRotationAngle(newbox, box.xRot, box.yRot, box.zRot);
//		newbox.mirror = box.mirror;
//		newbox.visible = box.visible;
//		return newbox;
//	}

    public ArmorModel(ModelPart root) {
        super(root);
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leggings = root.getChild("leggings");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
        this.leftLegging = root.getChild("left_legging");
        this.rightLegging = root.getChild("right_legging");
        this.leftFoot = root.getChild("left_foot");
        this.rightFoot = root.getChild("right_foot");
    }

    public static PartDefinition createHumanoidAlias(MeshDefinition mesh) {
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("leggings", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("head", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("left_legging", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("left_foot", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("right_legging", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("right_foot", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("left_arm", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("right_arm", new CubeListBuilder(), PartPose.ZERO);
        return root;
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return slot == EquipmentSlot.HEAD ? ImmutableList.of(head) : ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        if (slot == EquipmentSlot.CHEST) {
            return ImmutableList.of(body, leftArm, rightArm);
        } else if (slot == EquipmentSlot.LEGS) {
            return ImmutableList.of(leftLegging, rightLegging, leggings);
        } else if (slot == EquipmentSlot.FEET) {
            return ImmutableList.of(leftFoot, rightFoot);
        } else return ImmutableList.of();
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (slot == EquipmentSlot.LEGS) {  //I don't know why this is needed, but it is.
            this.leggings.copyFrom(this.body);
            this.leftLegging.copyFrom(this.leftLeg);
            this.rightLegging.copyFrom(this.rightLeg);
        }
        super.renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void copyFromDefault(HumanoidModel model) {
        leggings.copyFrom(model.body);
        body.copyFrom(model.body);
        head.copyFrom(model.head);
        leftArm.copyFrom(model.leftArm);
        rightArm.copyFrom(model.rightArm);
        leftLegging.copyFrom(leftLeg);
        rightLegging.copyFrom(rightLeg);
        leftFoot.copyFrom(leftLeg);
        rightFoot.copyFrom(rightLeg);
    }
}