package com.sammy.malum.common.items;

import com.sammy.malum.core.init.MalumItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public class MalumItemTiers
{
    public enum ItemTierEnum implements IItemTier
    {
        SOUL_STAINED_STEEL_ITEM(1951, 7.5f, 2.5f, 3, 16, MalumItems.SOUL_STAINED_STEEL_INGOT),
        TYRVING_ITEM(1021, 8f, 3f, 3, 12, MalumItems.DARKENED_ROCK),
        UMBRAL_ITEM(2450, 11f, 5f, 4, 18, MalumItems.UMBRAL_METAL_INGOT);
        private final int maxUses;
        private final float efficiency;
        private final float attackDamage;
        private final int harvestLevel;
        private final int enchantability;
        private final Supplier<Item> repairItem;
        
        ItemTierEnum(int maxUses, float efficiency, float attackDamage, int harvestLevel, int enchantability, Supplier<Item> repairItem)
        {
            this.maxUses = maxUses;
            this.efficiency = efficiency;
            this.attackDamage = attackDamage;
            this.harvestLevel = harvestLevel;
            this.enchantability = enchantability;
            this.repairItem = repairItem;
        }
        
        @Override
        public int getMaxUses()
        {
            return maxUses;
        }
        
        @Override
        public float getEfficiency()
        {
            return efficiency;
        }
        
        @Override
        public float getAttackDamage()
        {
            return attackDamage;
        }
        
        @Override
        public int getHarvestLevel()
        {
            return harvestLevel;
        }
        
        @Override
        public int getEnchantability()
        {
            return enchantability;
        }
        
        @Override
        public Ingredient getRepairMaterial()
        {
            return Ingredient.fromItems(repairItem.get());
        }
    }
}
