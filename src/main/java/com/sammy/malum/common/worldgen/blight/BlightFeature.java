package com.sammy.malum.common.worldgen.blight;

import com.google.common.collect.*;
import com.sammy.malum.common.block.blight.*;
import com.sammy.malum.common.worldgen.WorldgenHelper;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.magic.*;
import com.sammy.malum.registry.common.sound.*;
import com.sammy.malum.visual_effects.networked.blight.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.synth.*;
import net.minecraft.world.level.material.*;
import team.lodestar.lodestone.helpers.block.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.modules.toolkit.worldgen.LodestoneWorldgenBuilder;
import team.lodestar.lodestone.modules.toolkit.worldgen.LodestoneWorldgenBuilderEntry;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class BlightFeature extends Feature<NoneFeatureConfiguration> {

    private static final PerlinSimplexNoise COVERING_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(1234L)), ImmutableList.of(0));

    public BlightFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        var level = context.level();
        var pos = context.origin();
        generateBlight(level, pos, 8).place(level);
        return true;
    }


    public static void createBlightVFX(ServerLevel level, BlockPos sourcePos, LodestoneWorldgenBuilder blight) {
        MalumParticleEffectTypes.BLIGHT_PROPAGATION.createEffect(sourcePos)
                .customData(new BlightParticleEffect.BlightEffectData(blight.getAffectedArea(0)))
                .spawn(level);
        MalumParticleEffectTypes.BLIGHT_PLANT_GROWTH.createEffect(sourcePos)
                .customData(new BlightParticleEffect.BlightEffectData(blight.getAffectedArea(1)))
                .spawn(level);
    }

    public static void createScarstoneVFX(ServerLevel level, BlockPos sourcePos, LodestoneWorldgenBuilder scarstone) {
        MalumParticleEffectTypes.SCARSTONE_FORMS.createEffect(sourcePos)
                .color(MalumSpiritTypes.ARCANE_SPIRIT, MalumSpiritTypes.AQUEOUS_SPIRIT)
                .customData(new BlightParticleEffect.BlightEffectData(scarstone.getAffectedArea(0)))
                .spawn(level);
        MalumParticleEffectTypes.STRANGE_CRYSTAL_FORMS.createEffect(sourcePos)
                .color(MalumSpiritTypes.ARCANE_SPIRIT, MalumSpiritTypes.INFERNAL_SPIRIT)
                .customData(new BlightParticleEffect.BlightEffectData(scarstone.getAffectedArea(1)))
                .spawn(level);
    }

    public static LodestoneWorldgenBuilder generateBlight(WorldGenLevel level, BlockPos pos, boolean allowScarstone, int radius) {
        var random = level.getRandom();
        var builder = LodestoneWorldgenBuilder.create();
        Set<BlockPos> columnExclusions = new HashSet<>();
        if (allowScarstone && random.nextFloat() < 0.1f) {
            int offset = (int) (radius * 0.8f);
            int xOffset = Easing.CIRC_OUT.asWeighedRandom(random, offset / 2, offset * 2) * (random.nextBoolean() ? 1 : -1);
            int zOffset = Easing.CIRC_OUT.asWeighedRandom(random, offset / 2, offset * 2) * (random.nextBoolean() ? 1 : -1);
            var scarstonePos = pos.offset(xOffset, 0, zOffset);
            var scarstone = ScarstoneFeature.generateScarstone(level, scarstonePos, (int) (radius * 0.7f));
            for (BlockPos scarredPos : scarstone.getAffectedArea()) {
                columnExclusions.add(scarredPos);
                columnExclusions.add(scarredPos.above());
            }
            var extraBlight = generateBlight(level, scarstonePos, radius, columnExclusions);
            builder.merge(extraBlight).merge(scarstone);
            if (level instanceof ServerLevel realLevel) {
                createBlightVFX(realLevel, pos, extraBlight);
                createScarstoneVFX(realLevel, scarstonePos, scarstone);
                level.playSound(null, scarstonePos, MalumBlockSoundEvents.SCARSTONE_PROPAGATION.get(), SoundSource.BLOCKS, 2f, 1f);
            }
        }
        var blight = generateBlight(level, pos, radius, columnExclusions);
        builder.merge(blight);
        if (level instanceof ServerLevel realLevel) {
            createBlightVFX(realLevel, pos, blight);
            level.playSound(null, pos, MalumBlockSoundEvents.BLIGHT_PROPAGATION.get(), SoundSource.BLOCKS, 1f, 1f);
        }
        return builder;
    }

    private static LodestoneWorldgenBuilder generateBlight(WorldGenLevel level, BlockPos pos, int radius) {
        return generateBlight(level, pos, radius, Set.of());
    }

    private static LodestoneWorldgenBuilder generateBlight(WorldGenLevel level, BlockPos pos, int radius, Set<BlockPos> columnExclusions) {
        var random = level.getRandom();

        var builder = LodestoneWorldgenBuilder.create().addAdditionalPlacement(BlightFeature::cleanupFoliage);
        var blightLayer = builder.createLayer();
        var floraLayer = builder.createLayer();
        var coveringLayer = builder.createLayer();

        var blightedArea = fetchCoveringPositions(level, pos, radius);
        for (BlockPos blockPos : blightedArea) {
            BlockState state = level.getBlockState(blockPos);
            if (state.is(MalumTags.Blocks.BLIGHT_REPLACEABLE)) {
                blightLayer.add(blockPos, MalumContent.Blight.BLIGHTED_EARTH.get());
            }
        }

        if (random.nextFloat() < 0.2f) {
            int offset = (int) (radius * 0.25f);
            int xOffset = Easing.CIRC_OUT.asWeighedRandom(random, offset / 2, offset * 2) * (random.nextBoolean() ? 1 : -1);
            int zOffset = Easing.CIRC_OUT.asWeighedRandom(random, offset / 2, offset * 2) * (random.nextBoolean() ? 1 : -1);
            var columnPos = pos.offset(xOffset, 0, zOffset);
            var columns = fetchCoveringPositions(level, columnPos, Mth.floor(radius*0.5f)+1);
            var mutable = new BlockPos.MutableBlockPos();
            for (BlockPos blockPos : columns) {
                if (random.nextFloat() < 0.4f) {
                    boolean isBlightedFoundation = blightLayer.containsKey(blockPos)
                            || level.getBlockState(blockPos).is(MalumContent.Blight.BLIGHTED_EARTH.get());
                    if (!isBlightedFoundation || columnExclusions.contains(blockPos)) {
                        continue;
                    }
                    mutable.set(blockPos);
                    int desiredHeight = Easing.EXPO_IN_OUT.asWeighedRandom(random, 2, 6);
                    int height = 0;
                    for (int i = 0; i < desiredHeight; i++) {
                        mutable.move(Direction.UP);
                        var state = level.getBlockState(mutable);
                        if (columnExclusions.contains(mutable) || state.getBlock() instanceof ColumnarBlightBlock) {
                            height = -1;
                            break;
                        }
                        if (!state.is(MalumTags.Blocks.BLIGHT_REPLACEABLE) && !state.canBeReplaced()) {
                            height = -1;
                            break;
                        }
                        height++;
                    }
                    mutable.set(blockPos);
                    if (height > 0) {
                        for (int i = 0; i < height; i++) {
                            mutable.move(Direction.UP);
                            var columnarBlight = MalumContent.Blight.COLUMNAR_BLIGHT.get().defaultBlockState()
                                    .setValue(ColumnarBlightBlock.BOTTOM, i > 0)
                                    .setValue(ColumnarBlightBlock.TOP, i < height-1);
                            blightLayer.add(mutable.immutable(), columnarBlight);
                        }
                    }
                }
            }
        }

        if (!blightedArea.isEmpty()) {
            List<BlockPos> floraPositions = WorldgenHelper.shuffle(blightedArea, random);
            int floraCount = Math.min(random.nextInt(1, radius * 4 + 1), floraPositions.size() - 1);
            boolean hasSoulwood = false;
            for (BlockPos blockPos : floraPositions) {
                BlockPos above = blockPos.above();
                BlockState state = level.getBlockState(above);
                if (!state.getFluidState().isEmpty()) {
                    continue;
                }
                if (!state.canBeReplaced()) {
                    continue;
                }
                if (state.is(MalumTags.Blocks.BLIGHTED_PLANTS)) {
                    continue;
                }
                Block block;
                if (radius > 3 && !hasSoulwood && random.nextFloat() < 0.1f) {
                    block = MalumContent.BuildingBlocks.SOULWOOD_SAPLING.get();
                    hasSoulwood = true;
                } else if (random.nextFloat() < 0.4f) {
                    block = random.nextFloat() < 0.2f ? MalumContent.Blight.BLIGHTPEARL.get() : MalumContent.Blight.BLIGHTROOT.get();
                } else {
                    block = MalumContent.Blight.BLIGHTED_GUNK.get();
                }
                floraLayer.add(above, block);
                floraCount--;
                if (floraCount == 0) {
                    break;
                }
            }
        }
        var coveringArea = WorldgenHelper.shuffle(fetchCoveringPositions(level, pos, radius + 3), random);
        if (!coveringArea.isEmpty()) {
            int coveringCount = Math.min(random.nextInt(1, 8 + radius * 8 + 1), coveringArea.size() - 1);
            for (BlockPos blockPos : coveringArea) {
                BlockState state = level.getBlockState(blockPos);
                if (!state.is(MalumTags.Blocks.BLIGHT_REPLACEABLE)) {
                    continue;
                }
                if (blightLayer.containsKey(blockPos)) {
                    continue;
                }
                var above = blockPos.above();
                boolean isWaterLogged = level.getBlockState(above).getFluidState().is(Fluids.WATER);
                var covering = MalumContent.Blight.BLIGHT.get().defaultBlockState()
                        .setValue(MultifaceBlock.getFaceProperty(Direction.DOWN), true)
                        .setValue(BlockStateProperties.WATERLOGGED, isWaterLogged);
                coveringLayer.add(above, covering);
                coveringCount--;
                if (coveringCount == 0) {
                    break;
                }
            }
        }

        return builder;
    }

    public static void cleanupFoliage(WorldGenLevel level, LodestoneWorldgenBuilderEntry entry) {
        BlockPos.MutableBlockPos mutable = entry.position().mutable();
        for (int i = 0; i < 3; i++) {
            mutable.move(Direction.UP);
            BlockState aboveState = level.getBlockState(mutable);
            if (aboveState.getFluidState().isEmpty() && aboveState.is(MalumTags.Blocks.BLIGHT_REMOVABLE)) {
                level.setBlock(mutable, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 19);
                if (level instanceof Level realLevel) {
                    BlockStateHelper.updateState(realLevel, mutable);
                }
            }
        }
    }

    public static List<BlockPos> fetchCoveringPositions(ServerLevelAccessor level, BlockPos center, int radius) {
        return fetchCoveringPositions(level, center, radius, BlightFeature::canBeRemoved).stream().filter(
                        p -> {
                            BlockState above = level.getBlockState(p.above());
                            return above.canBeReplaced() || above.is(MalumTags.Blocks.BLIGHT_REMOVABLE);
                        })
                .collect(Collectors.toList());
    }

    public static List<BlockPos> fetchCoveringPositions(ServerLevelAccessor level, BlockPos center, int radius, Predicate<BlockState> statePredicate) {
        List<BlockPos> positions = new ArrayList<>();
        int x = center.getX();
        int z = center.getZ();
        var mutable = new BlockPos.MutableBlockPos();

        int verticalRange = 6;
        float limit = Mth.sqrt(radius * radius + radius * radius);
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                int offsetX = x + i;
                int offsetZ = z + j;
                float distance = Mth.sqrt(i * i + j * j);
                float theta = (float) (Math.toDegrees(Math.atan2(i, j)) * 0.01f);
                float noise = (float) ((COVERING_NOISE.getValue(x * 10000 + theta, z * 10000 + theta, true) + 1) / 2);
                double threshold = Easing.SINE_IN_OUT.lerp(noise, 0.5f, 2f) * radius * (limit - distance) / limit;
                if (distance <= threshold) {
                    mutable.set(offsetX, center.getY(), offsetZ);
                    for (int k = 0; k < verticalRange; k++) {
                        if (!level.isStateAtPosition(mutable, statePredicate)) {
                            mutable.move(Direction.UP);
                        }
                    }
                    for (int k = 0; k <= verticalRange * 2; k++) {
                        if (level.isStateAtPosition(mutable, statePredicate)) {
                            mutable.move(Direction.DOWN);
                        }
                    }
                    var state = level.getBlockState(mutable);
                    if (state.isFaceSturdy(level, mutable, Direction.UP)) {
                        positions.add(mutable.immutable());
                    }
                }
            }
        }
        return positions;
    }

    public static boolean canBeRemoved(BlockState state) {
        return state.canBeReplaced() || state.is(MalumTags.Blocks.BLIGHT_REMOVABLE);
    }
}
