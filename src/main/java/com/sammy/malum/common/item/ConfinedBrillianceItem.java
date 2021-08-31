package com.sammy.malum.common.item;

import com.sammy.malum.core.mod_systems.spirit.ISpiritEntityGlow;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.awt.*;

public class ConfinedBrillianceItem extends Item implements ISpiritEntityGlow {
    public ConfinedBrillianceItem(Properties properties) {
        super(properties);
    }

    @Override
    public Color getColor() {
        return new Color(31, 175, 18);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        int i = 3 + worldIn.rand.nextInt(5) + worldIn.rand.nextInt(5);

        while (i > 0) {
            int j = ExperienceOrbEntity.getXPSplit(i);
            i -= j;
            worldIn.addEntity(new ExperienceOrbEntity(worldIn, entityLiving.getPosX(), entityLiving.getPosY(), entityLiving.getPosZ(), j));
        }
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
