package com.sammy.malum.items.armor;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.init.ModItems;
import com.sammy.malum.models.ModelUmbralSteelArmor;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class ItemUmbraSteelBattleArmor extends ModArmor
{
    public ItemUmbraSteelBattleArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder)
    {
        super(materialIn, slot, builder);
    }

    @OnlyIn(value = Dist.CLIENT)
    @Override
    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
    {
        return "malum:textures/armor/soul_steel_armour.png";
    }
    
    public static boolean hasArmorSet(PlayerEntity playerEntity)
    {
        return MalumHelper.hasArmorSet(playerEntity, ModItems.umbral_steel_helm, ModItems.umbral_steel_chestplate, ModItems.umbral_steel_leggings, ModItems.umbral_steel_shoes);
    }
    
    @OnlyIn(value = Dist.CLIENT)
    @Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default)
    {
        return (A) new ModelUmbralSteelArmor(slot);
    }
    
}