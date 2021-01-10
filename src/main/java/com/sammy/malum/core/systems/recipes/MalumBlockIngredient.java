package com.sammy.malum.core.systems.recipes;

import com.sammy.malum.common.blocks.itemstand.ItemStandBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;

import java.util.ArrayList;
import java.util.List;

public class MalumBlockIngredient
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
    public MalumBlockIngredient(ITag<Block> tag) {
        this.tag = tag;
    }
    public MalumBlockIngredient(Block block) {
        this.block = block;
    }
}
