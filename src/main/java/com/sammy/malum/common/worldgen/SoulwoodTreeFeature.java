package com.sammy.malum.common.worldgen;

import com.sammy.malum.common.block.MalumLeavesBlock;
import com.sammy.malum.common.block.MalumSaplingBlock;
import com.sammy.malum.core.setup.content.block.BlockRegistry;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.helpers.DataHelper;
import com.sammy.ortus.systems.worldgen.OrtusBlockFiller;
import com.sammy.ortus.systems.worldgen.OrtusBlockFiller.BlockStateEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.RegistryObject;

import java.util.Random;

import static net.minecraft.tags.BlockTags.*;

public class SoulwoodTreeFeature extends Feature<NoneFeatureConfiguration> {
    public SoulwoodTreeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    private static final int minimumSapBlockCount = 3;
    private static final int extraSapBlockCount = 5;

    private static final int minimumTrunkHeight = 12;
    private static final int extraTrunkHeight = 6;
    private static final int minimumTwistCooldown = 3;
    private static final int extraTwistCooldown = 1;
    private static final int minimumTrunkTwists = 2;
    private static final int extraTrunkTwists = 4;
    private static final int minimumSideTrunkHeight = 1;
    private static final int extraSideTrunkHeight = 2;

    private static final int minimumDownwardsBranchOffset = 2;
    private static final int extraDownwardsBranchOffset = 3;
    private static final int minimumBranchCoreOffset = 2;
    private static final int branchCoreOffsetExtra = 3;
    private static final int minimumBranchTwists = 0;
    private static final int extraBranchTwists = 2;
    private static final int minimumBranchHeight = 3;
    private static final int branchHeightExtra = 2;

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        Random rand = context.random();
        if (level.isEmptyBlock(pos.below()) || !BlockRegistry.SOULWOOD_GROWTH.get().defaultBlockState().canSurvive(level, pos)) {
            return false;
        }
        BlockState defaultLog = BlockRegistry.SOULWOOD_LOG.get().defaultBlockState();
        BlockState blightedLog = BlockRegistry.BLIGHTED_SOULWOOD.get().defaultBlockState();

        OrtusBlockFiller treeFiller = new OrtusBlockFiller(false);
        OrtusBlockFiller blightFiller = new OrtusBlockFiller(false);
        OrtusBlockFiller leavesFiller = new OrtusBlockFiller(true);

        int trunkHeight = minimumTrunkHeight + rand.nextInt(extraTrunkHeight + 1);
        Direction[] directions = new Direction[]{Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST};
        int twistDirection = rand.nextInt(directions.length);
        int twistCooldown = 3;
        int twists = minimumTrunkTwists + rand.nextInt(extraTrunkTwists + 1);
        int lowestPossibleBranch = minimumDownwardsBranchOffset + extraDownwardsBranchOffset;
        BlockPos twistedPos = pos;
        for (int i = 0; i <= trunkHeight; i++) //trunk placement
        {
            if (i < trunkHeight-lowestPossibleBranch) {
                if (twistCooldown == 0 && twists != 0) {
                    twistCooldown = minimumTwistCooldown + rand.nextInt(extraTwistCooldown + 1);
                    BlockPos trunkPos = twistedPos.above(i);
                    if (canPlace(level, trunkPos)) {
                        treeFiller.entries.add(new BlockStateEntry(defaultLog, trunkPos));
                        twistCooldown--;
                    } else {
                        return false;
                    }
                    twistedPos = twistedPos.relative(directions[twistDirection]);
                    if (rand.nextFloat() < 0.85f) {
                        twistDirection++;
                        if (twistDirection == 4) {
                            twistDirection = 0;
                        }
                    }
                    twists--;
                }
            }
            BlockPos trunkPos = twistedPos.above(i);
            if (canPlace(level, trunkPos)) {
                treeFiller.entries.add(new BlockStateEntry(i == 0 ? blightedLog : defaultLog, trunkPos));
                twistCooldown--;
            } else {
                return false;
            }
        }
        BlockPos trunkTop = twistedPos.above(trunkHeight);

        makeLeafBlob(leavesFiller, rand, trunkTop);
        for (Direction direction : directions) //side trunk placement
        {
            int blightedIndex = 0;
            int sideTrunkHeight = minimumSideTrunkHeight + rand.nextInt(extraSideTrunkHeight + 1);
            for (int i = 0; i < sideTrunkHeight; i++) {
                BlockPos sideTrunkPos = pos.relative(direction).above(i);
                if (canPlace(level, sideTrunkPos)) {
                    if (i == 0) {
                        blightedIndex = treeFiller.entries.size();
                    }
                    treeFiller.entries.add(new BlockStateEntry(i == 0 ? blightedLog : defaultLog, sideTrunkPos));
                } else {
                    return false;
                }
            }
            boolean success = downwardsTrunk(level, treeFiller, pos.relative(direction));
            if (success) {
                treeFiller.replace(blightedIndex, e -> e.replaceState(defaultLog));
            }
        }
        for (Direction direction : directions) //tree top placement
        {
            int branchCoreOffset = minimumDownwardsBranchOffset + rand.nextInt(extraDownwardsBranchOffset + 1);
            int branchOffset = minimumBranchCoreOffset + rand.nextInt(branchCoreOffsetExtra + 1);
            BlockPos branchStartPos = trunkTop.below(branchCoreOffset);
            twistCooldown = 1;
            twists = minimumBranchTwists + rand.nextInt(extraBranchTwists + 1);
            for (int i = 1; i < branchOffset; i++) //branch connection placement
            {
                BlockPos branchConnectionPos = branchStartPos.relative(direction, i);
                if (twistCooldown == 0 && twists != 0) {
                    twistCooldown = minimumTwistCooldown + rand.nextInt(extraTwistCooldown + 1);
                    BlockPos offsetPos = branchConnectionPos.above();
                    if (canPlace(level, offsetPos)) {
                        treeFiller.entries.add(new BlockStateEntry(defaultLog.setValue(RotatedPillarBlock.AXIS, direction.getAxis()), offsetPos));
                    } else {
                        return false;
                    }
                    branchStartPos = branchStartPos.above();
                    twists--;
                }
                if (canPlace(level, branchConnectionPos)) {
                    treeFiller.entries.add(new BlockStateEntry(defaultLog.setValue(RotatedPillarBlock.AXIS, direction.getAxis()), branchConnectionPos));
                    twistCooldown--;
                } else {
                    return false;
                }
            }
            BlockPos branchEndPos = branchStartPos.relative(direction, branchOffset);
            int branchHeight = minimumBranchHeight + rand.nextInt(branchHeightExtra + 1);
            for (int i = 0; i < branchHeight; i++) //branch placement
            {
                BlockPos branchPos = branchEndPos.above(i);
                if (canPlace(level, branchPos)) {
                    treeFiller.entries.add(new BlockStateEntry(defaultLog, branchPos));
                } else {
                    return false;
                }
            }
            makeLeafBlob(leavesFiller, rand, branchEndPos.above(1));
        }
        int blightSize = 3+rand.nextInt(3);
        createBlight(level, blightFiller, BlockRegistry.BLIGHTED_SPIRE, level.getRandom(),pos.below(),blightSize);
        for (Direction direction : directions) {
            BlockPos relative = pos.below().relative(direction).offset(rand.nextInt(4), 0, rand.nextInt(4));
            createBlight(level, blightFiller, BlockRegistry.BLIGHTED_WEED, level.getRandom(),relative,blightSize);
            Direction otherDirection = directions[rand.nextInt(directions.length)];
            if (otherDirection.equals(direction)) {
                continue;
            }
            createBlight(level, blightFiller, BlockRegistry.BLIGHTED_WEED, level.getRandom(),relative.relative(otherDirection, 3+rand.nextInt(3)),blightSize-1);
            blightSize--;
        }
        int sapBlockCount = minimumSapBlockCount + rand.nextInt(extraSapBlockCount + 1);
        int[] sapBlockIndexes = DataHelper.nextInts(rand, sapBlockCount, treeFiller.entries.size());
        for (Integer index : sapBlockIndexes) {
            treeFiller.replace(index, e -> e.replaceState(BlockHelper.getBlockStateWithExistingProperties(e.state, BlockRegistry.EXPOSED_SOULWOOD_LOG.get().defaultBlockState())));
        }
        blightFiller.fill(level);
        treeFiller.fill(level);
        leavesFiller.fill(level);
        return true;
    }

    public static boolean downwardsTrunk(WorldGenLevel level, OrtusBlockFiller filler, BlockPos pos) {
        BlockState defaultLog = BlockRegistry.SOULWOOD_LOG.get().defaultBlockState();
        BlockState blightedLog = BlockRegistry.BLIGHTED_SOULWOOD.get().defaultBlockState();
        int i = 0;
        do {
            i++;
            BlockPos trunkPos = pos.below(i);
            if (canPlace(level, trunkPos)) {
                boolean blighted = !canPlace(level, trunkPos.below());
                filler.entries.add(new BlockStateEntry(blighted ? blightedLog : defaultLog, trunkPos));
            } else {
                break;
            }
            if (i > level.getMaxBuildHeight()) {
                break;
            }
        }
        while (true);
        return i > 1;
    }

    public static void makeLeafBlob(OrtusBlockFiller filler, Random rand, BlockPos pos) {
        makeLeafSlice(filler, rand, pos, 1, 0);
        makeLeafSlice(filler, rand, pos.above(1), 2, 1);
        makeLeafSlice(filler, rand, pos.above(2), 3, 2);
        makeLeafSlice(filler, rand, pos.above(3), 3, 3);
        makeLeafSlice(filler, rand, pos.above(3), 3, 3 - rand.nextInt(1));
        makeLeafSlice(filler, rand, pos.above(4), 3, 3 - rand.nextInt(1));
        makeLeafSlice(filler, rand, pos.above(5), 2, 3 + rand.nextInt(1));
        makeLeafSlice(filler, rand, pos.above(6), 1, 4);
    }

    public static void makeLeafSlice(OrtusBlockFiller filler, Random rand, BlockPos pos, int leavesSize, int leavesColor) {
        for (int x = -leavesSize; x <= leavesSize; x++) {
            for (int z = -leavesSize; z <= leavesSize; z++) {
                if (Math.abs(x) == leavesSize && Math.abs(z) == leavesSize) {
                    continue;
                }
                BlockPos leavesPos = new BlockPos(pos).offset(x, 0, z);
                int offsetColor = leavesColor + Mth.nextInt(rand, leavesColor == 0 ? 0 : -1, leavesColor == 4 ? 0 : 1);
                filler.entries.add(new BlockStateEntry(BlockRegistry.SOULWOOD_LEAVES.get().defaultBlockState().setValue(LeavesBlock.DISTANCE, 1).setValue(MalumLeavesBlock.COLOR, offsetColor), leavesPos));
            }
        }
    }

    public static void createBlight(WorldGenLevel level, OrtusBlockFiller filler, RegistryObject<Block> plant, Random rand, BlockPos pos, int size) {
        if (level.getBlockState(pos).is(BlockTags.MOSS_REPLACEABLE)) {
            filler.entries.add(new BlockStateEntry(BlockRegistry.BLIGHTED_SOIL.get().defaultBlockState(), pos));
        }
        int plantCooldown = 2;
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                if (Math.abs(x) == size && Math.abs(z) == size) {
                    continue;
                }
                BlockPos grassPos = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos.offset(x, 0, z)).below();
                if (grassPos.getY() < pos.getY() - 6) {
                    continue;
                }
                do {
                    if (level.getBlockState(grassPos).is(REPLACEABLE_PLANTS) || level.getBlockState(grassPos).is(FLOWERS)) {
                        boolean canSurvive = plant.get().defaultBlockState().canSurvive(level, grassPos);
                        filler.entries.add(new BlockStateEntry(canSurvive ? plant.get().defaultBlockState() : Blocks.AIR.defaultBlockState(), grassPos));
                        grassPos = grassPos.below();
                    } else {
                        break;
                    }
                }
                while (true);
                if (level.getBlockState(grassPos).is(BlockTags.MOSS_REPLACEABLE)) {
                    filler.entries.add(new BlockStateEntry(BlockRegistry.BLIGHTED_SOIL.get().defaultBlockState(), grassPos));
                    if (plantCooldown <= 0 && rand.nextFloat() < 0.4f) {
                        filler.entries.add(new BlockStateEntry(plant.get().defaultBlockState(), grassPos.above()));
                        plantCooldown = 2;
                    }
                    plantCooldown--;
                }
            }
        }
    }

    public static boolean canPlace(WorldGenLevel level, BlockPos pos) {
        if (level.isOutsideBuildHeight(pos)) {
            return false;
        }
        BlockState state = level.getBlockState(pos);
        return state.getBlock() instanceof MalumSaplingBlock || level.isEmptyBlock(pos) || state.getMaterial().isReplaceable();
    }
}