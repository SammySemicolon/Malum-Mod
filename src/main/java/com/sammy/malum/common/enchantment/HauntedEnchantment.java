package com.sammy.malum.common.enchantment;

import com.google.common.collect.Multimap;
import com.sammy.malum.registry.common.item.EnchantmentRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import team.lodestar.lodestone.registry.common.LodestoneAttributeRegistry;

import java.util.UUID;

public class HauntedEnchantment extends Enchantment {
    public HauntedEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentRegistry.SCYTHE_OR_STAFF, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    public static void addMagicDamage(EquipmentSlot slot, ItemStack stack, Multimap<Attribute, AttributeModifier> original) {
        if (slot.equals(EquipmentSlot.MAINHAND)) {
            int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.HAUNTED.get(), stack);
            if (enchantmentLevel > 0) {
                UUID uuid = LodestoneAttributeRegistry.UUIDS.get(LodestoneAttributeRegistry.MAGIC_DAMAGE);
                final Attribute magicDamage = LodestoneAttributeRegistry.MAGIC_DAMAGE.get();
                if (original.containsKey(magicDamage)) {
                    AttributeModifier attributeModifier = null;
                    if (original.get(magicDamage).size() > 0) {
                        attributeModifier = original.get(magicDamage).iterator().next();
                    }
                    if (attributeModifier != null) {
                        final double amount = attributeModifier.getAmount() + enchantmentLevel;
                        AttributeModifier newMagicDamage = new AttributeModifier(uuid, "Weapon magic damage", amount, AttributeModifier.Operation.ADDITION);

                        original.removeAll(magicDamage);
                        original.put(magicDamage, newMagicDamage);
                    }
                } else {
                    original.put(magicDamage, new AttributeModifier(uuid, "Weapon magic damage", enchantmentLevel, AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

}