package com.kittykitcatcat.malum.recipes.spiritinfusionresults;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class CarryOverNBTResult implements ISpiritInfusionResult
{
    public void result(ItemStack catalyst, ItemStack outputStack)
    {
        if (catalyst.getTag() != null)
        {
            CompoundNBT nbt = catalyst.getTag();
            outputStack.setTag(nbt);
        }
    }
}
