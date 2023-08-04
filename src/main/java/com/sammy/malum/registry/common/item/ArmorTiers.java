package com.sammy.malum.registry.common.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ArmorTiers {
    public enum ArmorTierEnum implements ArmorMaterial {
        SPIRIT_HUNTER("malum:spirit_hunter", 16, new int[]{1, 3, 4, 2}, 15, () -> SoundEvents.ARMOR_EQUIP_LEATHER, ItemRegistry.SPIRIT_FABRIC, 0),
        SOUL_STAINED_STEEL("malum:soul_stained_steel", 22, new int[]{2, 6, 7, 3}, 11, () -> SoundEvents.ARMOR_EQUIP_IRON, ItemRegistry.SOUL_STAINED_STEEL_INGOT, 2),
        SOUL_STAINED_STRONGHOLD("malum:soul_stained_stronghold", 36, new int[]{4, 7, 9, 5}, 13, () -> SoundEvents.ARMOR_EQUIP_IRON, ItemRegistry.SOUL_STAINED_STEEL_INGOT, 5);
        private final String name;
        private final int durabilityMultiplier;
        private final int[] damageReduction;
        private final int enchantability;
        private final Supplier<SoundEvent> equipSound;
        private final Supplier<Item> repairItem;
        private final float toughness;
        private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};

        ArmorTierEnum(String name, int durabilityMultiplier, int[] damageReduction, int enchantability, Supplier<SoundEvent> equipSound, Supplier<Item> repairItem, float toughness) {
            this.name = name;
            this.durabilityMultiplier = durabilityMultiplier;
            this.damageReduction = damageReduction;
            this.enchantability = enchantability;
            this.equipSound = equipSound;
            this.repairItem = repairItem;
            this.toughness = toughness;
        }

        @Override
        public int getDurabilityForType(ArmorItem.Type type) {
            return durabilityMultiplier * MAX_DAMAGE_ARRAY[type.getSlot().getIndex()];
        }

        @Override
        public int getDefenseForType(ArmorItem.Type type) {
            return damageReduction[type.getSlot().getIndex()];
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
            if (this.equals(SOUL_STAINED_STRONGHOLD)) {
                return 0.1f;
            }
            return 0;
        }
    }
}
