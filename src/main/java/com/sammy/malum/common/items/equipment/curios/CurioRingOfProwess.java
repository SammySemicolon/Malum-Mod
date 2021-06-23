package com.sammy.malum.common.items.equipment.curios;

import com.sammy.malum.core.init.items.MalumItems;
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
        return new ItemStack(MalumItems.CONFINED_BRILLIANCE.get(), previousStack.getCount());
    }
}