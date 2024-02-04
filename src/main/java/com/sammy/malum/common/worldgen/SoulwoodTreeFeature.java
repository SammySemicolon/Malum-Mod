package com.sammy.malum.common.worldgen;

import com.google.common.collect.*;
import com.mojang.datafixers.util.*;
import com.sammy.malum.common.block.blight.*;
import com.sammy.malum.common.block.nature.*;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.synth.*;
import net.minecraft.world.phys.*;
import org.joml.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.worldgen.*;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.*;

import java.lang.Math;
import java.util.*;

import static com.sammy.malum.common.block.blight.ClingingBlightBlock.BlightType.*;
import static com.sammy.malum.common.worldgen.RunewoodTreeFeature.*;
import static net.minecraft.tags.BlockTags.*;

public class SoulwoodTreeFeature extends Feature<NoneFeatureConfiguration> {
    public SoulwoodTreeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }


    private static final Direction[] DIRECTIONS = new Direction[]{Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST};
    private static final PerlinSimplexNoise BLIGHT_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(1234L)), ImmutableList.of(0));

    private static BlockState makeClingingBlight(ClingingBlightBlock.BlightType blightType, Direction direction) {
        return BlockRegistry.CLINGING_BLIGHT.get().defaultBlockState().setValue(ClingingBlightBlock.BLIGHT_TYPE, blightType).setValue(BlockStateProperties.HORIZONTAL_FACING, direction);
    }

    private static int getSapBlockCount(RandomSource random) {
        return Mth.nextInt(random, 5, 7);
    }
    private static int getTrunkHeight(RandomSource random) {
        return Mth.nextInt(random, 12, 18);
    }
    private static int getTwistCooldown(RandomSource random) {
        return Mth.nextInt(random, 3, 5);
    }
    private static int getTrunkTwistAmount(RandomSource random) {
        return Mth.nextInt(random, 2, 6);
    }
    private static int getSideTrunkHeight(RandomSource random) {
        return Mth.nextInt(random, 1, 3);
    }
    private static int getDownwardsBranchOffset(RandomSource random) {
        return Mth.nextInt(random, 2, 4);
    }
    private static int getBranchEndOffset(RandomSource random) {
        return Mth.nextInt(random, 3, 5);
    }
    private static int getBranchTwistAmount(RandomSource random) {
        return Mth.nextInt(random, 0, 2);
    }
    private static int getBranchHeight(RandomSource random) {
        return Mth.nextInt(random, 5, 6);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        var rand = context.random();
        if (level.isEmptyBlock(pos.below()) || !BlockRegistry.SOULWOOD_GROWTH.get().defaultBlockState().canSurvive(level, pos)) {
            return false;
        }
        List<Pair<Direction, BlockPos>> validSoulstoneSpikePositions = new ArrayList<>();

        BlockState defaultLog = BlockRegistry.SOULWOOD_LOG.get().defaultBlockState();
        BlockState blightedLog = BlockRegistry.BLIGHTED_SOULWOOD.get().defaultBlockState();

        LodestoneBlockFiller treeFiller = new LodestoneBlockFiller(false);
        LodestoneBlockFiller blightFiller = new LodestoneBlockFiller(false);
        LodestoneBlockFiller leavesFiller = new LodestoneBlockFiller(true);
        LodestoneBlockFiller hangingLeavesFiller = new LodestoneBlockFiller(true) {
            @Override
            public void fill(LevelAccessor level) {
                getEntries().forEach((pos, entry) -> {
                    final BlockState state = entry.getState();
                    if (blightFiller.getEntries().containsKey(pos) || blightFiller.getEntries().containsKey(pos.above())) {
                        return;
                    }
                    if (state.getBlock().equals(BlockRegistry.MYSTIC_SOULWOOD_LEAVES.get()) && level.getBlockState(pos).getBlock() instanceof MalumLeavesBlock) {
                        entry.place(level, pos);
                    } else if (!isCareful() || entry.canPlace(level, pos)) {
                        entry.place(level, pos);
                    }
                });
            }
        };
        Set<Vector2i> hangingLeavesCoverage = new HashSet<>();

        int sapBlockCount = getSapBlockCount(rand);
        int trunkHeight = getTrunkHeight(rand);
        int twistCooldown = getTwistCooldown(rand);
        int twistDirection = rand.nextInt(DIRECTIONS.length);
        int twistAmount = getTrunkTwistAmount(rand);
        int lowestPossibleBranch = 5;
        BlockPos twistedPos = pos;
        for (int i = 0; i <= trunkHeight+3; i++) //trunk placement
        {
            if (i < trunkHeight - lowestPossibleBranch) {
                if (twistCooldown == 0 && twistAmount != 0) {
                    twistCooldown = getTwistCooldown(rand);
                    BlockPos trunkPos = twistedPos.above(i);
                    if (canPlace(level, trunkPos)) {
                        treeFiller.getEntries().put(trunkPos, new BlockStateEntry(defaultLog));
                        for (Direction direction : DIRECTIONS) {
                            validSoulstoneSpikePositions.add(Pair.of(direction.getOpposite(), trunkPos.relative(direction)));
                        }
                        twistCooldown--;
                    } else {
                        return false;
                    }
                    final Direction direction = DIRECTIONS[twistDirection % 4];
                    blightFiller.getEntries().put(trunkPos.above(), new BlockStateEntry(makeClingingBlight(ClingingBlightBlock.BlightType.ROOTED_BLIGHT, direction)));
                    twistedPos = twistedPos.relative(direction);
                    if (rand.nextFloat() < 0.85f) {
                        twistDirection++;
                    }
                    twistAmount--;
                }
            }
            BlockPos trunkPos = twistedPos.above(i);
            if (canPlace(level, trunkPos)) {
                treeFiller.getEntries().put(trunkPos, new BlockStateEntry(i == 0 ? blightedLog : defaultLog));
                if (i != 0 && i < trunkHeight-3) {
                    for (Direction direction : DIRECTIONS) {
                        validSoulstoneSpikePositions.add(Pair.of(direction.getOpposite(), trunkPos.relative(direction)));
                    }
                }
                twistCooldown--;
            } else {
                return false;
            }
        }
        BlockPos trunkTop = twistedPos.above(trunkHeight);

        makeLeafBlob(leavesFiller, hangingLeavesFiller, hangingLeavesCoverage, rand, trunkTop);
        for (Direction direction : DIRECTIONS) //side trunk placement
        {
            Optional<BlockPos> blightedPosOptional = Optional.empty();
            int sideTrunkHeight = getSideTrunkHeight(rand);
            for (int i = 0; i < sideTrunkHeight; i++) {
                BlockPos sideTrunkPos = pos.relative(direction).above(i);
                if (canPlace(level, sideTrunkPos)) {
                    if (i == 0) {
                        blightedPosOptional = Optional.of(sideTrunkPos);
                    }
                    treeFiller.getEntries().put(sideTrunkPos, new BlockStateEntry(i == 0 ? blightedLog : defaultLog));
                } else {
                    return false;
                }
            }
            boolean success = downwardsTrunk(level, treeFiller, blightFiller, direction, pos.relative(direction));
            if (blightedPosOptional.isPresent()) {
                final BlockPos blightedPos = blightedPosOptional.get();
                if (success) {
                    treeFiller.replace(blightedPos, e -> new BlockStateEntry(defaultLog));
                } else {
                    blightFiller.getEntries().put(blightedPos.relative(direction), new BlockStateEntry(makeClingingBlight(ClingingBlightBlock.BlightType.ROOTED_BLIGHT, direction.getOpposite())));
                }
            }
        }
        for (Direction direction : DIRECTIONS) //tree top placement
        {
            int downwardsBranchOffset = getDownwardsBranchOffset(rand);
            int branchEndOffset = getBranchEndOffset(rand);
            twistAmount = getBranchTwistAmount(rand);
            BlockPos branchStartPos = trunkTop.below(downwardsBranchOffset);
            twistCooldown = 1;
            for (int i = 1; i < branchEndOffset; i++) //branch connection placement
            {
                BlockPos branchConnectionPos = branchStartPos.relative(direction, i);
                final Direction.Axis axis = direction.getAxis();
                if (twistCooldown == 0 && twistAmount != 0) {
                    twistCooldown = getTwistCooldown(rand);
                    BlockPos offsetPos = branchConnectionPos.above();
                    if (canPlace(level, offsetPos)) {
                        treeFiller.getEntries().put(offsetPos, new BlockStateEntry(defaultLog.setValue(RotatedPillarBlock.AXIS, axis)));
                    } else {
                        return false;
                    }
                    branchStartPos = branchStartPos.above();
                    twistAmount--;
                }
                if (canPlace(level, branchConnectionPos)) {
                    final boolean start = i == 1;
                    final Direction opposite = direction.getOpposite();
                    treeFiller.getEntries().put(branchConnectionPos, new BlockStateEntry(defaultLog.setValue(RotatedPillarBlock.AXIS, axis)));
                    blightFiller.getEntries().put(branchConnectionPos.below(), new BlockStateEntry(makeClingingBlight(start ? HANGING_BLIGHT : HANGING_ROOTS, opposite)));
                    if (start) {
                        blightFiller.getEntries().put(branchConnectionPos.below(2), new BlockStateEntry(makeClingingBlight(HANGING_BLIGHT_CONNECTION, opposite)));
                    }
                    twistCooldown--;
                } else {
                    return false;
                }
            }
            BlockPos branchEndPos = branchStartPos.relative(direction, branchEndOffset);
            int branchHeight = getBranchHeight(rand);
            for (int i = 0; i < branchHeight; i++) //branch placement
            {
                BlockPos branchPos = branchEndPos.above(i);
                if (canPlace(level, branchPos)) {
                    treeFiller.getEntries().put(branchPos, new BlockStateEntry(defaultLog));
                } else {
                    return false;
                }
            }
            makeLeafBlob(leavesFiller, hangingLeavesFiller, hangingLeavesCoverage, rand, branchEndPos.above(1));
        }
        generateBlight(level, blightFiller, pos.below(), 8);

        ArrayList<BlockPos> sapBlockPositions = new ArrayList<>(treeFiller.getEntries().keySet());
        Collections.shuffle(sapBlockPositions);
        for (BlockPos blockPos : sapBlockPositions.subList(0, sapBlockCount)) {
            treeFiller.replace(blockPos, e -> e.getState().getBlock().equals(BlockRegistry.BLIGHTED_SOULWOOD.get()) ? e : new BlockStateEntry(BlockHelper.getBlockStateWithExistingProperties(e.getState(), BlockRegistry.EXPOSED_SOULWOOD_LOG.get().defaultBlockState())));
        }
        int spikeCount = 6;
        Collections.shuffle(validSoulstoneSpikePositions);
        for (Pair<Direction, BlockPos> entry : validSoulstoneSpikePositions) {
            final BlockPos entryPos = entry.getSecond();
            if (!blightFiller.getEntries().containsKey(entryPos)) {
                blightFiller.getEntries().put(entryPos, new BlockStateEntry(makeClingingBlight(SOULWOOD_SPIKE, entry.getFirst())));
                if (spikeCount-- == 0) {
                    break;
                }
            }
        }
        blightFiller.fill(level);
        treeFiller.fill(level);
        leavesFiller.fill(level);
        hangingLeavesFiller.fill(level);
        updateLeaves(level, treeFiller.getEntries().keySet());
        return true;
    }

    public static boolean downwardsTrunk(WorldGenLevel level, LodestoneBlockFiller filler, LodestoneBlockFiller blightFiller, Direction direction, BlockPos pos) {
        BlockState defaultLog = BlockRegistry.SOULWOOD_LOG.get().defaultBlockState();
        BlockState blightedLog = BlockRegistry.BLIGHTED_SOULWOOD.get().defaultBlockState();
        int i = 0;
        do {
            i++;
            BlockPos trunkPos = pos.below(i);
            if (canPlace(level, trunkPos)) {
                boolean blighted = !canPlace(level, trunkPos.below());
                filler.getEntries().put(trunkPos, new BlockStateEntry(blighted ? blightedLog : defaultLog));
                if (blighted) {
                    final BlockState state = BlockRegistry.CLINGING_BLIGHT.get().defaultBlockState()
                            .setValue(ClingingBlightBlock.BLIGHT_TYPE, ClingingBlightBlock.BlightType.ROOTED_BLIGHT)
                            .setValue(BlockStateProperties.HORIZONTAL_FACING, direction.getOpposite());
                    blightFiller.getEntries().put(trunkPos.relative(direction), new BlockStateEntry(state));
                }
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

    public static void makeLeafBlob(LodestoneBlockFiller leavesFiller, LodestoneBlockFiller hangingLeavesFiller, Set<Vector2i> hangingLeavesCoverage, RandomSource rand, BlockPos pos) {
        final BlockPos.MutableBlockPos mutable = pos.mutable();
        int[] leafSizes = new int[]{1, 2, 3, 3, 3, 2, 1};
        int[] leafColors = new int[]{4, 3, 2, 1, 2, 3, 4};
        for (int i = 0; i < 7; i++) {
            int size = leafSizes[i];
            int color = leafColors[i];
            makeLeafSlice(leavesFiller, hangingLeavesFiller, hangingLeavesCoverage, rand, mutable, size, color, false);
            mutable.move(Direction.UP);
        }
        mutable.move(Direction.DOWN, 7);
        for (int i = 0; i < 3; i++) {
            int size = leafSizes[i];
            int color = leafColors[i];
            makeLeafSlice(leavesFiller, hangingLeavesFiller, hangingLeavesCoverage, rand, mutable, size, color, true);
            mutable.move(Direction.UP);
        }
    }

    public static void makeLeafSlice(LodestoneBlockFiller filler, LodestoneBlockFiller hangingLeavesFiller, Set<Vector2i> hangingLeavesCoverage, RandomSource rand, BlockPos.MutableBlockPos pos, int leavesSize, int leavesColor, boolean makeHangingLeaves) {
        for (int x = -leavesSize; x <= leavesSize; x++) {
            int offsetColor = leavesColor + Mth.nextInt(rand, leavesColor == 0 ? 0 : -1, leavesColor == 4 ? 0 : 1);
            for (int z = -leavesSize; z <= leavesSize; z++) {
                if (Math.abs(x) == leavesSize && Math.abs(z) == leavesSize) {
                    continue;
                }
                BlockPos leavesPos = pos.offset(x, 0, z);
                if (makeHangingLeaves && !(x == 0 && z == 0)) {
                    final Vector2i column = new Vector2i(leavesPos.getX(), leavesPos.getZ());
                    if (!hangingLeavesCoverage.contains(column)) {
                        if (rand.nextFloat() < (0.4f + leavesSize * 0.2f)) {
                            int length = leavesSize - 2 + RandomHelper.randomBetween(rand, 0, leavesSize);
                            if (length < 2) {
                                length-=2;
                            }
                            if (length < 2) {
                                continue;
                            }
                            int spawnHeight = rand.nextInt(2);
                            BlockPos.MutableBlockPos hangingLeavesPos = leavesPos.mutable().move(Direction.UP, spawnHeight);
                            for (int i = -spawnHeight; i <= length; i++) {
                                final int gradient = Mth.clamp(leavesColor + 1 + i, 0, 4);
                                BlockPos vinePos = hangingLeavesPos.move(Direction.DOWN).immutable();
                                final boolean hanging = i == length;
                                BlockState state = (hanging ? BlockRegistry.HANGING_MYSTIC_SOULWOOD_LEAVES : BlockRegistry.MYSTIC_SOULWOOD_LEAVES).get().defaultBlockState().setValue(MalumLeavesBlock.COLOR, gradient);
                                hangingLeavesFiller.getEntries().put(vinePos, new BlockStateEntry(state));
                                if (hanging) {
                                    hangingLeavesCoverage.add(column);
                                }
                            }
                        }
                    }
                }
                else {
                    filler.getEntries().put(leavesPos, new BlockStateEntry(BlockRegistry.SOULWOOD_LEAVES.get().defaultBlockState().setValue(MalumLeavesBlock.COLOR, offsetColor)));
                }
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
        generateBlight(level, filler, pos, radius * 2, radius, noiseValues);
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
                        if ((plantState.canBeReplaced() || plantState.is(REPLACEABLE) || plantState.is(FLOWERS))) {
                            final BlockPos immutable = blockPos.immutable();
                            if (!filler.getEntries().containsKey(blockPos)) {
                                filler.getEntries().put(immutable, new BlockStateEntry(Blocks.AIR.defaultBlockState()));
                            }
                            blockPos.move(Direction.DOWN);
                        } else {
                            break;
                        }
                    }
                    while (true);

                    if (level.getBlockState(blockPos).is(MOSS_REPLACEABLE)) {
                        filler.getEntries().put(blockPos.immutable(), new BlockStateEntry(BlockRegistry.BLIGHTED_SOIL.get().defaultBlockState()));
                        if (level.getBlockState(blockPos.move(0, -1, 0)).is(DIRT)) {
                            filler.getEntries().put(blockPos.immutable(), new BlockStateEntry(BlockRegistry.BLIGHTED_EARTH.get().defaultBlockState()));
                        }
                        final RandomSource random = level.getRandom();
                        if (random.nextFloat() < 0.75f) {
                            BlockPos plantPos = blockPos.offset(0, 2, 0);
                            BlockState blockState = level.getBlockState(plantPos);
                            if (naturalNoiseValue > 2.5f) {
                                if (lastSaplingPos == null || lastSaplingPos.distanceToSqr(plantPos.getX(), plantPos.getY(), plantPos.getZ()) > 5) {
                                    if (BlockHelper.fromBlockPos(center).distanceToSqr(plantPos.getX(), plantPos.getY(), plantPos.getZ()) > 4) {
                                        if (random.nextFloat() < 0.5f / (Math.pow(saplingsPlaced + 1, 2))) {
                                            filler.getEntries().put(plantPos, new BlockStateEntry(BlockRegistry.SOULWOOD_GROWTH.get().defaultBlockState()));
                                            lastSaplingPos = new Vec3(plantPos.getX(), plantPos.getY(), plantPos.getZ());
                                            saplingsPlaced++;
                                        }
                                    }
                                }
                            }
                            if (!filler.getEntries().containsKey(plantPos)) {
                                BlockState state = (BlockRegistry.BLIGHTED_GROWTH).get().defaultBlockState();
                                if (random.nextFloat() < 0.4f) {
                                    state = BlockRegistry.CLINGING_BLIGHT.get().defaultBlockState().setValue(ClingingBlightBlock.BLIGHT_TYPE, GROUNDED_ROOTS).setValue(BlockStateProperties.HORIZONTAL_FACING, DIRECTIONS[random.nextInt(4)]);
                                }
                                if ((blockState.isAir() || blockState.canBeReplaced()) && !blockState.is(BlockTagRegistry.BLIGHTED_PLANTS)) {
                                    filler.getEntries().put(plantPos, new BlockStateEntry(state));
                                }
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