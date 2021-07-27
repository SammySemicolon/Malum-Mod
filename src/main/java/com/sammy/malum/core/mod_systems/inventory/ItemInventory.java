package com.sammy.malum.core.mod_systems.inventory;

import net.minecraft.item.ItemStack;

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
