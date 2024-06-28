package com.sammy.malum.common.item;

import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BrillianceChunkItem extends Item {
    public BrillianceChunkItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        int i = 3 + level.random.nextInt(5) + level.random.nextInt(5);

        while (i > 0) {
            int j = ExperienceOrb.getExperienceValue(i);
            i -= j;
            level.addFreshEntity(new ExperienceOrb(level, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), j));
        }
        return super.finishUsingItem(stack, level, entityLiving);
    }

    public int getUseDuration(ItemStack pStack) {
        return 5;
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
