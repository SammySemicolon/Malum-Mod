package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.core.registry.items.ItemRegistry;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.Level.Level;

import net.minecraft.item.Item.Properties;

public class CurioRingOfProwess extends MalumCurioItem implements IEventResponderItem
{
    public CurioRingOfProwess(Properties builder)
    {
        super(builder);
    }

    @Override
    public boolean isGilded()
    {
        return true;
    }

    @Override
    public void pickupSpirit(LivingEntity attacker, ItemStack stack) {
        Level LevelIn = attacker.level;
        int i = 3 + LevelIn.random.nextInt(2) + LevelIn.random.nextInt(3);

        while (i > 0) {
            int j = ExperienceOrbEntity.getExperienceValue(i);
            i -= j;
            LevelIn.addFreshEntity(new ExperienceOrbEntity(LevelIn, attacker.getX(), attacker.getY(), attacker.getZ(), j));
        }
    }
}