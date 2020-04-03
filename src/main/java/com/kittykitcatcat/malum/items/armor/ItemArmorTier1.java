package com.kittykitcatcat.malum.items.armor;

import com.kittykitcatcat.malum.models.ModelArmorTier1;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class ItemArmorTier1 extends ArmorItem
{
    protected String name;

    public ItemArmorTier1(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder)
    {
        super(materialIn, slot, builder);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
    {
        return "malum:textures/armor/royal_steel_armour.png";
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default)
    {
        return (A) new ModelArmorTier1(slot);
    }
}