package com.sammy.malum.common.worldgen;

import com.google.common.collect.ImmutableList;
import com.sammy.malum.common.block.nature.MalumLeavesBlock;
import com.sammy.malum.registry.common.block.BlockRegistry;
import com.sammy.malum.registry.common.block.BlockTagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.BlockStateEntry;

import java.util.*;

import static com.sammy.malum.common.worldgen.RunewoodTreeFeature.canPlace;
import static com.sammy.malum.common.worldgen.RunewoodTreeFeature.updateLeaves;
import static net.minecraft.tags.BlockTags.*;

public class SoulwoodTreeFeature extends Feature<NoneFeatureConfiguration> {
    public SoulwoodTreeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    private static final PerlinSimplexNoise BLIGHT_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(1234L)), ImmutableList.of(0));

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
    private static final int minimumBranchHeight = 5;
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
            if (i < trunkHeight - lowestPossibleBranch) {
                if (twistCooldown == 0 && twists != 0) {
                    twistCooldown = minimumTwistCooldown + rand.nextInt(extraTwistCooldown + 1);
                    BlockPos trunkPos = twistedPos.above(i);
                    if (canPlace(level, trunkPos)) {
                        treeFiller.getEntries().put(trunkPos, new BlockStateEntry(defaultLog));
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
                treeFiller.getEntries().put(trunkPos, new BlockStateEntry(i == 0 ? blightedLog : defaultLog));
                twistCooldown--;
            } else {
                return false;
            }
        }
        BlockPos trunkTop = twistedPos.above(trunkHeight);

        makeLeafBlob(leavesFiller, rand, trunkTop);
        for (Direction direction : directions) //side trunk placement
        {
            BlockPos blightedPos = null;
            int sideTrunkHeight = minimumSideTrunkHeight + rand.nextInt(extraSideTrunkHeight + 1);
            for (int i = 0; i < sideTrunkHeight; i++) {
                BlockPos sideTrunkPos = pos.relative(direction).above(i);
                if (canPlace(level, sideTrunkPos)) {
                    if (i == 0) {
                        blightedPos = sideTrunkPos;
                    }
                    treeFiller.getEntries().put(sideTrunkPos, new BlockStateEntry(i == 0 ? blightedLog : defaultLog));
                } else {
                    return false;
                }
            }
            boolean success = downwardsTrunk(level, treeFiller, pos.relative(direction));
            if (success) {
                treeFiller.replace(blightedPos, e -> new BlockStateEntry(defaultLog));
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
                        treeFiller.getEntries().put(offsetPos, new BlockStateEntry(defaultLog.setValue(RotatedPillarBlock.AXIS, direction.getAxis())));
                    } else {
                        return false;
                    }
                    branchStartPos = branchStartPos.above();
                    twists--;
                }
                if (canPlace(level, branchConnectionPos)) {
                    treeFiller.getEntries().put(branchConnectionPos, new BlockStateEntry(defaultLog.setValue(RotatedPillarBlock.AXIS, direction.getAxis())));
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
                    treeFiller.getEntries().put(branchPos, new BlockStateEntry(defaultLog));
                } else {
                    return false;
                }
            }
            makeLeafBlob(leavesFiller, rand, branchEndPos.above(1));
        }
        generateBlight(level, blightFiller, pos.below(), 6);

        int sapBlockCount = minimumSapBlockCount + rand.nextInt(extraSapBlockCount + 1);
        ArrayList<BlockPos> sapBlockPositions = new ArrayList<>(treeFiller.getEntries().keySet());
        Collections.shuffle(sapBlockPositions);
        for (BlockPos blockPos : sapBlockPositions.subList(0, sapBlockCount)) {
            treeFiller.replace(blockPos, e -> e.getState().getBlock().equals(BlockRegistry.BLIGHTED_SOULWOOD.get()) ? e : new BlockStateEntry(BlockHelper.getBlockStateWithExistingProperties(e.getState(), BlockRegistry.EXPOSED_SOULWOOD_LOG.get().defaultBlockState())));
        }

        blightFiller.fill(level);
        treeFiller.fill(level);
        leavesFiller.fill(level);
        updateLeaves(level, treeFiller.getEntries().keySet());
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
                filler.getEntries().put(trunkPos, new BlockStateEntry(blighted ? blightedLog : defaultLog));
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
                filler.getEntries().put(leavesPos, new BlockStateEntry(BlockRegistry.SOULWOOD_LEAVES.get().defaultBlockState().setValue(MalumLeavesBlock.COLOR, offsetColor)));
            }
        }
    }

    public static Map<Integer, Double> generateBlight(ServerLevelAccessor level, LodestoneBlockFiller filler, BlockPos pos, int radius) {
        Map<Integer, Double> noiseValues = new HashMap<>();
        for (int i = 0; i <= 360; i++) {
            noiseValues.put(i, BLIGHT_NOISE.getValue(pos.getX() + pos.getZ() + i * 0.02f, pos.getY() / 0.05f, true) * 2.5f);
        }
        generateBlight(level, filler, noiseValues, pos, radius);
        return noiseValues;
    }

    public static void generateBlight(ServerLevelAccessor level, LodestoneBlockFiller filler, Map<Integer, Double> noiseValues, BlockPos pos, int radius) {
        generateBlight(level, filler, pos, radius*2, radius, noiseValues);
        filler.fill(level);
    }

    public static void generateBlight(ServerLevelAccessor level, LodestoneBlockFiller filler, BlockPos center, int radius, int effectiveRadius, Map<Integer, Double> noiseValues) {
        int x = center.getX();
        int z = center.getZ();
        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
        int saplingsPlaced = 0;
        Vec3 lastSaplingPos = null;
        for (int i = 0; i < radius * 2 + 1; i++) {
            for (int j = 0; j < radius * 2 + 1; j++) {
                int xp = x + i - radius;
                int zp = z + j - radius;
                blockPos.set(xp, center.getY(), zp);
                double theta = 180 + 180 / Math.PI * Math.atan2(x - xp, z - zp);
                double naturalNoiseValue = noiseValues.get(Mth.floor(theta));
                if (naturalNoiseValue > 1f) {
                    naturalNoiseValue *= naturalNoiseValue;
                }
                int floor = (int) Math.floor(pointDistancePlane(xp, zp, x, z));
                if (floor <= (effectiveRadius + Math.floor(naturalNoiseValue) - 1)) {
                    int verticalRange = 4;
                    for (int i1 = 0; level.isStateAtPosition(blockPos, (s) -> !s.isAir()) && i1 < verticalRange; ++i1) {
                        blockPos.move(Direction.UP);
                    }
                    for (int k = 0; level.isStateAtPosition(blockPos, BlockBehaviour.BlockStateBase::isAir) && k < verticalRange; ++k) {
                        blockPos.move(Direction.DOWN);
                    }
                    do {
                        BlockState plantState = level.getBlockState(blockPos);
                        if (plantState.isAir()) {
                            break;
                        }
                        if (plantState.is(BlockTagRegistry.BLIGHTED_PLANTS)) {
                            break;
                        }
                        if ((plantState.getMaterial().isReplaceable() || plantState.is(REPLACEABLE_PLANTS) || plantState.is(FLOWERS))) {
                            filler.getEntries().put(blockPos.immutable(), new BlockStateEntry(Blocks.AIR.defaultBlockState()));
                            blockPos.move(Direction.DOWN);
                        } else {
                            break;
                        }
                    }
                    while (true);

                    BlockPos immutable = blockPos.immutable();
                    if (level.getBlockState(immutable).is(MOSS_REPLACEABLE)) {
                        filler.getEntries().put(immutable, new BlockStateEntry(BlockRegistry.BLIGHTED_SOIL.get().defaultBlockState()));
                        if (level.getBlockState(immutable.below()).is(DIRT)) {
                            filler.getEntries().put(immutable.below(), new BlockStateEntry(BlockRegistry.BLIGHTED_EARTH.get().defaultBlockState()));
                        }
                        if (level.getRandom().nextFloat() < 0.25f) {
                            BlockPos plantPos = immutable.above();
                            BlockState blockState = level.getBlockState(plantPos);
                            if (naturalNoiseValue > 2.5f) {
                                if (lastSaplingPos == null || lastSaplingPos.distanceToSqr(plantPos.getX(), plantPos.getY(), plantPos.getZ()) > 5) {
                                    if (BlockHelper.fromBlockPos(center).distanceToSqr(plantPos.getX(), plantPos.getY(), plantPos.getZ()) > 4) {
                                        if (level.getRandom().nextFloat() < 0.5f / (Math.pow(saplingsPlaced + 1, 2))) {
                                            filler.getEntries().put(plantPos, new BlockStateEntry(BlockRegistry.SOULWOOD_GROWTH.get().defaultBlockState()));
                                            lastSaplingPos = new Vec3(plantPos.getX(), plantPos.getY(), plantPos.getZ());
                                            saplingsPlaced++;
                                        }
                                    }
                                }
                            }
                            if ((blockState.isAir() || blockState.getMaterial().isReplaceable()) && !blockState.is(BlockTagRegistry.BLIGHTED_PLANTS)) {
                                filler.getEntries().put(plantPos, new BlockStateEntry((level.getRandom().nextFloat() < 0.2f ? BlockRegistry.BLIGHTED_TUMOR : BlockRegistry.BLIGHTED_WEED).get().defaultBlockState()));
                            }
                        }
                    }
                }
            }
        }
    }

    public static float pointDistancePlane(double x1, double z1, double x2, double z2) {
        return (float) Math.hypot(x1 - x2, z1 - z2);
    }
}