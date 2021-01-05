package com.sammy.malum.common.enchantments;

import com.sammy.malum.common.enchantments.MalumEnchantmentTypes;
import com.sammy.malum.core.init.MalumEffects;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;

public class OutflowEnchantment extends Enchantment
{
    public OutflowEnchantment()
    {
        super(Rarity.UNCOMMON, MalumEnchantmentTypes.scytheOnly, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
    }
    
    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level)
    {
        if (target instanceof LivingEntity)
        {
            LivingEntity entity = (LivingEntity) target;
            int duration = 80;
            if (entity.getActivePotionEffect(MalumEffects.BLEEDING.get()) != null)
            {
                duration += entity.getActivePotionEffect(MalumEffects.BLEEDING.get()).getDuration();
            }
            entity.addPotionEffect(new EffectInstance(MalumEffects.BLEEDING.get(), duration, level));
        }
        super.onEntityDamaged(user, target, level);
    }
    
    @Override
    public int getMaxLevel()
    {
        return 5;
    }
    
}
