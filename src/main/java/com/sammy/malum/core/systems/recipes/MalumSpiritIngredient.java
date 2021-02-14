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
    
    public MalumSpiritIngredient(MalumSpiritType type, int count) {
        this.type = type;
        this.count = count;
    }
    public MalumSpiritIngredient(MalumSpiritType type) {
        this.type = type;
        this.count = 1;
    }
    public boolean matches(ItemStack stack)
    {
        return stack.getItem().equals(type.splinterItem) && stack.getCount() >= count;
    }
}
