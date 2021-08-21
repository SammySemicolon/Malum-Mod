package com.sammy.malum.core.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.items.MalumItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.sammy.malum.core.init.items.MalumItemTags.RUNEWOOD_LOGS;

public class MalumItemTags extends ItemTagsProvider
{
    public MalumItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper)
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
        getOrCreateBuilder(Tags.Items.GEMS).add(MalumItems.SOULSTONE.get(), MalumItems.BLAZING_QUARTZ.get());

        this.copy(BlockTags.WOOL, net.minecraft.tags.ItemTags.WOOL);
        this.copy(BlockTags.PLANKS, net.minecraft.tags.ItemTags.PLANKS);
        this.copy(BlockTags.STONE_BRICKS, net.minecraft.tags.ItemTags.STONE_BRICKS);
        this.copy(BlockTags.WOODEN_BUTTONS, net.minecraft.tags.ItemTags.WOODEN_BUTTONS);
        this.copy(BlockTags.BUTTONS, net.minecraft.tags.ItemTags.BUTTONS);
        this.copy(BlockTags.CARPETS, net.minecraft.tags.ItemTags.CARPETS);
        this.copy(BlockTags.WOODEN_DOORS, net.minecraft.tags.ItemTags.WOODEN_DOORS);
        this.copy(BlockTags.WOODEN_STAIRS, net.minecraft.tags.ItemTags.WOODEN_STAIRS);
        this.copy(BlockTags.WOODEN_SLABS, net.minecraft.tags.ItemTags.WOODEN_SLABS);
        this.copy(BlockTags.WOODEN_FENCES, net.minecraft.tags.ItemTags.WOODEN_FENCES);
        this.copy(BlockTags.WOODEN_PRESSURE_PLATES, net.minecraft.tags.ItemTags.WOODEN_PRESSURE_PLATES);
        this.copy(BlockTags.DOORS, net.minecraft.tags.ItemTags.DOORS);
        this.copy(BlockTags.SAPLINGS, net.minecraft.tags.ItemTags.SAPLINGS);
        this.copy(BlockTags.OAK_LOGS, net.minecraft.tags.ItemTags.OAK_LOGS);
        this.copy(BlockTags.DARK_OAK_LOGS, net.minecraft.tags.ItemTags.DARK_OAK_LOGS);
        this.copy(BlockTags.BIRCH_LOGS, net.minecraft.tags.ItemTags.BIRCH_LOGS);
        this.copy(BlockTags.ACACIA_LOGS, net.minecraft.tags.ItemTags.ACACIA_LOGS);
        this.copy(BlockTags.SPRUCE_LOGS, net.minecraft.tags.ItemTags.SPRUCE_LOGS);
        this.copy(BlockTags.JUNGLE_LOGS, net.minecraft.tags.ItemTags.JUNGLE_LOGS);
        this.copy(BlockTags.CRIMSON_STEMS, net.minecraft.tags.ItemTags.CRIMSON_STEMS);
        this.copy(BlockTags.WARPED_STEMS, net.minecraft.tags.ItemTags.WARPED_STEMS);
        this.copy(BlockTags.LOGS_THAT_BURN, net.minecraft.tags.ItemTags.LOGS_THAT_BURN);
        this.copy(BlockTags.LOGS, net.minecraft.tags.ItemTags.LOGS);
        this.copy(BlockTags.SAND, net.minecraft.tags.ItemTags.SAND);
        this.copy(BlockTags.SLABS, net.minecraft.tags.ItemTags.SLABS);
        this.copy(BlockTags.WALLS, net.minecraft.tags.ItemTags.WALLS);
        this.copy(BlockTags.STAIRS, net.minecraft.tags.ItemTags.STAIRS);
        this.copy(BlockTags.ANVIL, net.minecraft.tags.ItemTags.ANVIL);
        this.copy(BlockTags.RAILS, net.minecraft.tags.ItemTags.RAILS);
        this.copy(BlockTags.LEAVES, net.minecraft.tags.ItemTags.LEAVES);
        this.copy(BlockTags.WOODEN_TRAPDOORS, net.minecraft.tags.ItemTags.WOODEN_TRAPDOORS);
        this.copy(BlockTags.TRAPDOORS, net.minecraft.tags.ItemTags.TRAPDOORS);
        this.copy(BlockTags.SMALL_FLOWERS, net.minecraft.tags.ItemTags.SMALL_FLOWERS);
        this.copy(BlockTags.BEDS, net.minecraft.tags.ItemTags.BEDS);
        this.copy(BlockTags.FENCES, net.minecraft.tags.ItemTags.FENCES);
        this.copy(BlockTags.TALL_FLOWERS, net.minecraft.tags.ItemTags.TALL_FLOWERS);
        this.copy(BlockTags.FLOWERS, net.minecraft.tags.ItemTags.FLOWERS);
        this.copy(BlockTags.GOLD_ORES, net.minecraft.tags.ItemTags.GOLD_ORES);
        this.copy(BlockTags.SOUL_FIRE_BASE_BLOCKS, net.minecraft.tags.ItemTags.SOUL_FIRE_BASE_BLOCKS);
        this.copy(Tags.Blocks.ORES, Tags.Items.ORES);

        getOrCreateBuilder(Tags.Items.SLIMEBALLS).add(MalumItems.SOLAR_SAPBALL.get());
    
        getOrCreateBuilder(RUNEWOOD_LOGS).add(MalumItems.RUNEWOOD_LOG.get(), MalumItems.STRIPPED_RUNEWOOD_LOG.get(), MalumItems.RUNEWOOD.get(), MalumItems.STRIPPED_RUNEWOOD.get(), MalumItems.SAP_FILLED_RUNEWOOD_LOG.get(), MalumItems.STRIPPED_SAP_FILLED_RUNEWOOD_LOG.get());
    }
}