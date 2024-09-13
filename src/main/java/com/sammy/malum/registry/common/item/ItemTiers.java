package com.sammy.malum.registry.common.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public class ItemTiers {
    public enum ItemTierEnum implements Tier {
        SOUL_STAINED_STEEL(1250, 7.5f, 2.5f, 3, 16, ItemRegistry.SOUL_STAINED_STEEL_INGOT),
        MALIGNANT_ALLOY(2500, 8f, 4f, 3, 24, ItemRegistry.MALIGNANT_PEWTER_INGOT),
        TYRVING(1500, 8f, 1f, 3, 16, ItemRegistry.TWISTED_ROCK),
        HEX_STAFF(1250, 8f, 2.5f, 3, 16, ItemRegistry.MNEMONIC_FRAGMENT),
        AURIC_STAFF(2500, 8f, 2.5f, 3, 16, ItemRegistry.AURIC_EMBERS);
        private final int maxUses;
        private final float efficiency;
        private final float attackDamage;
        private final int harvestLevel;
        private final int enchantability;
        private final Supplier<Item> repairItem;

        ItemTierEnum(int maxUses, float efficiency, float attackDamage, int harvestLevel, int enchantability, Supplier<Item> repairItem) {
            this.maxUses = maxUses;
            this.efficiency = efficiency;
            this.attackDamage = attackDamage;
            this.harvestLevel = harvestLevel;
            this.enchantability = enchantability;
            this.repairItem = repairItem;
        }

        @Override
        public int getUses() {
            return maxUses;
        }

        @Override
        public float getSpeed() {
            return efficiency;
        }

        @Override
        public float getAttackDamageBonus() {
            return attackDamage;
        }

        @Override
        public int getLevel() {
            return harvestLevel;
        }

        @Override
        public int getEnchantmentValue() {
            return enchantability;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(repairItem.get());
        }
    }
}
