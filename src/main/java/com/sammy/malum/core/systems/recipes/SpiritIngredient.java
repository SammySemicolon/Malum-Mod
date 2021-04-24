package com.sammy.malum.core.systems.recipes;

import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;

import java.util.ArrayList;
import java.util.List;

public class SpiritIngredient
{
    public MalumSpiritType type;
    public int count;
    
    public SpiritIngredient(MalumSpiritType type, int count)
    {
        this.type = type;
        this.count = count;
    }
    
    public SpiritIngredient(MalumSpiritType type)
    {
        this.type = type;
        this.count = 1;
    }
    
    public boolean matches(ItemStack stack)
    {
        return stack.getItem().equals(type.splinterItem) && stack.getCount() >= count;
    }
    
    public ItemStack getItem()
    {
        return new ItemStack(type.splinterItem, count);
    }
}