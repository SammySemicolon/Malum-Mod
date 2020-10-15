package com.sammy.malum.init;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ModItemTiers
{
    public enum ItemTier implements IItemTier {
        SPIRITED_STEEL_ITEM(800, 7f, 2.5f, 3, 22, () -> ModItems.spirited_steel_ingot),
        UMBRAL_ALLOY_ITEM(1820, 9f, 4f, 5, 58, () -> ModItems.umbral_steel_ingot);
        
        private final int maxUses;
        private final float efficiency;
        private final float attackDamage;
        private final int harvestLevel;
        private final int enchantability;
        private final Supplier<Item> repairItem;
        
        ItemTier(int maxUses, float efficiency, float attackDamage, int harvestLevel, int enchantability, Supplier<Item> repairItem) {
            this.maxUses = maxUses;
            this.efficiency = efficiency;
            this.attackDamage = attackDamage;
            this.harvestLevel = harvestLevel;
            this.enchantability = enchantability;
            this.repairItem = repairItem;
        }
        
        @Override
        public int getMaxUses() {
            return maxUses;
        }
        
        @Override
        public float getEfficiency() {
            return efficiency;
        }
        
        @Override
        public float getAttackDamage() {
            return attackDamage;
        }
        
        @Override
        public int getHarvestLevel() {
            return harvestLevel;
        }
        
        @Override
        public int getEnchantability() {
            return enchantability;
        }
        
        @Override
        public Ingredient getRepairMaterial() {
            return Ingredient.fromItems(repairItem.get());
        }
    }
    
    public enum ArmorMaterial implements IArmorMaterial
    {
        SPIRIT_HUNTER_ARMOR("spirit_hunter_armor", 5, new int[]{1, 3, 4, 2}, 24, () -> SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> ModItems.spirit_fabric, 0),
        SPIRITED_STEEL_BATTLE_ARMOR("spirited_steel_battle_armor", 22, new int[]{2, 5, 7, 3}, 18, () -> SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> ModItems.spirited_steel_ingot, 1),
        UMBRAL_ALLOY_BATTLE_ARMOR("umbral_alloy_battle_armor", 58, new int[]{5, 8, 10, 7}, 52, () -> SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, () -> ModItems.umbral_steel_ingot, 5);
        
        private final String name;
        private final int durabilityMultiplier;
        private final int[] damageReduction;
        private final int enchantability;
        private final Supplier<SoundEvent> equipSound;
        private final Supplier<Item> repairItem;
        private final float toughness;
        private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
        
        ArmorMaterial(String name, int durabilityMultiplier, int[] damageReduction, int enchantability, Supplier<SoundEvent> equipSound, Supplier<Item> repairItem, float toughness)
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