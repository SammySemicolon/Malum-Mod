package com.sammy.malum.common.worldgen;

import com.sammy.malum.common.block.curiosities.weeping_well.PrimordialSoupBlock;
import com.sammy.malum.common.block.curiosities.weeping_well.WeepingWellBlock;
import com.sammy.malum.registry.common.block.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.FluidState;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.BlockStateEntry;

import java.util.Random;

public class WeepingWellFeature extends Feature<NoneFeatureConfiguration> {
    public WeepingWellFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
        while (true) {
            if (level.isOutsideBuildHeight(mutableBlockPos)) {
                return false;
            }
            final boolean isSpaceEmpty = level.isEmptyBlock(mutableBlockPos) && level.isFluidAtPosition(mutableBlockPos, FluidState::isEmpty);
            final boolean isSpaceBelowSolid = !level.isEmptyBlock(mutableBlockPos.move(0, -1, 0));
            if ((isSpaceEmpty && isSpaceBelowSolid)) {
                pos = mutableBlockPos.below();
                break;
            }
        }

        if (!isSufficientlyFlat(level, pos)) {
            return false;
        }


        Random rand = context.random();
        LodestoneBlockFiller filler = new LodestoneBlockFiller(false);
        Direction[] directions = new Direction[]{Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST};

        mutableBlockPos = new BlockPos.MutableBlockPos();
        int failedSolidChecks = 0;
        int failedNonSolidChecks = 0;

        for (int x = -2; x <= 2; x++) {
            for (int y = -5; y <= 4; y++) {
                for (int z = -2; z <= 2; z++) {
                    mutableBlockPos.set(pos.getX()+x, pos.getY()+y, pos.getZ()+z);
                    if (y <= 0) {
                        if (level.isEmptyBlock(mutableBlockPos) || !level.isFluidAtPosition(mutableBlockPos, FluidState::isEmpty)) {
                            failedSolidChecks++;
                        }
                    }
                    else {
                        if (!canPlace(level, mutableBlockPos)) {
                            failedNonSolidChecks++;
                        }
                    }
                }
            }
        }
        if (failedSolidChecks >= 25) {
            return false;
        }
        if (failedNonSolidChecks >= 50) {
            return false;
        }
        pos = pos.below();
        int wellDepth = 4;
        int airPocketHeight = 2;
        for (int i = 0; i < 9; i++) {
            int xOffset = (i / 3) - 1;
            int zOffset = i % 3 - 1;
            for (int j = 0; j < wellDepth; j++) {
                BlockPos primordialGoopPos = pos.offset(xOffset, -j, zOffset);
                filler.getEntries().put(primordialGoopPos, new BlockStateEntry(BlockRegistry.PRIMORDIAL_SOUP.get().defaultBlockState().setValue(PrimordialSoupBlock.TOP, j == 0)));
            }
            for (int j = 1; j <= airPocketHeight; j++) {
                BlockPos airPocketPos = pos.offset(xOffset, j, zOffset);
                filler.getEntries().put(airPocketPos, new BlockStateEntry(Blocks.CAVE_AIR.defaultBlockState()));
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
                filler.getEntries().put(immutable.above(), new BlockStateEntry(Blocks.CAVE_AIR.defaultBlockState()));
                filler.getEntries().put(immutable.above(2), new BlockStateEntry(Blocks.CAVE_AIR.defaultBlockState()));
            }
        }

        int startingIndex = rand.nextInt(2);
        Direction cachedChosenDirection = null;
        for (int i = 0; i < 2; i++) {
            Direction columnDirection = directions[startingIndex + i * 2];
            BlockPos.MutableBlockPos columnPosition = pos.mutable().move(0, 2, 0).move(columnDirection, 3);
            if (rand.nextBoolean()) {
                Direction chosenDirection;
                if (cachedChosenDirection == null) {
                    chosenDirection = rand.nextBoolean() ? columnDirection.getCounterClockWise() : columnDirection.getClockWise();
                } else {
                    chosenDirection = rand.nextBoolean() ? cachedChosenDirection.getOpposite() : null;
                }
                if (chosenDirection != null) {
                    columnPosition.move(chosenDirection);
                }
                cachedChosenDirection = chosenDirection;
            }
            int columnHeight = 3 + rand.nextInt(4);
            int wallHeightDifference = 1 + rand.nextInt(3);
            int wallHeight = columnHeight - wallHeightDifference;
            for (int j = 0; j < columnHeight; j++) {
                BlockState state;
                if (j == 0 || j == columnHeight - 1) {
                    state = BlockRegistry.TAINTED_ROCK_COLUMN_CAP.get().defaultBlockState().setValue(BlockStateProperties.FACING, j == 0 ? Direction.DOWN : Direction.UP);
                } else {
                    state = BlockRegistry.TAINTED_ROCK_COLUMN.get().defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Y);
                }
                filler.getEntries().put(columnPosition.immutable(), new BlockStateEntry(state));
                if (j < wallHeight) {
                    BlockState wallState = BlockRegistry.TAINTED_ROCK_BRICKS_WALL.get().defaultBlockState();
                    final WallSide wallSide = j == wallHeight-1 ? WallSide.LOW : WallSide.TALL;
                    switch (columnDirection) {
                        case SOUTH -> wallState = wallState.setValue(WallBlock.SOUTH_WALL, wallSide);
                        case NORTH -> wallState = wallState.setValue(WallBlock.NORTH_WALL, wallSide);
                        case WEST -> wallState = wallState.setValue(WallBlock.WEST_WALL, wallSide);
                        case EAST -> wallState = wallState.setValue(WallBlock.EAST_WALL, wallSide);
                    }
                    filler.getEntries().put(columnPosition.move(columnDirection.getOpposite()).immutable(), new BlockStateEntry(wallState));
                    columnPosition.move(columnDirection);
                }
                columnPosition.move(0, 1, 0);
            }
        }

        filler.fill(level);
        return true;
    }

    private boolean isSufficientlyFlat(WorldGenLevel level, BlockPos origin) {
        final long count = BlockPos.betweenClosedStream(origin.offset(-3, 0, -3), origin.offset(3, 0, 3))
                .filter(pos -> level.getBlockState(pos).isFaceSturdy(level, pos.below(), Direction.UP))
                .filter(pos -> level.getBlockState(pos.above()).isAir())
                .count();
        return count >= 20; //maximum is 49
    }

    public static boolean canPlace(WorldGenLevel level, BlockPos pos) {
        if (level.isOutsideBuildHeight(pos)) {
            return false;
        }
        BlockState state = level.getBlockState(pos);
        return level.isEmptyBlock(pos) || state.getMaterial().isReplaceable();
    }
}