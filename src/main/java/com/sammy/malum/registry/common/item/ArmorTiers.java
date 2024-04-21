package com.sammy.malum.registry.common.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ArmorTiers {
    public enum ArmorTierEnum implements ArmorMaterial {
        SPIRIT_HUNTER("malum:spirit_hunter", 16, new int[]{1, 3, 4, 2}, 16, () -> SoundEvents.ARMOR_EQUIP_LEATHER, ItemRegistry.SPIRIT_FABRIC, 0),
        SOUL_STAINED_STEEL("malum:soul_stained_steel", 24, new int[]{2, 6, 7, 3}, 16, () -> SoundEvents.ARMOR_EQUIP_IRON, ItemRegistry.SOUL_STAINED_STEEL_PLATING, 2),
        MALIGNANT_ALLOY("malum:malignant_alloy", 32, new int[]{3, 6, 8, 3}, 24, () -> SoundEvents.ARMOR_EQUIP_NETHERITE, ItemRegistry.MALIGNANT_PEWTER_PLATING, 2, 0.1f);
        private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
        private final String name;
        private final int durabilityMultiplier;
        private final int[] damageReduction;
        private final int enchantability;
        private final Supplier<SoundEvent> equipSound;
        private final Supplier<Item> repairItem;
        private final float toughness;
        private final float knockbackResistance;

        ArmorTierEnum(String name, int durabilityMultiplier, int[] damageReduction, int enchantability, Supplier<SoundEvent> equipSound, Supplier<Item> repairItem, float toughness) {
            this(name, durabilityMultiplier, damageReduction, enchantability, equipSound, repairItem, toughness, 0);
        }
        ArmorTierEnum(String name, int durabilityMultiplier, int[] damageReduction, int enchantability, Supplier<SoundEvent> equipSound, Supplier<Item> repairItem, float toughness, float knockbackResistance) {
            this.name = name;
            this.durabilityMultiplier = durabilityMultiplier;
            this.damageReduction = damageReduction;
            this.enchantability = enchantability;
            this.equipSound = equipSound;
            this.repairItem = repairItem;
            this.toughness = toughness;
            this.knockbackResistance = knockbackResistance;
        }

        @Override
        public int getDurabilityForType(ArmorItem.Type slot) {
            return durabilityMultiplier * MAX_DAMAGE_ARRAY[slot.getSlot().getIndex()];
        }

        @Override
        public int getDefenseForType(ArmorItem.Type slot) {
            return damageReduction[slot.getSlot().getIndex()];
        }


        @Override
        public int getEnchantmentValue() {
            return enchantability;
        }

        @Nonnull
        @Override
        public SoundEvent getEquipSound() {
            return equipSound.get();
        }

        @Nonnull
        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(repairItem.get());
        }

        @Nonnull
        @Override
        public String getName() {
            return name;
        }

        @Override
        public float getToughness() {
            return toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return knockbackResistance;
        }
    }
}
