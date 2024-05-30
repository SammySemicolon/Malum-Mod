package com.sammy.malum.common.enchantment;

import com.sammy.malum.registry.common.item.EnchantmentRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class HauntedEnchantment extends Enchantment {
    public HauntedEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentRegistry.SCYTHE_OR_STAFF, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    /*TODO
    public static void addMagicDamage(ItemAttributeModifierEvent event) {
        if (event.getSlotType().equals(EquipmentSlot.MAINHAND)) {
            ItemStack itemStack = event.getItemStack();
            int enchantmentLevel = itemStack.getEnchantmentLevel(EnchantmentRegistry.HAUNTED.get());
            if (enchantmentLevel > 0) {
                UUID uuid = LodestoneAttributeRegistry.UUIDS.get(LodestoneAttributeRegistry.MAGIC_DAMAGE);
                final Attribute magicDamage = LodestoneAttributeRegistry.MAGIC_DAMAGE.get();
                if (event.getOriginalModifiers().containsKey(magicDamage)) {
                    AttributeModifier attributeModifier = null;
                    if (event.getOriginalModifiers().get(magicDamage).size() > 0) {
                        attributeModifier = event.getOriginalModifiers().get(magicDamage).iterator().next();
                    }
                    if (attributeModifier != null) {
                        final double amount = attributeModifier.getAmount() + enchantmentLevel;
                        AttributeModifier newMagicDamage = new AttributeModifier(uuid, "Weapon magic damage", amount, AttributeModifier.Operation.ADDITION);
                        event.removeAttribute(magicDamage);
                        event.addModifier(magicDamage, newMagicDamage);
                    }
                } else {
                    event.addModifier(magicDamage, new AttributeModifier(uuid, "Weapon magic damage", enchantmentLevel, AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

     */

    @Override
    public int getMaxLevel() {
        return 2;
    }

}