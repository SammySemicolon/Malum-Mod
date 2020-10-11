package com.sammy.malum.models;
//Made with Blockbench, by Daniel Astral (@TrisAstral)

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
        torso.setRotationPoint(0.0F, -7.0F, 0.0F);
        torso.setTextureOffset(0, 25).addBox(-5.0F, 0.0F, -3.0F, 10.0F, 5.0F, 6.0F, 0.0F, false);
        torso.setTextureOffset(36, 52).addBox(-4.5F, 4.5F, -2.5F, 9.0F, 6.0F, 5.0F, -0.1F, false);
        torso.setTextureOffset(26, 23).addBox(-5.0F, -2.0F, -3.0F, 3.0F, 2.0F, 6.0F, 0.0F, true);
        torso.setTextureOffset(26, 23).addBox(2.0F, -2.0F, -3.0F, 3.0F, 2.0F, 6.0F, 0.0F, false);
        torso.setTextureOffset(30, 16).addBox(2.0F, -2.0F, 3.0F, 3.0F, 5.0F, 2.0F, 0.0F, true);
        torso.setTextureOffset(30, 16).addBox(-5.0F, -2.0F, 3.0F, 3.0F, 5.0F, 2.0F, 0.0F, false);
    
        chestplate_slope = new ModelRenderer(this);
        chestplate_slope.setRotationPoint(-8.0F, -19.0F, 5.0F);
        torso.addChild(chestplate_slope);
        setRotationAngle(chestplate_slope, 0.2618F, 0.0F, 0.0F);
        chestplate_slope.setTextureOffset(19, 22).addBox(6.0F, 20.8972F, -13.8177F, 4.0F, 2.0F, 1.0F, 0.0F, false);
    
        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.setTextureOffset(0, 0).addBox(-4.5F, -9.0F, -5.0F, 9.0F, 4.0F, 6.0F, 0.0F, false);
        head.setTextureOffset(21, 1).addBox(-1.5F, -10.0F, -6.0F, 3.0F, 6.0F, 9.0F, 0.0F, false);
        head.setTextureOffset(36, 0).addBox(-5.0F, -5.0F, -5.0F, 2.0F, 6.0F, 4.0F, 0.0F, true);
        head.setTextureOffset(0, 10).addBox(-5.0F, -10.0F, -1.0F, 2.0F, 7.0F, 7.0F, 0.0F, true);
        head.setTextureOffset(0, 10).addBox(3.0F, -10.0F, -1.0F, 2.0F, 7.0F, 7.0F, 0.0F, false);
        head.setTextureOffset(11, 10).addBox(3.0F, -10.0F, 6.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
        head.setTextureOffset(11, 10).addBox(-5.0F, -10.0F, 6.0F, 2.0F, 4.0F, 2.0F, 0.0F, true);
        head.setTextureOffset(36, 0).addBox(3.0F, -5.0F, -5.0F, 2.0F, 6.0F, 4.0F, 0.0F, false);
    
        arm_r = new ModelRenderer(this);
        arm_r.setRotationPoint(-7.0F, 2.0F, 0.0F);
        arm_r.setTextureOffset(44, 14).addBox(-4.0F, -4.0F, -3.0F, 4.0F, 6.0F, 6.0F, 0.0F, true);
        arm_r.setTextureOffset(42, 26).addBox(-3.5F, 5.0F, -3.0F, 5.0F, 6.0F, 6.0F, 0.0F, true);
        arm_r.setTextureOffset(48, 7).addBox(-3.5F, 3.0F, -2.5F, 3.0F, 2.0F, 5.0F, 0.0F, true);
    
        arm_l = new ModelRenderer(this);
        arm_l.setRotationPoint(7.0F, 2.0F, 0.0F);
        arm_l.setTextureOffset(44, 14).addBox(0.0F, -4.0F, -3.0F, 4.0F, 6.0F, 6.0F, 0.0F, false);
        arm_l.setTextureOffset(42, 26).addBox(-1.5F, 5.0F, -3.0F, 5.0F, 6.0F, 6.0F, 0.0F, false);
        arm_l.setTextureOffset(48, 7).addBox(0.5F, 3.0F, -2.5F, 3.0F, 2.0F, 5.0F, 0.0F, false);
    
        leg_r = new ModelRenderer(this);
        leg_r.setRotationPoint(-2.0F, 12.0F, 0.0F);
        leg_r.setTextureOffset(16, 36).addBox(-2.5F, -1.5F, -2.5F, 5.0F, 7.0F, 5.0F, 0.01F, true);
    
        boot_r = new ModelRenderer(this);
        boot_r.setRotationPoint(0.0F, 0.0F, 0.0F);
        leg_r.addChild(boot_r);
        boot_r.setTextureOffset(40, 39).addBox(-3.0F, 5.0F, -3.0F, 6.0F, 7.0F, 6.0F, 0.0F, true);
    
        boot_wing_r = new ModelRenderer(this);
        boot_wing_r.setRotationPoint(2.0F, 12.0F, 0.0F);
        boot_r.addChild(boot_wing_r);
        setRotationAngle(boot_wing_r, 0.7854F, 0.0F, 0.0F);
        boot_wing_r.setTextureOffset(0, 36).addBox(-6.0F, -5.5071F, 1.2071F, 1.0F, 4.0F, 7.0F, 0.0F, true);
    
        thigh_guard_r = new ModelRenderer(this);
        thigh_guard_r.setRotationPoint(-8.0F, 0.0F, 8.0F);
        leg_r.addChild(thigh_guard_r);
        setRotationAngle(thigh_guard_r, 0.0F, 0.0F, 0.1745F);
        thigh_guard_r.setTextureOffset(0, 47).addBox(4.7048F, -3.374F, -11.0F, 3.0F, 5.0F, 6.0F, 0.01F, true);
    
        leg_l = new ModelRenderer(this);
        leg_l.setRotationPoint(2.0F, 12.0F, 0.0F);
        leg_l.setTextureOffset(16, 36).addBox(-2.5F, -1.5F, -2.5F, 5.0F, 7.0F, 5.0F, 0.01F, false);
    
        thigh_guard_l = new ModelRenderer(this);
        thigh_guard_l.setRotationPoint(-8.0F, 0.0F, 8.0F);
        leg_l.addChild(thigh_guard_l);
        setRotationAngle(thigh_guard_l, 0.0F, 0.0F, -0.1745F);
        thigh_guard_l.setTextureOffset(0, 47).addBox(8.0521F, -0.5956F, -11.0F, 3.0F, 5.0F, 6.0F, 0.01F, false);
    
        boot_l = new ModelRenderer(this);
        boot_l.setRotationPoint(0.0F, 0.0F, 0.0F);
        leg_l.addChild(boot_l);
        boot_l.setTextureOffset(40, 39).addBox(-3.0F, 5.0F, -3.0F, 6.0F, 7.0F, 6.0F, 0.01F, false);
    
        boot_wing_l = new ModelRenderer(this);
        boot_wing_l.setRotationPoint(-2.0F, 12.0F, 0.0F);
        boot_l.addChild(boot_wing_l);
        setRotationAngle(boot_wing_l, 0.7854F, 0.0F, 0.0F);
        boot_wing_l.setTextureOffset(0, 36).addBox(5.0F, -5.5071F, 1.2071F, 1.0F, 4.0F, 7.0F, 0.0F, false);
    }

    public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        head.copyModelAngles(bipedHead);
        torso.copyModelAngles(bipedBody);
        arm_l.copyModelAngles(bipedLeftArm);
        arm_r.copyModelAngles(bipedRightArm);
        leg_l.copyModelAngles(bipedLeftLeg);
        leg_r.copyModelAngles(bipedRightLeg);
        boot_l.copyModelAngles(bipedLeftLeg);
        boot_r.copyModelAngles(bipedRightLeg);
    }

    @Override
    public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a)
    {

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