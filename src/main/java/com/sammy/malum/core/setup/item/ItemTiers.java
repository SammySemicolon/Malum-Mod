package com.sammy.malum.core.setup.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public class ItemTiers
{
    public enum ItemTierEnum implements Tier
    {
        SOUL_STAINED_STEEL(1731, 7.5f, 2.5f, 3, 16, ItemRegistry.SOUL_STAINED_STEEL_INGOT),
        TYRVING(1022, 8f, 1f, 3, 12, ItemRegistry.TWISTED_ROCK);
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
        public int getUses()
        {
            return maxUses;
        }
        
        @Override
        public float getSpeed()
        {
            return efficiency;
        }
        
        @Override
        public float getAttackDamageBonus()
        {
            return attackDamage;
        }
        
        @Override
        public int getLevel()
        {
            return harvestLevel;
        }
        
        @Override
        public int getEnchantmentValue()
        {
            return enchantability;
        }
        
        @Override
        public Ingredient getRepairIngredient()
        {
            return Ingredient.of(repairItem.get());
        }
    }
}
