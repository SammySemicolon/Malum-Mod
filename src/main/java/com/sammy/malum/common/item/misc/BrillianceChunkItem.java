package com.sammy.malum.common.item.misc;

import com.sammy.malum.core.systems.spirit.ISpiritEntityGlow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.Level.Level;

import java.awt.*;

import net.minecraft.item.Item.Properties;

public class BrillianceChunkItem extends Item {
    public BrillianceChunkItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level LevelIn, LivingEntity entityLiving) {
        int i = 3 + LevelIn.random.nextInt(5) + LevelIn.random.nextInt(5);

        while (i > 0) {
            int j = ExperienceOrbEntity.getExperienceValue(i);
            i -= j;
            LevelIn.addFreshEntity(new ExperienceOrbEntity(LevelIn, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), j));
        }
        return super.finishUsingItem(stack, LevelIn, entityLiving);
    }
}
