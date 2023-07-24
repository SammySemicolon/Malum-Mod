package com.sammy.malum.common.item.curiosities.curios.alchemical;

import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CurioRingOfProwess extends MalumCurioItem implements IMalumEventResponderItem {
    public CurioRingOfProwess(Properties builder) {
        super(builder, MalumTrinketType.ALCHEMICAL);
    }

    @Override
    public void pickupSpirit(LivingEntity collector, ItemStack stack, double arcaneResonance) {
        Level level = collector.level;
        int floored = (int) Math.floor(arcaneResonance);
        int i = 1 + level.random.nextInt(1+floored) + level.random.nextInt(2+floored);

        while (i > 0) {
            int j = ExperienceOrb.getExperienceValue(i);
            i -= j;
            level.addFreshEntity(new ExperienceOrb(level, collector.getX(), collector.getY(), collector.getZ(), j));
        }
    }
}