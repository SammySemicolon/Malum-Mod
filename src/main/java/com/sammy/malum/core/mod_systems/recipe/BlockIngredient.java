package com.sammy.malum.core.mod_systems.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;

import java.util.ArrayList;
import java.util.List;

public class BlockIngredient
{
    public ITag<Block> tag;
    public Block block;
    
    public boolean isSimple()
    {
        return tag == null;
    }
    public List<ItemStack> stacks()
    {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        if (isSimple())
        {
            stacks.add(block.asItem().getDefaultInstance());
        }
        else
        {
            for(Block tagBlock : tag.getAllElements())
            {
                stacks.add(tagBlock.asItem().getDefaultInstance());
            }
        }
        return stacks;
    }
    public BlockIngredient(ITag<Block> tag) {
        this.tag = tag;
    }
    public BlockIngredient(Block block) {
        this.block = block;
    }
}
