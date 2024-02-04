package com.sammy.malum.common.worldgen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sammy.malum.common.block.blight.*;
import com.sammy.malum.common.block.nature.MalumLeavesBlock;
import com.sammy.malum.common.block.nature.MalumSaplingBlock;
import com.sammy.malum.registry.common.block.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.*;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.BlockStateEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class RunewoodTreeFeature extends Feature<NoneFeatureConfiguration> {
    public RunewoodTreeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    private static int getSapBlockCount(RandomSource random) {
        return Mth.nextInt(random, 2, 3);
    }

    private static int getTrunkHeight(RandomSource random) {
        return Mth.nextInt(random, 7, 10);
    }
    private static int getSideTrunkHeight(RandomSource random) {
        return Mth.nextInt(random, 0, 2);
    }

    private static int getDownwardsBranchOffset(RandomSource random) {
        return Mth.nextInt(random, 2, 4);
    }
    private static int getBranchEndOffset(RandomSource random) {
        return Mth.nextInt(random, 2, 3);
    }
    private static int getBranchHeight(RandomSource random) {
        return Mth.nextInt(random, 3, 5);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        var rand = context.random();
        if (level.isEmptyBlock(pos.below()) || !BlockRegistry.RUNEWOOD_SAPLING.get().defaultBlockState().canSurvive(level, pos)) {
            return false;
        }
        BlockState defaultLog = BlockRegistry.RUNEWOOD_LOG.get().defaultBlockState();

        LodestoneBlockFiller treeFiller = new LodestoneBlockFiller(false);
        LodestoneBlockFiller leavesFiller = new LodestoneBlockFiller(true);

        int trunkHeight = getTrunkHeight(rand);
        int sapBlockCount = getSapBlockCount(rand);
        BlockPos trunkTop = pos.above(trunkHeight);
        Direction[] directions = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

        for (int i = 0; i <= trunkHeight; i++) //trunk placement
        {
            BlockPos trunkPos = pos.above(i);
            if (canPlace(level, trunkPos)) {
                treeFiller.getEntries().put(trunkPos, new BlockStateEntry(defaultLog));
            } else {
                return false;
            }
        }

        makeLeafBlob(leavesFiller, trunkTop);
        for (Direction direction : directions) //side trunk placement
        {
            int sideTrunkHeight = getSideTrunkHeight(rand);
            for (int i = 0; i < sideTrunkHeight; i++) {
                BlockPos sideTrunkPos = pos.relative(direction).above(i);
                if (canPlace(level, sideTrunkPos)) {
                    treeFiller.getEntries().put(sideTrunkPos, new BlockStateEntry(defaultLog));
                } else {
                    return false;
                }
            }
            downwardsTrunk(level, treeFiller, pos.relative(direction));
        }
        for (Direction direction : directions) //tree top placement
        {
            int downwardsBranchOffset = getDownwardsBranchOffset(rand);
            int branchEndOffset = getBranchEndOffset(rand);
            BlockPos branchStartPos = trunkTop.below(downwardsBranchOffset).relative(direction, branchEndOffset);
            for (int i = 0; i < branchEndOffset; i++) //branch connection placement
            {
                BlockPos branchConnectionPos = branchStartPos.relative(direction.getOpposite(), i);
                if (canPlace(level, branchConnectionPos)) {
                    treeFiller.getEntries().put(branchConnectionPos, new BlockStateEntry(defaultLog.setValue(RotatedPillarBlock.AXIS, direction.getAxis())));
                } else {
                    return false;
                }
            }
            int branchHeight = getBranchHeight(rand);
            for (int i = 0; i < branchHeight; i++) //branch placement
            {
                BlockPos branchPos = branchStartPos.above(i);
                if (canPlace(level, branchPos)) {
                    treeFiller.getEntries().put(branchPos, new BlockStateEntry(defaultLog));
                } else {
                    return false;
                }
            }
            makeLeafBlob(leavesFiller, branchStartPos.above(1));
        }
        ArrayList<BlockPos> sapBlockPositions = new ArrayList<>(treeFiller.getEntries().keySet());
        Collections.shuffle(sapBlockPositions);
        for (BlockPos blockPos : sapBlockPositions.subList(0, sapBlockCount)) {
            treeFiller.replace(blockPos, e -> new BlockStateEntry(BlockHelper.getBlockStateWithExistingProperties(e.getState(), BlockRegistry.EXPOSED_RUNEWOOD_LOG.get().defaultBlockState())));
        }

        treeFiller.fill(level);
        leavesFiller.fill(level);
        updateLeaves(level, treeFiller.getEntries().keySet());
        return true;
    }

    public static void downwardsTrunk(WorldGenLevel level, LodestoneBlockFiller filler, BlockPos pos) {
        int i = 0;
        do {
            i++;
            BlockPos trunkPos = pos.below(i);
            if (canPlace(level, trunkPos)) {
                filler.getEntries().put(trunkPos, new BlockStateEntry(BlockRegistry.RUNEWOOD_LOG.get().defaultBlockState()));
            } else {
                break;
            }
            if (i > level.getMaxBuildHeight()) {
                break;
            }
        }
        while (true);
    }

    public static void makeLeafBlob(LodestoneBlockFiller filler, BlockPos pos) {
        final BlockPos.MutableBlockPos mutable = pos.mutable();
        int[] leafSizes = new int[]{1, 2, 2, 2, 1};
        int[] leafColors = new int[]{0, 1, 2, 3, 4};
        mutable.move(Direction.DOWN, 1);
        for (int i = 0; i < 2; i++) {
            int size = leafSizes[i];
            int color = leafColors[i];
            final BlockState state = BlockRegistry.HANGING_RUNEWOOD_LEAVES.get().defaultBlockState().setValue(MalumLeavesBlock.COLOR, color);
            makeLeafSlice(filler, mutable, size, state);
            mutable.move(Direction.UP);
        }
        mutable.move(Direction.DOWN, 1);
        for (int i = 0; i < 5; i++) {
            int size = leafSizes[i];
            int color = leafColors[i];
            final BlockState state = BlockRegistry.RUNEWOOD_LEAVES.get().defaultBlockState().setValue(MalumLeavesBlock.COLOR, color);
            makeLeafSlice(filler, mutable, size, state);
            mutable.move(Direction.UP);
        }
    }

    public static void makeLeafSlice(LodestoneBlockFiller filler, BlockPos.MutableBlockPos pos, int leavesSize, BlockState state) {
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

    public static void updateLeaves(LevelAccessor pLevel, Set<BlockPos> logPositions) {
        List<Set<BlockPos>> list = Lists.newArrayList();
        for (int j = 0; j < 6; ++j) {
            list.add(Sets.newHashSet());
        }

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (BlockPos pos : Lists.newArrayList(logPositions)) {
            for (Direction direction : Direction.values()) {
                mutable.setWithOffset(pos, direction);
                if (!logPositions.contains(mutable)) {
                    BlockState blockstate = pLevel.getBlockState(mutable);
                    if (blockstate.hasProperty(MalumLeavesBlock.DISTANCE)) {
                        list.get(0).add(mutable.immutable());
                        pLevel.setBlock(mutable, blockstate.setValue(MalumLeavesBlock.DISTANCE, 1), 19);
                    }
                }
            }
        }

        for (int l = 1; l < 6; ++l) {
            Set<BlockPos> set = list.get(l - 1);
            Set<BlockPos> set1 = list.get(l);

            for (BlockPos pos : set) {
                for (Direction direction1 : Direction.values()) {
                    mutable.setWithOffset(pos, direction1);
                    if (!set.contains(mutable) && !set1.contains(mutable)) {
                        BlockState blockstate1 = pLevel.getBlockState(mutable);
                        if (blockstate1.hasProperty(MalumLeavesBlock.DISTANCE)) {
                            int k = blockstate1.getValue(MalumLeavesBlock.DISTANCE);
                            if (k > l + 1) {
                                BlockState blockstate2 = blockstate1.setValue(MalumLeavesBlock.DISTANCE, l + 1);
                                pLevel.setBlock(mutable, blockstate2, 19);
                                set1.add(mutable.immutable());
                            }
                        }
                    }
                }
            }
        }
    }
}