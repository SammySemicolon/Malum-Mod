package com.sammy.malum.core.systems.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import java.util.function.Predicate;

public class ItemInventory extends SimpleInventory
{
    ItemStack stack;
    
    public ItemInventory(ItemStack stack, int slotCount, int slotSize)
    {
        super(slotCount, slotSize);
        this.stack = stack;
    
        if (stack.getOrCreateTag().contains("inventory"))
        {
            readData(stack.getOrCreateTag());
        }
    }
    
    @Override
    protected void onContentsChanged(int slot)
    {
        writeData(stack.getOrCreateTag());
        super.onContentsChanged(slot);
    }
}
