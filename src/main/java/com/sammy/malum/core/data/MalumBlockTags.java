package com.sammy.malum.core.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.block.MalumBlocks;
import net.minecraft.block.*;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static com.sammy.malum.core.init.block.MalumBlocks.BLOCKS;
import static net.minecraft.tags.BlockTags.*;
import static net.minecraftforge.common.Tags.Blocks.DIRT;

public class MalumBlockTags extends BlockTagsProvider
{
    public MalumBlockTags(DataGenerator generatorIn, ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, MalumMod.MODID, existingFileHelper);
    }
    
    @Override
    protected void registerTags() {
        getOrCreateBuilder(Tags.Blocks.ORES).add(MalumBlocks.SOULSTONE_ORE.get(), MalumBlocks.BLAZING_QUARTZ_ORE.get());

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
        getOrCreateBuilder(DIRT).add(getModBlocks(b -> b instanceof GrassBlock || b instanceof FarmlandBlock));
        getOrCreateBuilder(SAPLINGS).add(getModBlocks(b -> b instanceof SaplingBlock));

        getOrCreateBuilder(LOGS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_log") || b.getRegistryName().getPath().endsWith("wood")));
        getOrCreateBuilder(PLANKS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_planks")));
        getOrCreateBuilder(WOODEN_FENCES).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_fence")));
        getOrCreateBuilder(WOODEN_DOORS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_door")));
        getOrCreateBuilder(WOODEN_STAIRS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_planks_stairs")));
        getOrCreateBuilder(WOODEN_SLABS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_planks_slab")));
        getOrCreateBuilder(WOODEN_TRAPDOORS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_trapdoor")));
        getOrCreateBuilder(WOODEN_PRESSURE_PLATES).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_planks_pressure_plate")));
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