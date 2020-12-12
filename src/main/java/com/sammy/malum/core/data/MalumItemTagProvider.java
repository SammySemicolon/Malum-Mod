package com.sammy.malum.core.data;

import net.minecraft.block.SlabBlock;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;

public class MalumItemTagProvider extends ItemTagsProvider
{
    public MalumItemTagProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider)
    {
        super(dataGenerator, blockTagProvider);
    }
    
    @Override
    public String getName()
    {
        return "Malum Item Tags";
    }
    
    @Override
    protected void registerTags()
    {
    }
}
