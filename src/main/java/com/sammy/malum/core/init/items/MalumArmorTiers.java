package com.sammy.malum.core.init.items;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class MalumArmorTiers
{
    public enum ArmorTierEnum implements IArmorMaterial
    {
        SOUL_STAINED_STEEL("malum:soul_stained_steel", 22, new int[]{2, 6, 7, 3}, 11, () -> SoundEvents.ITEM_ARMOR_EQUIP_IRON, MalumItems.SOUL_STAINED_STEEL_INGOT, 1),
        SOUL_STAINED_STRONGHOLD("malum:soul_stained_stronghold", 36, new int[]{4, 7, 9, 4}, 13, () -> SoundEvents.ITEM_ARMOR_EQUIP_IRON, MalumItems.SOUL_STAINED_STEEL_INGOT, 2);
        private final String name;
        private final int durabilityMultiplier;
        private final int[] damageReduction;
        private final int enchantability;
        private final Supplier<SoundEvent> equipSound;
        private final Supplier<Item> repairItem;
        private final float toughness;
        private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    
        ArmorTierEnum(String name, int durabilityMultiplier, int[] damageReduction, int enchantability, Supplier<SoundEvent> equipSound, Supplier<Item> repairItem, float toughness)
        {
            this.name = name;
            this.durabilityMultiplier = durabilityMultiplier;
            this.damageReduction = damageReduction;
            this.enchantability = enchantability;
            this.equipSound = equipSound;
            this.repairItem = repairItem;
            this.toughness = toughness;
        }
        
        @Override
        public int getDurability(EquipmentSlotType slot)
        {
            return durabilityMultiplier * MAX_DAMAGE_ARRAY[slot.getIndex()];
        }
        
        @Override
        public int getDamageReductionAmount(EquipmentSlotType slot)
        {
            return damageReduction[slot.getIndex()];
        }
        
        @Override
        public int getEnchantability()
        {
            return enchantability;
        }
        
        @Nonnull
        @Override
        public SoundEvent getSoundEvent()
        {
            return equipSound.get();
        }
        
        @Nonnull
        @Override
        public Ingredient getRepairMaterial()
        {
            return Ingredient.fromItems(repairItem.get());
        }
        
        @Nonnull
        @Override
        public String getName()
        {
            return name;
        }
        
        @Override
        public float getToughness()
        {
            return toughness;
        }
        
        @Override
        public float getKnockbackResistance()
        {
            return 0;
        }
    }
}
