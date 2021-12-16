package com.sammy.malum.common.item.misc;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;

public class MalumFuelBlockItem extends BlockItem
{
    public final int fuel;
    public MalumFuelBlockItem(Block block, Properties properties, int fuel)
    {
        super(block, properties);
        this.fuel = fuel;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return fuel;
    }

}
