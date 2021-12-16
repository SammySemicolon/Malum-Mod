package com.sammy.malum.core.systems.worldgen;

import com.sammy.malum.MalumHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class MalumFiller
{
    public ArrayList<BlockStateEntry> entries = new ArrayList<>();

    public MalumFiller()
    {

    }

    public void fill(WorldGenLevel level, boolean safetyCheck)
    {
        for (BlockStateEntry entry : entries)
        {
            if (safetyCheck && !entry.canPlace(level))
            {
                continue;
            }
            level.setBlock(entry.pos, entry.state, 3);
            entry.additionalPlacement(level);
            if (level instanceof Level)
            {
                MalumHelper.updateState((Level) level, entry.pos);
            }
        }
    }
    public void replaceAt(int index, BlockStateEntry entry)
    {
        entries.set(index, entry);
    }

    public static class BlockStateEntry
    {
        public final BlockState state;
        public final BlockPos pos;

        public BlockStateEntry(BlockState state, BlockPos pos)
        {
            this.state = state;
            this.pos = pos;
        }

        public boolean canPlace(WorldGenLevel level)
        {
            return canPlace(level, pos);
        }

        public boolean canPlace(WorldGenLevel level, BlockPos pos)
        {
            if (level.isOutsideBuildHeight(pos))
            {
                return false;
            }
            BlockState state = level.getBlockState(pos);
            return level.isEmptyBlock(pos) || state.getMaterial().isReplaceable();
        }

        public boolean canPlace(WorldGenLevel level, BlockPos pos, Block block)
        {
            if (level.isOutsideBuildHeight(pos))
            {
                return false;
            }
            BlockState state = level.getBlockState(pos);
            return state.getBlock().equals(block) || level.isEmptyBlock(pos) || state.getMaterial().isReplaceable();
        }

        public void additionalPlacement(WorldGenLevel level)
        {

        }
    }
}