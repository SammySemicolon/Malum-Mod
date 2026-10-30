package com.sammy.malum.common.worldgen.flora;

import com.google.common.collect.ImmutableList;
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
        var noiseSampler = new ImprovedNoise(new XoroshiroRandomSource(level.getSeed()));

        boolean placed = true;

        float solidArea = 0.6f;
        var ebonstone = MalumContent.BuildingBlocks.EBONSTONE.getRaw().block.getDefaultState();
        int twiceRadius = radius * 2;
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
                    addedHeight = Easing.SINE_IN_OUT.asValueDistribution(relative, height, height+2);
                }
                else {
                    float relative = (delta - solidArea) / (1 - solidArea);
                    addedHeight = Easing.BACK_IN_OUT.asValueDistribution(relative, height + 2, 0);
                }
                if (addedHeight > 0) {
                    float heightNoise = (float) WorldgenHelper.getNoise(noiseSampler, mutable, 0.35f);
                    addedHeight += Easing.SINE_IN_OUT.asValueDistribution(heightNoise, -1, 2);
                    for (int i = 0; i < addedHeight; i++) {
                        level.setBlock(mutable, ebonstone, 3);
                        mutable.move(Direction.UP);
                    }
                }

            }
        }

//        for (int i = 0; i < count; i++) {
//            int x = origin.getX() + random.nextInt(radius * 2 + 1) - radius;
//            int y = origin.getY() + random.nextInt(5) - 2;
//            int z = origin.getZ() + random.nextInt(radius * 2 + 1) - radius;
//
//            BlockPos pos = new BlockPos(x, y, z);
//
//            if (canPlace(level, pos)) {
//                level.setBlock(
//                        pos,
//                        MalumContent.Materials.EBONY_SAPLING
//                                .get()
//                                .defaultBlockState(),
//                        3
//                );
//
//                ebonifyArea(level, pos, random);
//
//                placed = true;
//            }
//        }

        return placed;
    }

    private void ebonifyArea(WorldGenLevel level, BlockPos centre, RandomSource random) {
        int radius = random.nextIntBetweenInclusive(4, 7);

        BlockState ebonstone = MalumContent.BuildingBlocks.EBONSTONE.getRaw().block.getDefaultState();
        BlockState stalk = MalumContent.Materials.EBONY_STALK.get().defaultBlockState();

        for (BlockPos pos : BlockPos.betweenClosed(
                centre.offset(-radius, -radius, -radius),
                centre.offset(radius, radius, radius))) {

            double randomRadius = radius + random.nextDouble() * 1.5 - 0.75;

            if (pos.distSqr(centre) > randomRadius * randomRadius) { //this can be more efficient
                continue;
            }

            BlockState state = level.getBlockState(pos);

            if (!state.is(MalumTags.Blocks.EBONY_REPLACEABLE)) {
                continue;
            }

            //replace blocks in tag with ebonstone
            level.setBlock(pos, ebonstone, 3);

            //chance for ebonstone to have stalk
            if (random.nextFloat() >= 0.15f) {
                continue;
            }

            BlockPos stalkPos = pos.above();
            if (!level.getBlockState(stalkPos).isAir() || !stalk.canSurvive(level, stalkPos)) {
                continue;
            }

            level.setBlock(stalkPos, stalk, 3);

            //chance for next segment
            float chance = 0.65f;
            for (int i = 1; i < 4; i++) {
                if (random.nextFloat() >= chance) {
                    break;
                }

                stalkPos = stalkPos.above();

                if (!level.getBlockState(stalkPos).isAir() || !stalk.canSurvive(level, stalkPos)) {
                    break;
                }

                level.setBlock(stalkPos, stalk, 3);

                chance *= 0.5f;
            }
        }

        //TUFF LOOP
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
