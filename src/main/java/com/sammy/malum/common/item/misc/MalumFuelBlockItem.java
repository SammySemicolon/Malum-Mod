package com.sammy.malum.common.item.misc;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

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
