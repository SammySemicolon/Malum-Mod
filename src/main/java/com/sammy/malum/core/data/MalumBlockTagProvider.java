package com.sammy.malum.core.data;

import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.block.*;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static com.sammy.malum.core.init.blocks.MalumBlocks.BLOCKS;
import static net.minecraft.tags.BlockTags.*;
import static net.minecraftforge.common.Tags.Blocks.DIRT;

public class MalumBlockTagProvider extends BlockTagsProvider
{
    public MalumBlockTagProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }
    
    @Override
    protected void registerTags()
    {
       //getOrCreateBuilder(Tags.Blocks.ORES).add(MalumBlocks.SOLAR_ORE.get());
       //getOrCreateBuilder(Tags.Blocks.ORES).add(MalumBlocks.BLAZE_QUARTZ_ORE.get());
        getOrCreateBuilder(FLOWERS).add(MalumBlocks.LAVENDER.get(), MalumBlocks.MARIGOLD.get());
        getOrCreateBuilder(BlockTags.SLABS).add(getModBlocks(b -> b instanceof SlabBlock));
        getOrCreateBuilder(BlockTags.STAIRS).add(getModBlocks(b -> b instanceof StairsBlock));
        getOrCreateBuilder(BlockTags.WALLS).add(getModBlocks(b -> b instanceof WallBlock));
        getOrCreateBuilder(BlockTags.FENCES).add(getModBlocks(b -> b instanceof FenceBlock));
        getOrCreateBuilder(BlockTags.FENCE_GATES).add(getModBlocks(b -> b instanceof FenceGateBlock));
        getOrCreateBuilder(BlockTags.LEAVES).add(getModBlocks(b -> b instanceof LeavesBlock));
        getOrCreateBuilder(DOORS).add(getModBlocks(b -> b instanceof DoorBlock));
        getOrCreateBuilder(TRAPDOORS).add(getModBlocks(b -> b instanceof TrapDoorBlock));
        getOrCreateBuilder(BUTTONS).add(getModBlocks(b -> b instanceof AbstractButtonBlock));
        getOrCreateBuilder(WOODEN_BUTTONS).add(getModBlocks(b -> b instanceof WoodButtonBlock));
        getOrCreateBuilder(PRESSURE_PLATES).add(getModBlocks(b -> b instanceof AbstractPressurePlateBlock));
        getOrCreateBuilder(LOGS).add(MalumBlocks.RUNEWOOD_LOG.get(), MalumBlocks.RUNEWOOD.get());
        getOrCreateBuilder(DIRT).add(getModBlocks(b -> b instanceof GrassBlock || b instanceof FarmlandBlock));
        getOrCreateBuilder(SAPLINGS).add(getModBlocks(b -> b instanceof SaplingBlock));
        getOrCreateBuilder(PLANKS).add(MalumBlocks.RUNEWOOD_PLANKS.get());
        getOrCreateBuilder(WOODEN_FENCES).add(MalumBlocks.RUNEWOOD_PLANKS_FENCE.get(),MalumBlocks.RUNEWOOD_PLANKS_FENCE_GATE.get());
        getOrCreateBuilder(WOODEN_DOORS).add(MalumBlocks.RUNEWOOD_DOOR.get());
        getOrCreateBuilder(WOODEN_STAIRS).add(MalumBlocks.RUNEWOOD_PLANKS_STAIRS.get());
        getOrCreateBuilder(WOODEN_SLABS).add(MalumBlocks.RUNEWOOD_PLANKS_SLAB.get());
        getOrCreateBuilder(WOODEN_TRAPDOORS).add(MalumBlocks.RUNEWOOD_TRAPDOOR.get(),MalumBlocks.SOLID_RUNEWOOD_TRAPDOOR.get());
        getOrCreateBuilder(WOODEN_PRESSURE_PLATES).add(MalumBlocks.RUNEWOOD_PLANKS_PRESSURE_PLATE.get());
    }
    
    @Override
    public String getName()
    {
        return "Malum Block Tags";
    }
    
    @Nonnull
    private Block[] getModBlocks(Predicate<Block> predicate)
    {
        List<Block> ret = new ArrayList<>(Collections.emptyList());
        BLOCKS.getEntries().stream()
                .filter(b -> predicate.test(b.get())).forEach(b -> ret.add(b.get()));
        return ret.toArray(new Block[0]);
    }
}