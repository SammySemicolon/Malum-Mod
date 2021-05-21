package com.sammy.malum.common.enchantments;

import com.sammy.malum.core.systems.spirits.SpiritHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class HauntingEnchantment extends Enchantment
{
    public HauntingEnchantment()
    {
        super(Rarity.UNCOMMON, MalumEnchantmentTypes.scytheOnly, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
    }
    
    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level)
    {
        if (target instanceof LivingEntity)
        {
            LivingEntity entity = (LivingEntity) target;
            entity.hurtResistantTime = 0;
            SpiritHelper.causeVoodooDamage(user, entity, level+1);
        }
        super.onEntityDamaged(user, target, level);
    }
    
    @Override
    public int getMaxLevel()
    {
        return 2;
    }
    
}
