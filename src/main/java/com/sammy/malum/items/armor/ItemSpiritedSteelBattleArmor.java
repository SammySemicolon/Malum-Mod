package com.sammy.malum.items.armor;


import com.sammy.malum.MalumHelper;
import com.sammy.malum.init.ModItems;
import com.sammy.malum.models.ModelSpiritedSteelBattleArmor;
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

public class ItemSpiritedSteelBattleArmor extends ModArmor
{

    public ItemSpiritedSteelBattleArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder)
    {
        super(materialIn, slot, builder);
    }
    
    @OnlyIn(Dist.CLIENT)
    @Override
    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
    {
        return "malum:textures/armor/spirited_steel_armor.png";
    }

    public static boolean hasArmorSet(PlayerEntity playerEntity)
    {
        return MalumHelper.hasArmorSet(playerEntity, ModItems.spirited_steel_battle_helm, ModItems.spirited_steel_battle_chestplate, ModItems.spirited_steel_battle_leggings, ModItems.spirited_steel_battle_shoes);
    }
    @OnlyIn(Dist.CLIENT)
    @Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default)
    {
        return (A) new ModelSpiritedSteelBattleArmor(slot);
    }
}