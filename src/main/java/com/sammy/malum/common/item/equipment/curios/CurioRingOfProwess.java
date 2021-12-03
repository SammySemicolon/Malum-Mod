package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.core.registry.items.ItemRegistry;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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
        World worldIn = attacker.world;
        int i = 3 + worldIn.rand.nextInt(2) + worldIn.rand.nextInt(3);

        while (i > 0) {
            int j = ExperienceOrbEntity.getXPSplit(i);
            i -= j;
            worldIn.addEntity(new ExperienceOrbEntity(worldIn, attacker.getPosX(), attacker.getPosY(), attacker.getPosZ(), j));
        }
    }
}