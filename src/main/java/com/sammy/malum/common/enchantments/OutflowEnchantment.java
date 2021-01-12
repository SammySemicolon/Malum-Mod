package com.sammy.malum.common.enchantments;

import com.sammy.malum.MalumHelper;
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
            MalumHelper.giveStackingEffect(MalumEffects.BLEEDING.get(), entity, 80,1);
        }
        super.onEntityDamaged(user, target, level);
    }
    
    @Override
    public int getMaxLevel()
    {
        return 2;
    }
    
}
