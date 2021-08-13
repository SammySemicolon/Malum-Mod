package com.sammy.malum.common.enchantment;

import com.sammy.malum.core.init.enchantment.MalumEnchantments;
import com.sammy.malum.core.mod_systems.spirit.SpiritHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class HauntedEnchantment extends Enchantment
{
    public HauntedEnchantment()
    {
        super(Rarity.UNCOMMON, MalumEnchantments.SCYTHE_OR_DAGGER, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
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
