package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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
        Level level = attacker.level;
        int i = 3 + level.random.nextInt(2) + level.random.nextInt(3);

        while (i > 0) {
            int j = ExperienceOrb.getExperienceValue(i);
            i -= j;
            level.addFreshEntity(new ExperienceOrb(level, attacker.getX(), attacker.getY(), attacker.getZ(), j));
        }
    }
}