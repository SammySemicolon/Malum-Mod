package com.kittykitcatcat.malum.models;
//Made with Blockbench, by Daniel Astral (@TrisAstral)
//Paste this code into your mod.

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class ModelArmorTier2 extends ModelArmor
{
    private final ModelRenderer torso;
    private final ModelRenderer chestplate_slope2;
    private final ModelRenderer head;
    private final ModelRenderer arm_r;
    private final ModelRenderer arm_l;
    private final ModelRenderer leg_r;
    private final ModelRenderer thigh_guard_r2;
    private final ModelRenderer boot_r;
    private final ModelRenderer boot_wing_r2;
    private final ModelRenderer leg_l;
    private final ModelRenderer thigh_guard_l2;
    private final ModelRenderer boot_l;
    private final ModelRenderer boot_wing_l2;

    public ModelArmorTier2(EquipmentSlotType slot)
    {
        super(slot, 64, 98);
        textureWidth = 64;
        textureHeight = 96;

        torso = new ModelRenderer(this);
        torso.setRotationPoint(0.0F, 6.0F, 0.0F);
        torso.setTextureOffset(32,22);
        torso.addBox(-5.0F, 1.0F, -3.0F, 10, 4, 6, 0.0F, false);
        torso.setTextureOffset(23,35);
        torso.addBox( -5.0F, 7.0F, -3.0F, 10, 3, 6, 0.0F, false);
        torso.setTextureOffset(0,39);
        torso.addBox(-4.5F, 4.5F, -2.5F, 9, 7, 5, 0.01F, false);
        torso.setTextureOffset(46,14);
        torso.addBox(-5.0F, -1.0F, -3.0F, 3, 2, 6, 0.0F, true);
        torso.addBox(2.0F, -1.0F, -3.0F, 3, 2, 6, 0.0F, false);
        torso.setTextureOffset(54,6);
        torso.addBox(2.0F, -1.0F, 3.0F, 3, 6, 2, 0.0F, false);
        torso.addBox(-5.0F, -1.0F, 3.0F, 3, 6, 2, 0.0F, true);

        chestplate_slope2 = new ModelRenderer(this);
        chestplate_slope2.setRotationPoint(0.0F, -1.0F, -3.0F);
        setRotationAngle(chestplate_slope2, 0.2618F, 0.0F, 0.0F);
        torso.addChild(chestplate_slope2);
        chestplate_slope2.setTextureOffset(18,31);
        chestplate_slope2.addBox(-3.0F, 5.75F, -1.5F, 6, 2, 1, 0.0F, false);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addBox(-4.5F, -9.0F, -5.0F, 9, 4, 10, 0.0F, false);
        head.setTextureOffset(0,14);
        head.addBox(-4.5F, -5.0F, -1.5F, 9, 5, 6, 0.0F, false);
        head.setTextureOffset(29,5);
        head.addBox(-3.5F, -10.0F, -6.0F, 2, 6, 9, 0.0F, true);
        head.addBox(1.5F, -10.0F, -6.0F, 2, 6, 9, 0.0F, false);
        head.setTextureOffset(42,4);
        head.addBox(-5.0F, -5.0F, -5.0F, 2, 6, 4, 0.0F, true);
        head.setTextureOffset(0,25);
        head.addBox(-5.5F, -10.0F, -1.0F, 2, 7, 7, 0.0F, true);
        head.addBox(3.5F, -10.0F, -1.0F, 2, 7, 7, 0.0F, false);
        head.setTextureOffset(0,0);
        head.addBox(3.5F, -10.0F, 6.0F, 2, 4, 2, 0.0F, false);
        head.addBox(-5.5F, -10.0F, 6.0F, 2, 4, 2, 0.0F, true);
        head.setTextureOffset(42,4);
        head.addBox(3.0F, -5.0F, -5.0F, 2, 6, 4, 0.0F, false);
        head.setTextureOffset(11,25);
        head.addBox(-3.0F, -2.0F, -5.0F, 6, 4, 2, 0.0F, false);

        arm_r = new ModelRenderer(this);
        arm_r.setRotationPoint(-4.0F, 2.0F, 0.0F);
        arm_r.setTextureOffset(0,53);
        arm_r.addBox(-4.0F, -4.0F, -3.0F, 4, 5, 6, 0.0F, true);
        arm_r.setTextureOffset(20,53);
        arm_r.addBox(-3.5F, 6.0F, -3.0F, 5, 5, 6, 0.0F, true);
        arm_r.setTextureOffset(28,44);
        arm_r.addBox(-3.5F, 2.0F, -2.5F, 3, 4, 5, 0.0F, true);
        arm_r.setTextureOffset(38,47);
        arm_r.addBox(-5.0F, -5.0F, -2.0F, 3, 5, 6, 0.0F, true);
        arm_r.setTextureOffset(52,41);
        arm_r.addBox(-4.5F, 5.0F, -1.5F, 2, 7, 3, 0.0F, true);

        arm_l = new ModelRenderer(this);
        arm_l.setRotationPoint(4.0F, 2.0F, 0.0F);
        arm_l.setTextureOffset(0,53);
        arm_l.addBox(0.0F, -4.0F, -3.0F, 4, 5, 6, 0.0F, false);
        arm_l.setTextureOffset(38,47);
        arm_l.addBox(2.0F, -5.0F, -2.0F, 3, 5, 6, 0.0F, false);
        arm_l.setTextureOffset(20,53);
        arm_l.addBox(-1.5F, 6.0F, -3.0F, 5, 5, 6, 0.0F, false);
        arm_l.setTextureOffset(28,44);
        arm_l.addBox(0.5F, 2.0F, -2.5F, 3, 4, 5, 0.0F, false);
        arm_l.setTextureOffset(52,41);
        arm_l.addBox(2.5F, 5.0F, -1.5F, 2, 7, 3, 0.0F, false);

        leg_r = new ModelRenderer(this);
        leg_r.setRotationPoint(-2.0F, 12.0F, 0.0F);
        leg_r.setTextureOffset(34,65);
        leg_r.addBox(-2.5F, -0.5F, -2.5F, 5, 7, 5, 0.0F, true);

        thigh_guard_r2 = new ModelRenderer(this);
        thigh_guard_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(thigh_guard_r2, 0.0F, 0.0F, 0.1745F);
        leg_r.addChild(thigh_guard_r2);
        thigh_guard_r2.setTextureOffset(18,73);
        thigh_guard_r2.addBox(-3.0F, -1.0F, -3.0F, 3, 7, 6, 0.0F, true);
        thigh_guard_r2.setTextureOffset(0,78);
        thigh_guard_r2.addBox(-4.0F, 4.0F, -3.0F, 1, 2, 6, 0.0F, true);

        boot_r = new ModelRenderer(this);
        boot_r.setRotationPoint(0.0F, 4.0F, 0.0F);
        leg_r.addChild(boot_r);
        boot_r.setTextureOffset(0,65);
        boot_r.addBox(-3.0F, 6.0F, -3.0F, 6, 7, 6, 0.0F, true);

        boot_wing_r2 = new ModelRenderer(this);
        boot_wing_r2.setRotationPoint(-5.0F, 5.0F, 0.0F);
        setRotationAngle(boot_wing_r2, 0.7854F, 0.0F, 0.0F);
        boot_r.addChild(boot_wing_r2);
        boot_wing_r2.setTextureOffset(24,65);
        boot_wing_r2.addBox(1.0F, 1.0F, -5.0F, 1, 4, 4, 0.0F, true);

        leg_l = new ModelRenderer(this);
        leg_l.setRotationPoint(2.0F, 12.0F, 0.0F);
        leg_l.setTextureOffset(34,65);
        leg_l.addBox(-2.5F, -0.5F, -2.5F, 5, 7, 5, 0.0F, false);

        thigh_guard_l2 = new ModelRenderer(this);
        thigh_guard_l2.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(thigh_guard_l2, 0.0F, 0.0F, -0.1745F);
        leg_l.addChild(thigh_guard_l2);
        thigh_guard_l2.setTextureOffset(18,73);
        thigh_guard_l2.addBox(0.0F, -1.0F, -3.0F, 3, 7, 6, 0.0F, false);
        thigh_guard_l2.setTextureOffset(0,78);
        thigh_guard_l2.addBox(3.0F, 4.0F, -3.0F, 1, 2, 6, 0.0F, false);

        boot_l = new ModelRenderer(this);
        boot_l.setRotationPoint(0.0F, 4.0F, 0.0F);
        leg_l.addChild(boot_l);
        boot_l.setTextureOffset(0,65);
        boot_l.addBox(-3.0F, 6.0F, -3.0F, 6, 7, 6, 0.0F, false);

        boot_wing_l2 = new ModelRenderer(this);
        boot_wing_l2.setRotationPoint(5.0F, 5.0F, 0.0F);
        setRotationAngle(boot_wing_l2, 0.7854F, 0.0F, 0.0F);
        boot_l.addChild(boot_wing_l2);
        boot_wing_l2.setTextureOffset(24,65);
        boot_wing_l2.addBox(-2.0F, 1.0F, -5.0F, 1, 4, 4, 0.0F, false);
    }
    @Override
    public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a) {

        head.showModel = slot == EquipmentSlotType.HEAD;

        leg_r.showModel = slot == EquipmentSlotType.LEGS;
        leg_l.showModel = slot == EquipmentSlotType.LEGS;

        thigh_guard_r2.showModel = slot == EquipmentSlotType.LEGS;
        thigh_guard_l2.showModel = slot == EquipmentSlotType.LEGS;

        torso.showModel = slot == EquipmentSlotType.CHEST;
        chestplate_slope2.showModel = slot == EquipmentSlotType.CHEST;
        arm_r.showModel = slot == EquipmentSlotType.CHEST;
        arm_l.showModel = slot == EquipmentSlotType.CHEST;

        boot_r.showModel = slot == EquipmentSlotType.FEET;
        boot_l.showModel = slot == EquipmentSlotType.FEET;

        boot_wing_r2.showModel = slot == EquipmentSlotType.FEET;
        boot_wing_l2.showModel = slot == EquipmentSlotType.FEET;

        bipedHeadwear.showModel = false;

        bipedHead = head;

        bipedBody = torso;
        bipedRightArm = arm_r;
        bipedLeftArm = arm_l;

        if (slot == EquipmentSlotType.LEGS)
        {
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
    public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.head.copyModelAngles(this.bipedHead);
        this.torso.copyModelAngles(this.bipedBody);
        this.arm_l.copyModelAngles(this.bipedLeftArm);
        this.arm_r.copyModelAngles(this.bipedRightArm);
        this.leg_l.copyModelAngles(this.bipedLeftLeg);
        this.leg_r.copyModelAngles(this.bipedRightLeg);
        this.boot_l.copyModelAngles(this.bipedLeftLeg);
        this.boot_r.copyModelAngles(this.bipedRightLeg);

    }
    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}