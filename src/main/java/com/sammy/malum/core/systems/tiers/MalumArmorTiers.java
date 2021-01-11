package com.sammy.malum.core.systems.tiers;

import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.MalumSounds;
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
        SPIRITED_METAL_ARMOR("ruin_armor", 36, new int[]{2, 7, 8, 3}, 16, () -> SoundEvents.ITEM_ARMOR_EQUIP_IRON, MalumItems.SPIRITED_METAL_INGOT, 1),
        UMBRAL_ARMOR("umbral_armor", 45, new int[]{4, 7, 10, 4}, 18, () -> SoundEvents.ITEM_ARMOR_EQUIP_IRON, MalumItems.UMBRAL_METAL_INGOT, 5);
        
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
            if (this.equals(UMBRAL_ARMOR))
            {
                return 0.25f;
            }
            return 0;
        }
    }
}
