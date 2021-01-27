package com.sammy.malum.core.systems.recipes;

import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;

import java.util.ArrayList;
import java.util.List;

public class MalumSpiritIngredient
{
    public MalumSpiritType type;
    public int count;
    
    public static MalumSpiritIngredient simpleIngredient(MalumSpiritType type)
    {
        return new MalumSpiritIngredient(type, 1);
    }
    public static MalumSpiritIngredient simpleIngredient(MalumSpiritType type, int count)
    {
        return new MalumSpiritIngredient(type, count);
    }
    public MalumSpiritIngredient(MalumSpiritType type, int count) {
        this.type = type;
        this.count = count;
    }
}
