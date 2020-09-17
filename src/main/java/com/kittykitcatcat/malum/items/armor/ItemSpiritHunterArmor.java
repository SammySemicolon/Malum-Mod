package com.kittykitcatcat.malum.items.armor;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.models.ModelSpiritHunterArmor;
import com.kittykitcatcat.malum.models.ModelSpiritedSteelBattleArmor;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class ItemSpiritHunterArmor extends ModArmor
{

    public ItemSpiritHunterArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder)
    {
        super(materialIn, slot, builder);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
    {
        return "malum:textures/armor/spirit_hunter_armor.png";
    }
    
    
    public static boolean hasArmorSet(PlayerEntity playerEntity)
    {
        return MalumHelper.hasArmorSet(playerEntity, ModItems.spirit_hunter_helm, ModItems.spirit_hunter_chestplate, ModItems.spirit_hunter_leggings, ModItems.spirit_hunter_shoes);
    }
    @OnlyIn(Dist.CLIENT)
    @Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default)
    {
        return (A) new ModelSpiritHunterArmor(slot);
    }
}