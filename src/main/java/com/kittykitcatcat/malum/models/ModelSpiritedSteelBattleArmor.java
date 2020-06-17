package com.kittykitcatcat.malum.models;
//Made with Blockbench, by Daniel Astral (@TrisAstral)
//Paste this code into your mod.

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class ModelSpiritedSteelBattleArmor extends ModelArmor
{
    private final ModelRenderer torso;
    private final ModelRenderer chestplate_slope;
    private final ModelRenderer head;
    private final ModelRenderer arm_r;
    private final ModelRenderer arm_l;
    private final ModelRenderer leg_r;
    private final ModelRenderer boot_r;
    private final ModelRenderer boot_wing_r;
    private final ModelRenderer thigh_guard_r;
    private final ModelRenderer leg_l;
    private final ModelRenderer thigh_guard_l;
    private final ModelRenderer boot_l;
    private final ModelRenderer boot_wing_l;

    public ModelSpiritedSteelBattleArmor(EquipmentSlotType slot)
    {
        super(slot, 64, 64);
        textureWidth = 64;
        textureHeight = 64;

        torso = new ModelRenderer(this);
        torso.setRotationPoint(8.0F, -10.0F, -8.0F);
        torso.setTextureOffset(0,25);
        torso.addBox(-5.0F, 1.0F, -3.0F, 10, 5, 6, 0.0F, false);
        torso.setTextureOffset(0,36);
        torso.addBox(-4.5F, 5.5F, -2.5F, 9, 6, 5, 0.0F, false);
        torso.setTextureOffset(26,23);
        torso.addBox( -5.0F, -1.0F, -3.0F, 3, 2, 6, 0.0F, true);
        torso.addBox(2.0F, -1.0F, -3.0F, 3, 2, 6, 0.0F, false);
        torso.setTextureOffset(30,16);
        torso.addBox(2.0F, -1.0F, 3.0F, 3, 5, 2, 0.0F, false);
        torso.addBox(-5.0F, -1.0F, 3.0F, 3, 5, 2, 0.0F, true);

        chestplate_slope = new ModelRenderer(this);
        chestplate_slope.setRotationPoint(-8.0F, 0.0F, 5.0F);
        setRotationAngle(chestplate_slope, 0.2618F, 0.0F, 0.0F);
        torso.addChild(chestplate_slope);
        chestplate_slope.setTextureOffset(19,22);
        chestplate_slope.addBox(6.0F, 3.7429F, -9.2274F, 4, 2, 1, 0.0F, false);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addBox(-4.5F, -9.0F, -5.0F, 9, 4, 6, 0.0F, false);
        head.setTextureOffset(21,1);
        head.addBox(-1.5F, -10.0F, -6.0F, 3, 6, 9, 0.0F, false);
        head.setTextureOffset(36,0);
        head.addBox( -5.0F, -5.0F, -5.0F, 2, 6, 4, 0.0F, true);
        head.setTextureOffset(0,10);
        head.addBox(-5.0F, -10.0F, -1.0F, 2, 7, 7, 0.0F, true);
        head.addBox( 3.0F, -10.0F, -1.0F, 2, 7, 7, 0.0F, false);
        head.setTextureOffset(11,10);
        head.addBox(3.0F, -10.0F, 6.0F, 2, 4, 2, 0.0F, false);
        head.addBox( -5.0F, -10.0F, 6.0F, 2, 4, 2, 0.0F, true);
        head.setTextureOffset(36,0);
        head.addBox( 3.0F, -5.0F, -5.0F, 2, 6, 4, 0.0F, false);

        arm_r = new ModelRenderer(this);
        arm_r.setRotationPoint(-4.0F, 2.0F, 0.0F);
        arm_r.setTextureOffset(44,14);
        arm_r.addBox(-4.0F, -4.0F, -3.0F, 4, 6, 6, 0.0F, true);
        arm_r.setTextureOffset(42,26);
        arm_r.addBox(-4.5F, 5.0F, -3.0F, 5, 6, 6, 0.0F, true);
        arm_r.setTextureOffset(48,7);
        arm_r.addBox( -4.5F, 3.0F, -2.5F, 3, 2, 5, 0.0F, true);

        arm_l = new ModelRenderer(this);
        arm_l.setRotationPoint(4.0F, 2.0F, 0.0F);
        arm_l.setTextureOffset(44,14);
        arm_l.addBox( 0.0F, -4.0F, -3.0F, 4, 6, 6, 0.0F, false);
        arm_l.setTextureOffset(42,26);
        arm_l.addBox(-0.5F, 5.0F, -3.0F, 5, 6, 6, 0.0F, false);
        arm_l.setTextureOffset(48,7);
        arm_l.addBox( 1.5F, 3.0F, -2.5F, 3, 2, 5, 0.0F, false);

        leg_r = new ModelRenderer(this);
        leg_r.setRotationPoint(-2.0F, 12.0F, 0.0F);
        leg_r.setTextureOffset(12,47);
        leg_r.addBox(-2.5F, -0.5F, -2.5F, 5, 7, 5, 0.0F, true);

        boot_r = new ModelRenderer(this);
        boot_r.setRotationPoint(-4.0F, 0.0F, 8.0F);
        leg_r.addChild(boot_r);
        boot_r.setTextureOffset(29,39);
        boot_r.addBox(-3.0F, 6.0F, -3.0F, 6, 7, 6, 0.0F, true);

        boot_wing_r = new ModelRenderer(this);
        boot_wing_r.setRotationPoint(-7.0F, 9.0F, 0.0F);
        setRotationAngle(boot_wing_r, 0.7854F, 0.0F, 0.0F);
        boot_r.addChild(boot_wing_r);
        boot_wing_r.setTextureOffset(25,53);
        boot_wing_r.addBox( 3.0F, -1.6569F, -1.6569F, 1, 4, 7, 0.0F, true);

        thigh_guard_r = new ModelRenderer(this);
        thigh_guard_r.setRotationPoint(-8.0F, 0.0F, 8.0F);
        setRotationAngle(thigh_guard_r, 0.0F, 0.0F, 0.1745F);
        leg_r.addChild(thigh_guard_r);
        thigh_guard_r.setTextureOffset(0,53);
        thigh_guard_r.addBox(4.8785F, -2.3892F, -11.0F, 3, 5, 6, 0.0F, true);

        leg_l = new ModelRenderer(this);
        leg_l.setRotationPoint(2.0F, 12.0F, 0.0F);
        leg_l.setTextureOffset(12,47);
        leg_l.addBox(-2.5F, -0.5F, -2.5F, 5, 7, 5, 0.0F, false);

        thigh_guard_l = new ModelRenderer(this);
        thigh_guard_l.setRotationPoint(-8.0F, 0.0F, 8.0F);
        setRotationAngle(thigh_guard_l, 0.0F, 0.0F, -0.1745F);
        leg_l.addChild(thigh_guard_l);
        thigh_guard_l.setTextureOffset(0,53);
        thigh_guard_l.addBox(7.8785F, 0.3892F, -11.0F, 3, 5, 6, 0.0F, false);

        boot_l = new ModelRenderer(this);
        boot_l.setRotationPoint(-8.0F, 0.0F, 8.0F);
        leg_l.addChild(boot_l);
        boot_l.setTextureOffset(29,39);
        boot_l.addBox(-3f, 6.0F, -3.0F, 6, 7, 6, 0.0F, false);

        boot_wing_l = new ModelRenderer(this);
        boot_wing_l.setRotationPoint(3.0F, 9.0F, 0.0F);
        setRotationAngle(boot_wing_l, 0.7854F, 0.0F, 0.0F);
        boot_l.addChild(boot_wing_l);
        boot_wing_l.setTextureOffset(25,53);
        boot_wing_l.addBox(0, -1.6569F, -1.6569F, 1, 4, 7, 0.0F, false);
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
    @Override
    public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a) {

        head.showModel = slot == EquipmentSlotType.HEAD;

        leg_r.showModel = slot == EquipmentSlotType.LEGS;
        leg_l.showModel = slot == EquipmentSlotType.LEGS;
        thigh_guard_r.showModel = slot == EquipmentSlotType.LEGS;
        thigh_guard_l.showModel = slot == EquipmentSlotType.LEGS;

        torso.showModel = slot == EquipmentSlotType.CHEST;
        chestplate_slope.showModel = slot == EquipmentSlotType.CHEST;
        arm_r.showModel = slot == EquipmentSlotType.CHEST;
        arm_l.showModel = slot == EquipmentSlotType.CHEST;

        boot_r.showModel = slot == EquipmentSlotType.FEET;
        boot_l.showModel = slot == EquipmentSlotType.FEET;

        boot_wing_r.showModel = slot == EquipmentSlotType.FEET;
        boot_wing_l.showModel = slot == EquipmentSlotType.FEET;

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

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}