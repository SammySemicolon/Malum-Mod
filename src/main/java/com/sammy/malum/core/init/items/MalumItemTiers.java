package com.sammy.malum.core.init.items;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public class MalumItemTiers
{
    public enum ItemTierEnum implements IItemTier
    {
        SOUL_STAINED_STEEL_ITEM(1731, 7.5f, 2.5f, 3, 16, MalumItems.SOUL_STAINED_STEEL_INGOT),
        SACRIFICIAL_DAGGER_ITEM(511, 6f, 2f, 2, 9, MalumItems.TAINTED_ROCK),
        TYRVING_ITEM(1022, 8f, 3f, 3, 12, MalumItems.TWISTED_ROCK);
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
