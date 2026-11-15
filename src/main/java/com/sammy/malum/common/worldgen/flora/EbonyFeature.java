package com.sammy.malum.common.worldgen.flora;

import com.google.common.collect.ImmutableList;
import com.sammy.malum.common.block.flora.EbonyStemBlock;
import com.sammy.malum.common.worldgen.WorldgenHelper;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.MalumTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import team.lodestar.lodestone.modules.core.easing.Easing;

public class EbonyFeature extends Feature<NoneFeatureConfiguration> {

    private static final PerlinSimplexNoise EBONY_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(1234L)), ImmutableList.of(0));

    private static final BlockState STALK = MalumContent.Materials.EBONY_STALK.get().defaultBlockState();
    private static final BlockState STEM = MalumContent.Materials.EBONY_STEM.get().defaultBlockState();
    private static final BlockState BLOOM = MalumContent.Materials.EBONY_BLOOM.get().defaultBlockState();
    private static final BlockState LEAF = MalumContent.Materials.EBONY_LEAF.get().defaultBlockState();
    private static final Direction[] HORIZONTAL_DIRECTIONS = {
            Direction.NORTH,
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST
    };

    private static final BooleanProperty[] BRANCH_PROPERTIES = {
            EbonyStemBlock.BRANCH_NORTH,
            EbonyStemBlock.BRANCH_EAST,
            EbonyStemBlock.BRANCH_SOUTH,
            EbonyStemBlock.BRANCH_WEST
    };

    private static final BooleanProperty[] CONNECTION_PROPERTIES = {
            EbonyStemBlock.NORTH,
            EbonyStemBlock.EAST,
            EbonyStemBlock.SOUTH,
            EbonyStemBlock.WEST
    };
    public EbonyFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }


    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        var level = context.level();
        var origin = context.origin();
        var random = context.random();
        var mutable = origin.mutable();

        int radius = random.nextIntBetweenInclusive(6, 12);
        int height = random.nextIntBetweenInclusive(2, 8);
        int twiceRadius = radius * 2;

        var noiseSampler = new ImprovedNoise(new XoroshiroRandomSource(level.getSeed()));

        float solidArea = 0.6f;

        var ebonstone = MalumContent.BuildingBlocks.EBONSTONE.getRaw().block.getDefaultState();

        // Find the bottom of the central ebonstone column.
        mutable.set(origin);

        // Find the ground below the central column.
        while (mutable.getY() > level.getMinBuildHeight()
                && (level.getBlockState(mutable).isAir() || level.getBlockState(mutable).is(ebonstone.getBlock()))) {
            mutable.move(Direction.DOWN);
        }

        // Establish the normal column height based on mutable
        int normalBaseY = mutable.getY() - 1;

        // Track generated column heights.
        int[][] columnBaseHeights = new int[twiceRadius * 2 + 1][twiceRadius * 2 + 1];
        boolean[][] generatedColumns = new boolean[twiceRadius * 2 + 1][twiceRadius * 2 + 1];

        for (int x = -twiceRadius; x <= twiceRadius; x++) {
            for (int z = -twiceRadius; z <= twiceRadius; z++) {
                float delta = Mth.sqrt(x * x + z * z);
                mutable.set(origin).move(x, 0, z);

                float noise = (float) WorldgenHelper.getNoise(noiseSampler, mutable, 0.25f);
                float secondaryNoise = (float) WorldgenHelper.getNoise(noiseSampler, mutable, 0.5f);
                float adjustedRadius = radius * Easing.SINE_IN_OUT.asValueDistribution(noise, 0.8f, 1.2f);
                delta += secondaryNoise * 0.5f;
                delta /= adjustedRadius;
                delta = Mth.clamp(delta, 0, 1);
                delta *= delta;
                int addedHeight;
                if (delta < solidArea) {
                    float relative = delta / solidArea;
                    relative = Easing.SINE_IN_OUT.lerp(relative, 0, 1);
                    addedHeight = Easing.SINE_IN_OUT.asValueDistribution(relative, height, height + 2);
                } else {
                    float relative = (delta - solidArea) / (1 - solidArea);
                    addedHeight = Easing.BACK_IN_OUT.asValueDistribution(relative, height + 2, 0);
                }
                if (addedHeight > 0) {
                    float heightNoise = (float) WorldgenHelper.getNoise(noiseSampler, mutable, 0.35f);
                    addedHeight += Easing.SINE_IN_OUT.asValueDistribution(heightNoise, -1, 2);
                    if (addedHeight <= 0) {
                        continue;
                    }
                    // Project column onto the ground, starting 4 blocks above normal height.
                    int baseY = normalBaseY;
                    boolean foundGround = false;
                    int searchY = normalBaseY + 4;

                    for (int i = 1; i <= 8; i++) {
                        var checkPos = new BlockPos(mutable.getX(), searchY - i, mutable.getZ());
                        if (!level.getBlockState(checkPos).isAir()) {
                            baseY = checkPos.getY() - 1;
                            foundGround = true;
                            break;
                        }
                    }
                    if (!foundGround) {
                        baseY = searchY - 8;
                    }
                    // Restrict height based on neighbouring columns, can be removed if not wanted
                    int gridX = x + twiceRadius;
                    int gridZ = z + twiceRadius;
                    int minY = level.getMinBuildHeight();
                    int maxY = level.getMaxBuildHeight();
                    for (int[] offset : new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
                        int nx = gridX + offset[0];
                        int nz = gridZ + offset[1];

                        if (nx >= 0 && nx < columnBaseHeights.length
                                && nz >= 0 && nz < columnBaseHeights.length
                                && generatedColumns[nx][nz]) {

                            int neighbourY = columnBaseHeights[nx][nz];

                            minY = Math.max(minY, neighbourY - 1);
                            maxY = Math.min(maxY, neighbourY + 1);
                        }
                    }

                    if (minY <= maxY) {
                        baseY = Mth.clamp(baseY, minY, maxY);
                    } else {
                        baseY = (minY + maxY) / 2;
                    }

                    columnBaseHeights[gridX][gridZ] = baseY;
                    generatedColumns[gridX][gridZ] = true;

                    mutable.set(origin.getX() + x, baseY, origin.getZ() + z);

                    // Generate column.
                    for (int i = 0; i < addedHeight; i++) {
                        level.setBlock(mutable, ebonstone, 3);
                        mutable.move(Direction.UP);
                    }

                    // Move upward through existing ebonstone.
                    while (level.getBlockState(mutable).is(ebonstone.getBlock())) {
                        mutable.move(Direction.UP);
                    }

                    // Require four air blocks above the column.
                    boolean emptyAbove = true;

                    for (int i = 0; i < 4; i++) {
                        if (!level.getBlockState(mutable).isAir()) {
                            emptyAbove = false;
                            break;
                        }
                        mutable.move(Direction.UP);
                    }

                    if (emptyAbove) {
                        generateEbonyStalk(level, mutable.getX(), mutable.getY() - 4, mutable.getZ(), random);
                    }
                }
            }
        }

        return true;
    }


    private void generateEbonyStalk(WorldGenLevel level, int x, int y, int z, RandomSource random) {
        if (random.nextFloat() >= 0.10f) {
            return;
        }

        var pos = new BlockPos.MutableBlockPos(x, y, z);

        if (!level.getBlockState(pos).isAir() || !STALK.canSurvive(level, pos)) {
            return;
        }

        level.setBlock(pos, STALK, 3);

        float chance = 0.5f;

        for (int i = 1; i <= 5; i++) {
            pos.move(Direction.UP);

            if (i == 5) {
                if (random.nextFloat() < chance &&
                        level.getBlockState(pos).isAir() &&
                        BLOOM.canSurvive(level, pos)) {
                    level.setBlock(pos, BLOOM, 3);
                }

                break;
            }

            if (!level.getBlockState(pos).isAir()) {
                break;
            }

            boolean isStem = random.nextFloat() < 0.30f;
            BlockState segmentState = isStem ? STEM : STALK;

            if (!segmentState.canSurvive(level, pos)) {
                break;
            }

            level.setBlock(pos, segmentState, 3);

            if (isStem) {
                generateEbonyStem(level, pos, random);
            }
        }
    }

    private void generateEbonyStem(WorldGenLevel level, BlockPos pos, RandomSource random) {
        boolean[] branches = new boolean[4];
        boolean[] connections = new boolean[4];

        for (int i = 0; i < 4; i++) {
            branches[i] = random.nextFloat() > 0.3f;
        }

        for (int i = 0; i < 4; i++) {
            connections[i] = !branches[i] && random.nextFloat() > 0.5f;
        }

        BlockState stemState = EbonyFeature.STEM;

        for (int i = 0; i < 4; i++) {
            stemState = stemState
                    .setValue(BRANCH_PROPERTIES[i], branches[i])
                    .setValue(CONNECTION_PROPERTIES[i], connections[i]);
        }

        level.setBlock(pos, stemState, 3);

        for (int i = 0; i < 4; i++) {
            if (branches[i]) {
                generateEbonyLeaf(level, pos, HORIZONTAL_DIRECTIONS[i]);
            }
        }
    }

    private void generateEbonyLeaf(WorldGenLevel level, BlockPos stemPos, Direction direction) {
        var leafPos = stemPos.relative(direction);

        if (!level.getBlockState(leafPos).isAir()) {
            return;
        }

        BlockState facingLeaf = EbonyFeature.LEAF.setValue(
                BlockStateProperties.HORIZONTAL_FACING,
                direction.getOpposite()
        );

        if (!facingLeaf.canSurvive(level, leafPos)) {
            return;
        }

        level.setBlock(leafPos, facingLeaf, 3);
    }


    private void ebonifyArea(WorldGenLevel level, BlockPos centre, RandomSource random) {
        int radius = random.nextIntBetweenInclusive(4, 7);

        BlockState ebonstone = MalumContent.BuildingBlocks.EBONSTONE
                .getRaw().block.getDefaultState();

        for (BlockPos pos : BlockPos.betweenClosed(
                centre.offset(-radius, -radius, -radius),
                centre.offset(radius, radius, radius))) {

            double randomRadius = radius + random.nextDouble() * 1.5 - 0.75;

            if (pos.distSqr(centre) > randomRadius * randomRadius) {
                continue;
            }

            BlockState state = level.getBlockState(pos);

            if (!state.is(MalumTags.Blocks.EBONY_REPLACEABLE)) {
                continue;
            }

            // Replace blocks in the tag with ebonstone.
            level.setBlock(pos, ebonstone, 3);

            // Attempt to generate a stalk above this block.
            generateEbonyStalk(
                    level,
                    pos.getX(),
                    pos.getY() + 1,
                    pos.getZ(),
                    random
            );
        }

        // TUFF LOOP
        BlockState tuff = Blocks.TUFF.defaultBlockState();
        int outerRadius = radius + 2;

        for (BlockPos pos : BlockPos.betweenClosed(
                centre.offset(-outerRadius, -outerRadius, -outerRadius),
                centre.offset(outerRadius, outerRadius, outerRadius))) {

            double randomRadius = outerRadius + random.nextDouble() * 1.5 - 0.75;

            if (pos.distSqr(centre) > randomRadius * randomRadius) {
                continue;
            }

            BlockState state = level.getBlockState(pos);

            if (!state.is(MalumTags.Blocks.EBONY_REPLACEABLE)) {
                continue;
            }

            if (state.is(ebonstone.getBlock())) {
                continue;
            }

            level.setBlock(pos, tuff, 3);
        }
    }

    private boolean canPlace(WorldGenLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);

        if (!state.isAir()) {
            return false;
        }

        return MalumContent.Materials.EBONY_SAPLING
                .get()
                .defaultBlockState()
                .canSurvive(level, pos);
    }
}
