package com.sammy.malum.client.model;
//Made with Blockbench, by Daniel Astral (@TrisAstral)

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlot;

public class UmbralArmorModel extends ArmorModel
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
    
    public UmbralArmorModel(EquipmentSlot slot)
    {
        super(slot, 64, 98);
        texWidth = 64;
        texHeight = 96;
        
        torso = new ModelRenderer(this);
        torso.setPos(0.0F, 6.0F, 0.0F);
        torso.texOffs(32, 22);
        torso.addBox(-5.0F, 1.0F, -3.0F, 10, 4, 6, 0.0F, false);
        torso.texOffs(23, 35);
        torso.addBox(-5.0F, 7.0F, -3.0F, 10, 3, 6, 0.0F, false);
        torso.texOffs(0, 39);
        torso.addBox(-4.5F, 4.5F, -2.5F, 9, 7, 5, 0.01F, false);
        torso.texOffs(46, 14);
        torso.addBox(-5.0F, -1.0F, -3.0F, 3, 2, 6, 0.0F, true);
        torso.addBox(2.0F, -1.0F, -3.0F, 3, 2, 6, 0.0F, false);
        torso.texOffs(54, 6);
        torso.addBox(2.0F, -1.0F, 3.0F, 3, 6, 2, 0.0F, false);
        torso.addBox(-5.0F, -1.0F, 3.0F, 3, 6, 2, 0.0F, true);
        
        chestplate_slope2 = new ModelRenderer(this);
        chestplate_slope2.setPos(0.0F, -1.0F, -3.0F);
        setRotationAngle(chestplate_slope2, 0.2618F, 0.0F, 0.0F);
        torso.addChild(chestplate_slope2);
        chestplate_slope2.texOffs(18, 31);
        chestplate_slope2.addBox(-3.0F, 5.75F, -1.5F, 6, 2, 1, 0.0F, false);
        
        head = new ModelRenderer(this);
        head.setPos(0.0F, 0.0F, 0.0F);
        head.addBox(-4.5F, -9.0F, -5.0F, 9, 4, 10, 0.0F, false);
        head.texOffs(0, 14);
        head.addBox(-4.5F, -5.0F, -1.5F, 9, 5, 6, 0.0F, false);
        head.texOffs(29, 5);
        head.addBox(-3.5F, -10.0F, -6.0F, 2, 6, 9, 0.0F, true);
        head.addBox(1.5F, -10.0F, -6.0F, 2, 6, 9, 0.0F, false);
        head.texOffs(42, 4);
        head.addBox(-5.0F, -5.0F, -5.0F, 2, 6, 4, 0.0F, true);
        head.texOffs(0, 25);
        head.addBox(-5.5F, -10.0F, -1.0F, 2, 7, 7, 0.0F, true);
        head.addBox(3.5F, -10.0F, -1.0F, 2, 7, 7, 0.0F, false);
        head.texOffs(0, 0);
        head.addBox(3.5F, -10.0F, 6.0F, 2, 4, 2, 0.0F, false);
        head.addBox(-5.5F, -10.0F, 6.0F, 2, 4, 2, 0.0F, true);
        head.texOffs(42, 4);
        head.addBox(3.0F, -5.0F, -5.0F, 2, 6, 4, 0.0F, false);
        head.texOffs(11, 25);
        head.addBox(-3.0F, -2.0F, -5.0F, 6, 4, 2, 0.0F, false);
        
        arm_r = new ModelRenderer(this);
        arm_r.setPos(-4.0F, 2.0F, 0.0F);
        arm_r.texOffs(0, 53);
        arm_r.addBox(-4.0F, -4.0F, -3.0F, 4, 5, 6, 0.0F, true);
        arm_r.texOffs(20, 53);
        arm_r.addBox(-3.5F, 6.0F, -3.0F, 5, 5, 6, 0.0F, true);
        arm_r.texOffs(28, 44);
        arm_r.addBox(-3.5F, 2.0F, -2.5F, 3, 4, 5, 0.0F, true);
        arm_r.texOffs(38, 47);
        arm_r.addBox(-5.0F, -5.0F, -2.0F, 3, 5, 6, 0.0F, true);
        arm_r.texOffs(52, 41);
        arm_r.addBox(-4.5F, 5.0F, -1.5F, 2, 7, 3, 0.0F, true);
        
        arm_l = new ModelRenderer(this);
        arm_l.setPos(4.0F, 2.0F, 0.0F);
        arm_l.texOffs(0, 53);
        arm_l.addBox(0.0F, -4.0F, -3.0F, 4, 5, 6, 0.0F, false);
        arm_l.texOffs(38, 47);
        arm_l.addBox(2.0F, -5.0F, -2.0F, 3, 5, 6, 0.0F, false);
        arm_l.texOffs(20, 53);
        arm_l.addBox(-1.5F, 6.0F, -3.0F, 5, 5, 6, 0.0F, false);
        arm_l.texOffs(28, 44);
        arm_l.addBox(0.5F, 2.0F, -2.5F, 3, 4, 5, 0.0F, false);
        arm_l.texOffs(52, 41);
        arm_l.addBox(2.5F, 5.0F, -1.5F, 2, 7, 3, 0.0F, false);
        
        leg_r = new ModelRenderer(this);
        leg_r.setPos(-2.0F, 12.0F, 0.0F);
        leg_r.texOffs(34, 65);
        leg_r.addBox(-2.5F, -0.5F, -2.5F, 5, 7, 5, 0.0F, true);
        
        thigh_guard_r2 = new ModelRenderer(this);
        thigh_guard_r2.setPos(0.0F, 0.0F, 0.0F);
        setRotationAngle(thigh_guard_r2, 0.0F, 0.0F, 0.1745F);
        leg_r.addChild(thigh_guard_r2);
        thigh_guard_r2.texOffs(18, 73);
        thigh_guard_r2.addBox(-3.0F, -1.0F, -3.0F, 3, 7, 6, 0.0F, true);
        thigh_guard_r2.texOffs(0, 78);
        thigh_guard_r2.addBox(-4.0F, 4.0F, -3.0F, 1, 2, 6, 0.0F, true);
        
        boot_r = new ModelRenderer(this);
        boot_r.setPos(0.0F, 4.0F, 0.0F);
        leg_r.addChild(boot_r);
        boot_r.texOffs(0, 65);
        boot_r.addBox(-3.0F, 6.0F, -3.0F, 6, 7, 6, 0.0F, true);
        
        boot_wing_r2 = new ModelRenderer(this);
        boot_wing_r2.setPos(-5.0F, 5.0F, 0.0F);
        setRotationAngle(boot_wing_r2, 0.7854F, 0.0F, 0.0F);
        boot_r.addChild(boot_wing_r2);
        boot_wing_r2.texOffs(24, 65);
        boot_wing_r2.addBox(1.0F, 1.0F, -5.0F, 1, 4, 4, 0.0F, true);
        
        leg_l = new ModelRenderer(this);
        leg_l.setPos(2.0F, 12.0F, 0.0F);
        leg_l.texOffs(34, 65);
        leg_l.addBox(-2.5F, -0.5F, -2.5F, 5, 7, 5, 0.0F, false);
        
        thigh_guard_l2 = new ModelRenderer(this);
        thigh_guard_l2.setPos(0.0F, 0.0F, 0.0F);
        setRotationAngle(thigh_guard_l2, 0.0F, 0.0F, -0.1745F);
        leg_l.addChild(thigh_guard_l2);
        thigh_guard_l2.texOffs(18, 73);
        thigh_guard_l2.addBox(0.0F, -1.0F, -3.0F, 3, 7, 6, 0.0F, false);
        thigh_guard_l2.texOffs(0, 78);
        thigh_guard_l2.addBox(3.0F, 4.0F, -3.0F, 1, 2, 6, 0.0F, false);
        
        boot_l = new ModelRenderer(this);
        boot_l.setPos(0.0F, 4.0F, 0.0F);
        leg_l.addChild(boot_l);
        boot_l.texOffs(0, 65);
        boot_l.addBox(-3.0F, 6.0F, -3.0F, 6, 7, 6, 0.0F, false);
        
        boot_wing_l2 = new ModelRenderer(this);
        boot_wing_l2.setPos(5.0F, 5.0F, 0.0F);
        setRotationAngle(boot_wing_l2, 0.7854F, 0.0F, 0.0F);
        boot_l.addChild(boot_wing_l2);
        boot_wing_l2.texOffs(24, 65);
        boot_wing_l2.addBox(-2.0F, 1.0F, -5.0F, 1, 4, 4, 0.0F, false);
    }
    
    @Override
    public void renderToBuffer(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a)
    {
        
        head.visible = slot == EquipmentSlot.HEAD;
        
        leg_r.visible = slot == EquipmentSlot.LEGS;
        leg_l.visible = slot == EquipmentSlot.LEGS;
        
        thigh_guard_r2.visible = slot == EquipmentSlot.LEGS;
        thigh_guard_l2.visible = slot == EquipmentSlot.LEGS;
        
        torso.visible = slot == EquipmentSlot.CHEST;
        chestplate_slope2.visible = slot == EquipmentSlot.CHEST;
        arm_r.visible = slot == EquipmentSlot.CHEST;
        arm_l.visible = slot == EquipmentSlot.CHEST;
        
        boot_r.visible = slot == EquipmentSlot.FEET;
        boot_l.visible = slot == EquipmentSlot.FEET;
        
        boot_wing_r2.visible = slot == EquipmentSlot.FEET;
        boot_wing_l2.visible = slot == EquipmentSlot.FEET;
        
        hat.visible = false;
        
        head = head;
        
        body = torso;
        rightArm = arm_r;
        leftArm = arm_l;
        
        if (slot == EquipmentSlot.LEGS)
        {
            rightLeg = leg_r;
            leftLeg = leg_l;
        }
        else
        {
            rightLeg = boot_r;
            leftLeg = boot_l;
        }
        super.renderToBuffer(ms, buffer, light, overlay, r, g, b, a);
    }
}