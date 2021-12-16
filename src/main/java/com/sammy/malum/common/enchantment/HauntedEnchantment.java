package com.sammy.malum.common.enchantment;

import com.sammy.malum.core.registry.enchantment.MalumEnchantments;
import com.sammy.malum.core.registry.misc.DamageSourceRegistry;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

import net.minecraft.enchantment.Enchantment.Rarity;

public class HauntedEnchantment extends Enchantment
{
    public HauntedEnchantment()
    {
        super(Rarity.UNCOMMON, MalumEnchantments.SCYTHE_ONLY, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
    }
    
    @Override
    public void doPostAttack(LivingEntity user, Entity target, int level)
    {
        if (target instanceof LivingEntity)
        {
            LivingEntity entity = (LivingEntity) target;
            entity.invulnerableTime = 0;
            entity.hurt(DamageSourceRegistry.causeVoodooDamage(user), level+1);
        }
        super.doPostAttack(user, target, level);
    }

    @Override
    public int getMaxLevel()
    {
        return 2;
    }
    
}
