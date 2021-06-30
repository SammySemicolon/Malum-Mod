package com.sammy.malum.core.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.init.items.MalumItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.sammy.malum.core.init.items.MalumItemTags.RUNEWOOD_LOGS;

public class MalumItemTagProvider extends ItemTagsProvider
{
    public MalumItemTagProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper)
    {
        super(dataGenerator, blockTagProvider, MalumMod.MODID, existingFileHelper);
    }
    
    @Override
    public String getName()
    {
        return "Malum Item Tags";
    }

    @Override
    protected void registerTags()
    {
        getOrCreateBuilder(Tags.Items.GEMS).add(MalumItems.SOULSTONE_ORE.get(), MalumItems.BLAZING_QUARTZ.get());

        this.copy(BlockTags.WOOL, ItemTags.WOOL);
        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copy(BlockTags.STONE_BRICKS, ItemTags.STONE_BRICKS);
        this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        this.copy(BlockTags.BUTTONS, ItemTags.BUTTONS);
        this.copy(BlockTags.CARPETS, ItemTags.CARPETS);
        this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        this.copy(BlockTags.DOORS, ItemTags.DOORS);
        this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        this.copy(BlockTags.OAK_LOGS, ItemTags.OAK_LOGS);
        this.copy(BlockTags.DARK_OAK_LOGS, ItemTags.DARK_OAK_LOGS);
        this.copy(BlockTags.BIRCH_LOGS, ItemTags.BIRCH_LOGS);
        this.copy(BlockTags.ACACIA_LOGS, ItemTags.ACACIA_LOGS);
        this.copy(BlockTags.SPRUCE_LOGS, ItemTags.SPRUCE_LOGS);
        this.copy(BlockTags.JUNGLE_LOGS, ItemTags.JUNGLE_LOGS);
        this.copy(BlockTags.CRIMSON_STEMS, ItemTags.CRIMSON_STEMS);
        this.copy(BlockTags.WARPED_STEMS, ItemTags.WARPED_STEMS);
        this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        this.copy(BlockTags.LOGS, ItemTags.LOGS);
        this.copy(BlockTags.SAND, ItemTags.SAND);
        this.copy(BlockTags.SLABS, ItemTags.SLABS);
        this.copy(BlockTags.WALLS, ItemTags.WALLS);
        this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
        this.copy(BlockTags.ANVIL, ItemTags.ANVIL);
        this.copy(BlockTags.RAILS, ItemTags.RAILS);
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        this.copy(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
        this.copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        this.copy(BlockTags.BEDS, ItemTags.BEDS);
        this.copy(BlockTags.FENCES, ItemTags.FENCES);
        this.copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
        this.copy(BlockTags.FLOWERS, ItemTags.FLOWERS);
        this.copy(BlockTags.GOLD_ORES, ItemTags.GOLD_ORES);
        this.copy(BlockTags.SOUL_FIRE_BASE_BLOCKS, ItemTags.SOUL_FIRE_BASE_BLOCKS);
    
        getOrCreateBuilder(Tags.Items.SLIMEBALLS).add(MalumItems.SOLAR_SAPBALL.get());
    
        getOrCreateBuilder(RUNEWOOD_LOGS).add(MalumItems.RUNEWOOD_LOG.get(), MalumItems.STRIPPED_RUNEWOOD_LOG.get(), MalumItems.RUNEWOOD.get(), MalumItems.STRIPPED_RUNEWOOD.get());
    }
}