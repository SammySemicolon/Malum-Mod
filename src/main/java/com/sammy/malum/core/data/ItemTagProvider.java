package com.sammy.malum.core.data;

import net.minecraft.block.SlabBlock;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;

public class ItemTagProvider extends ItemTagsProvider
{
    public ItemTagProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider)
    {
        super(dataGenerator, blockTagProvider);
    }
    
    @Override
    protected void registerTags()
    {
        super.registerTags();
    }
}
