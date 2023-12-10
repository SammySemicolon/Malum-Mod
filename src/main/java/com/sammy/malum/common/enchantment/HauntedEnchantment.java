package com.sammy.malum.common.enchantment;

import com.sammy.malum.registry.common.DamageTypeRegistry;
import com.sammy.malum.registry.common.item.EnchantmentRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.event.*;
import team.lodestar.lodestone.registry.common.*;

public class HauntedEnchantment extends Enchantment {
    public HauntedEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentRegistry.SCYTHE_OR_STAFF, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    public static void addMagicDamage(ItemAttributeModifierEvent event) {
        if (event.getSlotType().equals(EquipmentSlot.MAINHAND)) {
            ItemStack itemStack = event.getItemStack();
            int enchantmentLevel = itemStack.getEnchantmentLevel(EnchantmentRegistry.HAUNTED.get());
            if (enchantmentLevel > 0) {
                event.addModifier(LodestoneAttributeRegistry.MAGIC_DAMAGE.get(),
                        new AttributeModifier(LodestoneAttributeRegistry.UUIDS.get(LodestoneAttributeRegistry.MAGIC_DAMAGE), "Weapon magic damage", enchantmentLevel, AttributeModifier.Operation.ADDITION));
            }
        }
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

}