package com.sammy.malum.core.data;

import net.minecraft.block.*;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Predicate;

import static com.sammy.malum.MalumHelper.takeAll;
import static com.sammy.malum.core.init.blocks.MalumBlocks.BLOCKS;
import static net.minecraft.tags.BlockTags.*;
import static net.minecraftforge.common.Tags.Blocks.DIRT;

public class BlockTagProvider extends BlockTagsProvider
{
    public BlockTagProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }
    
    @Override
    protected void registerTags()
    {
        getOrCreateBuilder(BlockTags.SLABS).add(getModBlocks(b -> b instanceof SlabBlock));
        getOrCreateBuilder(BlockTags.STAIRS).add(getModBlocks(b -> b instanceof StairsBlock));
        getOrCreateBuilder(BlockTags.WALLS).add(getModBlocks(b -> b instanceof WallBlock));
        getOrCreateBuilder(BlockTags.FENCES).add(getModBlocks(b -> b instanceof FenceBlock));
        getOrCreateBuilder(BlockTags.FENCE_GATES).add(getModBlocks(b -> b instanceof FenceGateBlock));
        getOrCreateBuilder(DOORS).add(getModBlocks(b -> b instanceof DoorBlock));
        getOrCreateBuilder(TRAPDOORS).add(getModBlocks(b -> b instanceof TrapDoorBlock));
        getOrCreateBuilder(BUTTONS).add(getModBlocks(b -> b instanceof AbstractButtonBlock));
        getOrCreateBuilder(WOODEN_BUTTONS).add(getModBlocks(b -> b instanceof WoodButtonBlock));
        getOrCreateBuilder(PRESSURE_PLATES).add(getModBlocks(b -> b instanceof AbstractPressurePlateBlock));
        getOrCreateBuilder(LOGS).add(getModBlocks(b -> b instanceof RotatedPillarBlock));
        getOrCreateBuilder(DIRT).add(getModBlocks(b -> b instanceof GrassBlock));
        super.registerTags();
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