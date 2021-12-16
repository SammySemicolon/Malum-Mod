package com.sammy.malum.common.item.misc;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.item.Item.Properties;

public class MalumFuelBlockItem extends BlockItem
{
    public final int fuel;
    public MalumFuelBlockItem(Block block, Properties properties, int fuel)
    {
        super(block, properties);
        this.fuel = fuel;
    }
    
    @Override
    public int getBurnTime(ItemStack itemStack)
    {
        return fuel;
    }
}
