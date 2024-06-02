package com.sammy.malum.common.worldgen.tree;

import com.sammy.malum.common.block.blight.BlightedGrowthBlock;
import com.sammy.malum.common.block.nature.MalumLeavesBlock;
import com.sammy.malum.common.block.nature.MalumSaplingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.BlockStateEntry;

import java.util.ArrayList;
import java.util.Collections;

import static com.sammy.malum.common.worldgen.WorldgenHelper.DIRECTIONS;
import static com.sammy.malum.common.worldgen.WorldgenHelper.updateLeaves;

public class RunewoodTreeFeature extends Feature<RunewoodTreeConfiguration> {

    public RunewoodTreeFeature() {
        super(RunewoodTreeConfiguration.CODEC);
    }

    private int getSapBlockCount(RandomSource random) {
        return Mth.nextInt(random, 2, 3);
    }

    private int getTrunkHeight(RandomSource random) {
        return Mth.nextInt(random, 7, 10);
    }

    private int getSideTrunkHeight(RandomSource random) {
        return Mth.nextInt(random, 0, 2);
    }

    private int getDownwardsBranchOffset(RandomSource random) {
        return Mth.nextInt(random, 2, 4);
    }

    private int getBranchEndOffset(RandomSource random) {
        return Mth.nextInt(random, 2, 3);
    }

    private int getBranchHeight(RandomSource random) {
        return Mth.nextInt(random, 3, 5);
    }

    @Override
    public boolean place(FeaturePlaceContext<RunewoodTreeConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource rand = context.random();
        Block sapling = context.config().sapling;

        if (level.isEmptyBlock(pos.below()) || !sapling.defaultBlockState().canSurvive(level, pos)) {
            return false;
        }
        Block log = context.config().log;
        BlockState logState = log.defaultBlockState();
        Block sapFilledLog = context.config().sapFilledLog;
        BlockState sapFilledLogState = sapFilledLog.defaultBlockState();
        Block leaves = context.config().leaves;
        Block hangingLeaves = context.config().hangingLeaves;

        LodestoneBlockFiller treeFiller = new LodestoneBlockFiller(false);
        LodestoneBlockFiller leavesFiller = new LodestoneBlockFiller(true);
        LodestoneBlockFiller hangingLeavesFiller = new LodestoneBlockFiller(true);

        int trunkHeight = getTrunkHeight(rand);
        int sapBlockCount = getSapBlockCount(rand);
        BlockPos trunkTop = pos.above(trunkHeight);

        for (int i = 0; i <= trunkHeight; i++) //trunk placement
        {
            BlockPos trunkPos = pos.above(i);
            if (canPlace(level, trunkPos)) {
                treeFiller.getEntries().put(trunkPos, new BlockStateEntry(logState));
            } else {
                return false;
            }
        }

        makeLeafBlob(leaves, hangingLeaves, leavesFiller, hangingLeavesFiller, trunkTop);
        for (Direction direction : DIRECTIONS) //side trunk placement
        {
            int sideTrunkHeight = getSideTrunkHeight(rand);
            for (int i = 0; i < sideTrunkHeight; i++) {
                BlockPos sideTrunkPos = pos.relative(direction).above(i);
                if (canPlace(level, sideTrunkPos)) {
                    treeFiller.getEntries().put(sideTrunkPos, new BlockStateEntry(logState));
                } else {
                    return false;
                }
            }
            downwardsTrunk(logState, level, treeFiller, pos.relative(direction));
        }
        for (Direction direction : DIRECTIONS) //tree top placement
        {
            int downwardsBranchOffset = getDownwardsBranchOffset(rand);
            int branchEndOffset = getBranchEndOffset(rand);
            BlockPos branchStartPos = trunkTop.below(downwardsBranchOffset).relative(direction, branchEndOffset);
            for (int i = 0; i < branchEndOffset; i++) //branch connection placement
            {
                BlockPos branchConnectionPos = branchStartPos.relative(direction.getOpposite(), i);
                if (canPlace(level, branchConnectionPos)) {
                    treeFiller.getEntries().put(branchConnectionPos, new BlockStateEntry(logState.setValue(RotatedPillarBlock.AXIS, direction.getAxis())));
                } else {
                    return false;
                }
            }
            int branchHeight = getBranchHeight(rand);
            for (int i = 0; i < branchHeight; i++) //branch placement
            {
                BlockPos branchPos = branchStartPos.above(i);
                if (canPlace(level, branchPos)) {
                    treeFiller.getEntries().put(branchPos, new BlockStateEntry(logState));
                } else {
                    return false;
                }
            }
            makeLeafBlob(leaves, hangingLeaves, leavesFiller, hangingLeavesFiller, branchStartPos.above(1));
        }
        ArrayList<BlockPos> sapBlockPositions = new ArrayList<>(treeFiller.getEntries().keySet());
        Collections.shuffle(sapBlockPositions);
        for (BlockPos blockPos : sapBlockPositions.subList(0, sapBlockCount)) {
            treeFiller.replace(blockPos, e -> new BlockStateEntry(BlockHelper.getBlockStateWithExistingProperties(e.getState(), sapFilledLogState)));
        }

        treeFiller.fill(level);
        leavesFiller.fill(level);
        hangingLeavesFiller.fill(level);
        updateLeaves(level, treeFiller.getEntries().keySet());
        return true;
    }

    public void downwardsTrunk(BlockState log, WorldGenLevel level, LodestoneBlockFiller filler, BlockPos pos) {
        int i = 0;
        do {
            i++;
            BlockPos trunkPos = pos.below(i);
            if (canPlace(level, trunkPos)) {
                filler.getEntries().put(trunkPos, new BlockStateEntry(log));
            } else {
                break;
            }
            if (i > level.getMaxBuildHeight()) {
                break;
            }
        }
        while (true);
    }

    public void makeLeafBlob(Block leaves, Block hangingLeaves, LodestoneBlockFiller filler, LodestoneBlockFiller hangingLeavesFiller, BlockPos pos) {
        final BlockPos.MutableBlockPos mutable = pos.mutable();
        int[] leafSizes = new int[]{1, 2, 2, 2, 1};
        int[] leafColors = new int[]{0, 1, 2, 3, 4};
        mutable.move(Direction.DOWN, 1);
        for (int i = 0; i < 2; i++) {
            int size = leafSizes[i];
            int color = leafColors[i];
            final BlockState state = hangingLeaves.defaultBlockState().setValue(MalumLeavesBlock.COLOR, color);
            makeLeafSlice(hangingLeavesFiller, mutable, size, state);
            mutable.move(Direction.UP);
        }
        mutable.move(Direction.DOWN, 1);
        for (int i = 0; i < 5; i++) {
            int size = leafSizes[i];
            int color = leafColors[i];
            final BlockState state = leaves.defaultBlockState().setValue(MalumLeavesBlock.COLOR, color);
            makeLeafSlice(filler, mutable, size, state);
            mutable.move(Direction.UP);
        }
    }

    public void makeLeafSlice(LodestoneBlockFiller filler, BlockPos.MutableBlockPos pos, int leavesSize, BlockState state) {
        for (int x = -leavesSize; x <= leavesSize; x++) {
            for (int z = -leavesSize; z <= leavesSize; z++) {
                if (Math.abs(x) == leavesSize && Math.abs(z) == leavesSize) {
                    continue;
                }
                filler.getEntries().put(pos.offset(x, 0, z), new BlockStateEntry(state));
            }
        }
    }

    public static boolean canPlace(WorldGenLevel level, BlockPos pos) {
        if (level.isOutsideBuildHeight(pos)) {
            return false;
        }
        BlockState state = level.getBlockState(pos);
        return state.getBlock() instanceof MalumSaplingBlock || state.getBlock() instanceof BlightedGrowthBlock || level.isEmptyBlock(pos) || state.canBeReplaced();
    }
}