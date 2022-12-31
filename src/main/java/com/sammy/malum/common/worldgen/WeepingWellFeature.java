package com.sammy.malum.common.worldgen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sammy.malum.common.block.MalumLeavesBlock;
import com.sammy.malum.common.block.MalumSaplingBlock;
import com.sammy.malum.common.block.weeping_well.PrimordialSoupBlock;
import com.sammy.malum.common.block.weeping_well.WeepingWellBlock;
import com.sammy.malum.registry.common.block.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.BlockStateEntry;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class WeepingWellFeature extends Feature<NoneFeatureConfiguration> {
    public WeepingWellFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        Random rand = context.random();
        LodestoneBlockFiller filler = new LodestoneBlockFiller(false);
        Direction[] directions = new Direction[]{Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST};

        int wellDepth = 4;
        for (int i = 0; i < 9; i++) {
            int xOffset = (i / 3) - 1;
            int zOffset = i % 3 - 1;
            for (int j = 0; j <= wellDepth; j++) {
                BlockPos primordialGoopPos = pos.offset(xOffset, -j, zOffset);
                filler.getEntries().put(primordialGoopPos, new BlockStateEntry(BlockRegistry.PRIMORDIAL_SOUP.get().defaultBlockState().setValue(PrimordialSoupBlock.TOP, j == 0)));
            }
        }
        filler.getEntries().replace(pos, new BlockStateEntry(BlockRegistry.VOID_CONDUIT.get().defaultBlockState()));

        BlockPos above = pos.above();
        for (Direction direction : directions) {
            BlockPos.MutableBlockPos start = above.mutable().move(direction, 2).move(direction.getCounterClockWise(), 2);
            for (int i = 0; i < 4; i++) {
                Block block = BlockRegistry.WEEPING_WELL_SIDE.get();
                BlockPos.MutableBlockPos segmentPosition = start.move(direction.getClockWise());
                if (i == 1) {
                    block = BlockRegistry.WEEPING_WELL_CORE.get();
                }
                if (i == 3) {
                    block = BlockRegistry.WEEPING_WELL_CORNER.get();
                }
                BlockState state = block.defaultBlockState().setValue(WeepingWellBlock.FACING, direction);
                BlockPos immutable = segmentPosition.immutable();
                filler.getEntries().put(immutable, new BlockStateEntry(state));
                filler.getEntries().put(immutable.below(), new BlockStateEntry(Blocks.DEEPSLATE.defaultBlockState()));
            }
        }

        filler.fill(level);
        return true;
    }

    public static boolean canPlace(WorldGenLevel level, BlockPos pos) {
        if (level.isOutsideBuildHeight(pos)) {
            return false;
        }
        BlockState state = level.getBlockState(pos);
        return level.isEmptyBlock(pos) || state.getMaterial().isReplaceable();
    }
}