package com.sammy.malum.common.enchantment;

import com.sammy.malum.registry.common.DamageSourceRegistry;
import com.sammy.malum.registry.common.item.EnchantmentRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;

public class HauntedEnchantment extends Enchantment {
    public HauntedEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentRegistry.SCYTHE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public void doPostAttack(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity entity) {
            if (entity.isAlive()) {
                entity.invulnerableTime = 0;
                entity.hurt(DamageSourceRegistry.create(user.level(), DamageSourceRegistry.VOODOO, user), level + 1);
            }
        }
        super.doPostAttack(user, target, level);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

}