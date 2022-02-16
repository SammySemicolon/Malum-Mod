package com.sammy.malum.core.systems.worldgen;

import com.sammy.malum.core.helper.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class MalumFiller {
    public ArrayList<BlockStateEntry> entries = new ArrayList<>();

    public MalumFiller() {

    }

    public void fill(WorldGenLevel level, boolean safetyCheck) {
        for (BlockStateEntry entry : entries) {
            if (safetyCheck && !entry.canPlace(level)) {
                continue;
            }
            entry.place(level);
        }
    }

    public void replaceAt(int index, BlockStateEntry entry) {
        entries.set(index, entry);
    }

    public static class BlockStateEntry {
        public final BlockState state;
        public final BlockPos pos;

        public BlockStateEntry(BlockState state, BlockPos pos) {
            this.state = state;
            this.pos = pos;
        }

        public boolean canPlace(WorldGenLevel level) {
            return canPlace(level, pos);
        }

        public boolean canPlace(WorldGenLevel level, BlockPos pos) {
            if (level.isOutsideBuildHeight(pos)) {
                return false;
            }
            BlockState state = level.getBlockState(pos);
            return level.isEmptyBlock(pos) || state.getMaterial().isReplaceable();
        }

        public void place(WorldGenLevel level) {
            level.setBlock(pos, state, 3);
            if (level instanceof Level) {
                BlockHelper.updateState((Level) level, pos);
            }
        }
    }
}