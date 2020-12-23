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
        getOrCreateBuilder(FLOWERS).add(MalumBlocks.LAVENDER.get(), MalumBlocks.TAINTED_LAVENDER.get(), MalumBlocks.MARIGOLD.get());
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
        getOrCreateBuilder(LOGS).add(MalumBlocks.SUN_KISSED_LOG.get(), MalumBlocks.TAINTED_LOG.get(), MalumBlocks.SUN_KISSED_WOOD.get(), MalumBlocks.TAINTED_WOOD.get());
        getOrCreateBuilder(DIRT).add(getModBlocks(b -> b instanceof GrassBlock));
        getOrCreateBuilder(SAPLINGS).add(getModBlocks(b -> b instanceof SaplingBlock));
        getOrCreateBuilder(PLANKS).add(MalumBlocks.SUN_KISSED_PLANKS.get(), MalumBlocks.TAINTED_PLANKS.get());
        getOrCreateBuilder(WOODEN_FENCES).add(MalumBlocks.SUN_KISSED_PLANKS_FENCE.get(), MalumBlocks.TAINTED_PLANKS_FENCE.get(),MalumBlocks.SUN_KISSED_PLANKS_FENCE_GATE.get(), MalumBlocks.TAINTED_PLANKS_FENCE_GATE.get());
        getOrCreateBuilder(WOODEN_DOORS).add(MalumBlocks.SUN_KISSED_DOOR.get(), MalumBlocks.TAINTED_DOOR.get());
        getOrCreateBuilder(WOODEN_STAIRS).add(MalumBlocks.SUN_KISSED_PLANKS_STAIRS.get(), MalumBlocks.TAINTED_PLANKS_STAIRS.get());
        getOrCreateBuilder(WOODEN_SLABS).add(MalumBlocks.SUN_KISSED_PLANKS_SLAB.get(), MalumBlocks.TAINTED_PLANKS_SLAB.get());
        getOrCreateBuilder(WOODEN_TRAPDOORS).add(MalumBlocks.SUN_KISSED_TRAPDOOR.get(), MalumBlocks.TAINTED_TRAPDOOR.get(),MalumBlocks.SOLID_SUN_KISSED_TRAPDOOR.get(), MalumBlocks.SOLID_TAINTED_TRAPDOOR.get());
        getOrCreateBuilder(WOODEN_PRESSURE_PLATES).add(MalumBlocks.SUN_KISSED_PLANKS_PRESSURE_PLATE.get(), MalumBlocks.TAINTED_PLANKS_PRESSURE_PLATE.get());
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