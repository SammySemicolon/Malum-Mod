package com.sammy.malum.core.data;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumItems;
import net.minecraft.block.SlabBlock;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

import static com.sammy.malum.core.init.MalumItemTags.SUN_KISSED_LOGS;
import static com.sammy.malum.core.init.MalumItemTags.TAINTED_LOGS;
import static net.minecraft.tags.ItemTags.makeWrapperTag;

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
    
    public static final ITag.INamedTag<Item> SUN_KISSED_LOGS = makeWrapperTag("sun_logs");
    public static final ITag.INamedTag<Item> TAINTED_LOGS = makeWrapperTag("tainted_logs");
    
    public static ITag.INamedTag<Item> makeWrapperTag(String id)
    {
        return ItemTags.createOptional(MalumHelper.prefix(id));
    }
    @Override
    protected void registerTags()
    {
        getOrCreateBuilder(SUN_KISSED_LOGS).add(MalumItems.SUN_KISSED_LOG.get(), MalumItems.STRIPPED_SUN_KISSED_LOG.get(), MalumItems.SUN_KISSED_WOOD.get(), MalumItems.STRIPPED_SUN_KISSED_WOOD.get());
        getOrCreateBuilder(TAINTED_LOGS).add(MalumItems.TAINTED_LOG.get(), MalumItems.STRIPPED_TAINTED_LOG.get(), MalumItems.TAINTED_WOOD.get(), MalumItems.STRIPPED_TAINTED_WOOD.get());
    }
}