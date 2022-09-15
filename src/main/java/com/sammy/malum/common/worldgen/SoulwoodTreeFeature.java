package com.sammy.malum.common.worldgen;

import com.sammy.malum.common.block.MalumLeavesBlock;
import com.sammy.malum.common.block.MalumSaplingBlock;
import com.sammy.malum.core.setup.content.block.BlockRegistry;
import com.sammy.malum.core.setup.content.block.BlockTagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.BlockStateEntry;

import java.util.List;
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

        LodestoneBlockFiller treeFiller = new LodestoneBlockFiller(false);
        LodestoneBlockFiller blightFiller = new LodestoneBlockFiller(false);
        LodestoneBlockFiller leavesFiller = new LodestoneBlockFiller(true);

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
        createBlight(level, blightFiller, BlockRegistry.BLIGHTED_SPIRE, level.getRandom(),pos.below(),blightSize, 0);
        for (Direction direction : directions) {
            BlockPos relative = pos.below().relative(direction).offset(rand.nextInt(4), 0, rand.nextInt(4));
            createBlight(level, blightFiller, BlockRegistry.BLIGHTED_WEED, level.getRandom(),relative,blightSize, 0.1f);
            Direction otherDirection = directions[rand.nextInt(directions.length)];
            if (otherDirection.equals(direction)) {
                continue;
            }
            createBlight(level, blightFiller, BlockRegistry.BLIGHTED_WEED, level.getRandom(),relative.relative(otherDirection, 3+rand.nextInt(3)),blightSize-1, 0.1f);
            blightSize--;
        }
        int sapBlockCount = minimumSapBlockCount + rand.nextInt(extraSapBlockCount + 1);
        int[] sapBlockIndexes = DataHelper.nextInts(sapBlockCount, treeFiller.entries.size());
        for (Integer index : sapBlockIndexes) {
            treeFiller.replace(index, e -> e.replaceState(BlockHelper.getBlockStateWithExistingProperties(e.state, BlockRegistry.EXPOSED_SOULWOOD_LOG.get().defaultBlockState())));
        }
        blightFiller.fill(level);
        treeFiller.fill(level);
        leavesFiller.fill(level);
        return true;
    }

    public static boolean downwardsTrunk(WorldGenLevel level, LodestoneBlockFiller filler, BlockPos pos) {
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

    public static void makeLeafBlob(LodestoneBlockFiller filler, Random rand, BlockPos pos) {
        makeLeafSlice(filler, rand, pos, 1, 0);
        makeLeafSlice(filler, rand, pos.above(1), 2, 1);
        makeLeafSlice(filler, rand, pos.above(2), 3, 2);
        makeLeafSlice(filler, rand, pos.above(3), 3, 3);
        makeLeafSlice(filler, rand, pos.above(3), 3, 3 - rand.nextInt(1));
        makeLeafSlice(filler, rand, pos.above(4), 3, 3 - rand.nextInt(1));
        makeLeafSlice(filler, rand, pos.above(5), 2, 3 + rand.nextInt(1));
        makeLeafSlice(filler, rand, pos.above(6), 1, 4);
    }

    public static void makeLeafSlice(LodestoneBlockFiller filler, Random rand, BlockPos pos, int leavesSize, int leavesColor) {
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

    public static List<BlockStateEntry> createBlight(LevelAccessor level, LodestoneBlockFiller filler, RegistryObject<Block> plant, Random rand, BlockPos pos, int size, float growths) {
        if (growths < 1f) {
            growths = level.getRandom().nextFloat() < growths ? 1 : 0;
        }
        if (level.getBlockState(pos).is(MOSS_REPLACEABLE)) {
            filler.entries.add(new BlockStateEntry(BlockRegistry.BLIGHTED_SOIL.get().defaultBlockState(), pos));
        }
        if (level.getBlockState(pos.below()).is(DIRT)) {
            filler.entries.add(new BlockStateEntry(BlockRegistry.BLIGHTED_EARTH.get().defaultBlockState(), pos.below()));
        }
        LodestoneBlockFiller plantFiller = new LodestoneBlockFiller(false);
        int plantCooldown = 2;
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                if (Math.abs(x) == size && Math.abs(z) == size) {
                    continue;
                }
                BlockPos grassPos = pos.offset(x, 0, z);
                BlockPos.MutableBlockPos mutable = grassPos.mutable();
                int verticalRange = 4;
                for (int i1 = 0; level.isStateAtPosition(mutable, (s) -> !s.isAir()) && i1 < verticalRange; ++i1) {
                    mutable.move(Direction.UP);
                }
                for (int k = 0; level.isStateAtPosition(mutable, BlockBehaviour.BlockStateBase::isAir) && k < verticalRange; ++k) {
                    mutable.move(Direction.DOWN);
                }
                do {
                    BlockState plantState = level.getBlockState(mutable);
                    if (plantState.isAir()) {
                        break;
                    }
                    if (plantState.is(BlockTagRegistry.BLIGHTED_PLANTS)) {
                        break;
                    }
                    if ((plantState.getMaterial().isReplaceable() || plantState.is(REPLACEABLE_PLANTS) || plantState.is(FLOWERS))) {
                        filler.entries.add(new BlockStateEntry(Blocks.AIR.defaultBlockState(), mutable.immutable()));
                        mutable.move(Direction.DOWN);
                    } else {
                        break;
                    }
                }
                while (true);
                grassPos = mutable.immutable();
                if (level.getBlockState(grassPos).is(MOSS_REPLACEABLE)) {
                    filler.entries.add(new BlockStateEntry(BlockRegistry.BLIGHTED_SOIL.get().defaultBlockState(), grassPos));
                    if (level.getBlockState(grassPos.below()).is(DIRT)) {
                        filler.entries.add(new BlockStateEntry(BlockRegistry.BLIGHTED_EARTH.get().defaultBlockState(), grassPos.below()));
                    }
                    if (plantCooldown <= 0 && rand.nextFloat() < 0.4f) {
                        BlockPos plantPos = grassPos.above();
                        BlockState blockState = level.getBlockState(plantPos);
                        if (blockState.isAir() && !blockState.is(BlockTagRegistry.BLIGHTED_PLANTS)) {
                            plantFiller.entries.add(new BlockStateEntry(plant.get().defaultBlockState(), plantPos));
                        }
                        plantCooldown = 2;
                    }
                    plantCooldown--;
                }
            }
        }
        if (!plantFiller.entries.isEmpty()) {
            while (growths >= 1) {
                plantFiller.replace(level.getRandom().nextInt(plantFiller.entries.size()), s -> s.replaceState(BlockRegistry.SOULWOOD_GROWTH.get().defaultBlockState()));
                growths--;
            }
            if (growths < 1 && rand.nextFloat() < growths) {
                plantFiller.replace(level.getRandom().nextInt(plantFiller.entries.size()), s -> s.replaceState(BlockRegistry.SOULWOOD_GROWTH.get().defaultBlockState()));

            }
            filler.entries.addAll(plantFiller.entries);
        }
        return filler.entries;
    }

    public static boolean canPlace(WorldGenLevel level, BlockPos pos) {
        if (level.isOutsideBuildHeight(pos)) {
            return false;
        }
        BlockState state = level.getBlockState(pos);
        return state.getBlock() instanceof MalumSaplingBlock || level.isEmptyBlock(pos) || state.getMaterial().isReplaceable();
    }
}