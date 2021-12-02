package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.core.registry.items.ItemRegistry;
import net.minecraft.item.ItemStack;

public class CurioRingOfProwess extends MalumCurioItem
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
    public ItemStack spiritReplacementStack(ItemStack previousStack)
    {
        return new ItemStack(ItemRegistry.BRILLIANCE_CHUNK.get(), previousStack.getCount());
    }
}