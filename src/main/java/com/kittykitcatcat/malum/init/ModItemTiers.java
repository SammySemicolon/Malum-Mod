package com.kittykitcatcat.malum.init;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class ModItemTiers
{
    public static final ItemTier TIER1_ITEM = new ItemTier(
        3,
        880,
        7.0F,
        2.5F,
        22);
    public static final ItemTier TIER2_ITEM = new ItemTier(
        3,
        1820,
        9.0F,
        3.5F,
        24);

    public static final ArmorTier TIER1_ARMOR = new ArmorTier(
            "soul_crystal",
            18,
            new int[]{2, 5, 7, 3},
            16,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,
            1.0F,
            () -> Ingredient.fromItems(ModItems.lit_evil_pumpkin));

    public static final ArmorTier TIER2_ARMOR = new ArmorTier(
            "soul_steel",
            24,
            new int[]{3, 6, 8, 3},
            22,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,
            1.0F,
            () -> Ingredient.fromItems(ModItems.evil_pumpkin));
    private static class ItemTier implements IItemTier
    {
        private final int harvestLevel;
        private final int maxUses;
        private final float efficiency;
        private final float attackDamage;
        private final int enchantability;

        public ItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability)
        {
            this.harvestLevel = harvestLevel;
            this.maxUses = maxUses;
            this.efficiency = efficiency;
            this.attackDamage = attackDamage;
            this.enchantability = enchantability;
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
            return null;
        }
    }
    private static class ArmorTier implements IArmorMaterial
    {
        private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
        private final String name;
        private final int maxDamageFactor;
        private final int[] damageReductionAmountArray;
        private final int enchantability;
        private final SoundEvent soundEvent;
        private final float toughness;
        private final LazyValue<Ingredient> repairMaterial;

        private ArmorTier(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountsIn, int enchantabilityIn, SoundEvent equipSoundIn, float p_i48533_8_, Supplier<Ingredient> repairMaterialSupplier) {
            this.name = nameIn;
            this.maxDamageFactor = maxDamageFactorIn;
            this.damageReductionAmountArray = damageReductionAmountsIn;
            this.enchantability = enchantabilityIn;
            this.soundEvent = equipSoundIn;
            this.toughness = p_i48533_8_;
            this.repairMaterial = new LazyValue<>(repairMaterialSupplier);
        }

        public int getDurability(EquipmentSlotType slotIn) {
            return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
        }

        public int getDamageReductionAmount(EquipmentSlotType slotIn) {
            return this.damageReductionAmountArray[slotIn.getIndex()];
        }

        public int getEnchantability() {
            return this.enchantability;
        }

        public SoundEvent getSoundEvent() {
            return this.soundEvent;
        }

        public Ingredient getRepairMaterial() {
            return this.repairMaterial.getValue();
        }

        @OnlyIn(Dist.CLIENT)
        public String getName() {
            return this.name;
        }

        public float getToughness() {
            return this.toughness;
        }
    }
}