package com.sammy.malum.common.item.equipment.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.LazyValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class MalumArmorItem extends ArmorItem
{
    protected LazyValue<Object> model;
    public MalumArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder)
    {
        super(materialIn, slot, builder);
    }
    public boolean hasArmorSet(LivingEntity livingEntity)
    {
        return hasArmorPiece(livingEntity, EquipmentSlotType.HEAD) && hasArmorPiece(livingEntity, EquipmentSlotType.CHEST) && hasArmorPiece(livingEntity, EquipmentSlotType.LEGS) && hasArmorPiece(livingEntity, EquipmentSlotType.FEET);
    }
    public boolean hasArmorPiece(LivingEntity livingEntity, EquipmentSlotType slot)
    {
        return this.getClass().isInstance(livingEntity.getItemStackFromSlot(slot).getItem());
    }

    public String texture()
    {
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
    {
        return "malum:textures/armor/" + texture() + ".png";
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A original)
    {
        return (A) model.getValue();
    }
}