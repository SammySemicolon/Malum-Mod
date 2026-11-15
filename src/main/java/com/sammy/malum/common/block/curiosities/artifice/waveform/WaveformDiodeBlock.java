package com.sammy.malum.common.block.curiosities.artifice.waveform;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.modules.toolkit.block.LodestoneEntityBlock;

import java.util.EnumSet;

public abstract class WaveformDiodeBlock<T extends SpiritDiodeBlockEntity> extends LodestoneEntityBlock<T> {

    public static final VoxelShape SHAPE = box(0, 0, 0, 16, 2, 16);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public WaveformDiodeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false).setValue(OPEN, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED, OPEN, FACING);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    /**
     * Should update blockstates.
     * Return true if further updates are required.
     */
    public abstract boolean processUpdate(Level level, BlockPos pos, BlockState state, T diode, int cachedSignal, int liveSignal);

    /**
     * Should not update blockstates or Block Entity data.
     * Return true if the blockstate or signal output needs to change.
     */
    public abstract boolean shouldUpdateWhenNeighborChanged(Level level, BlockPos pos, BlockState state, T diode, int liveSignal);


    public int redstoneTicksUntilUpdate(Level level, BlockPos pos, BlockState state, T diode, int cachedSignal, int liveSignal) {
        return Math.max(diode.getAdjustedFrequency(), 1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.getBlockTicks().willTickThisTick(pos, this)) {
            if (level.getBlockEntity(pos) instanceof SpiritDiodeBlockEntity spiritDiode) {
                var direction = state.getValue(FACING);
                int liveSignal = level.getSignal(pos.relative(direction), direction);
                if (shouldUpdateWhenNeighborChanged(level, pos, state, (T) spiritDiode, liveSignal)) {
                    if (spiritDiode.cachedInputSignal == -1) {
                        spiritDiode.cachedInputSignal = liveSignal;
                    }
                    int delay = redstoneTicksUntilUpdate(level, pos, state, (T) spiritDiode, liveSignal, liveSignal);
                    level.scheduleTick(pos, this, delay);
                    if (level instanceof ServerLevel serverLevel) {
                        spiritDiode.updateAnimation(serverLevel, pos, liveSignal);
                    }
                }
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getBlockEntity(pos) instanceof SpiritDiodeBlockEntity spiritDiode) {
            var direction = state.getValue(FACING);
            int cachedSignal = spiritDiode.cachedInputSignal;
            if (cachedSignal == -1) {
                cachedSignal = 0;
            }
            int liveSignal = level.getSignal(pos.relative(direction), direction);
            if (processUpdate(level, pos, state, (T) spiritDiode, cachedSignal, liveSignal)) {
                int delay = redstoneTicksUntilUpdate(level, pos, state, (T) spiritDiode, cachedSignal, liveSignal);
                level.scheduleTick(pos, this, delay);
                spiritDiode.updateAnimation(level, pos, cachedSignal);
            }
            spiritDiode.cachedInputSignal = -1;
        }
    }

    public void updateState(Level level, BlockPos pos, BlockState state, T diode) {
        boolean shouldBePowered = diode.getOutputSignal() != 0;
        boolean isPowered = state.getValue(POWERED);
        if (shouldBePowered != isPowered)
            level.setBlock(pos, state.setValue(WaveformDiodeBlock.POWERED, shouldBePowered), 2);

        notifyNeighborsInFront(level, pos, state);
    }

    public void notifyNeighborsInFront(Level level, BlockPos pos, BlockState state) {
        var direction = state.getValue(FACING);
        if (!EventHooks.onNeighborNotify(level, pos, level.getBlockState(pos), EnumSet.of(direction.getOpposite()), false).isCanceled()) {
            var blockpos = pos.relative(direction.getOpposite());
            level.neighborChanged(blockpos, this, pos);
            level.updateNeighborsAtExceptFromFacing(blockpos, this, direction);
        }
    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        Direction facing = state.getValue(FACING);
        return facing == direction || facing.getOpposite() == direction;
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction side) {
		if (state.getValue(POWERED)) {
			if (state.getValue(FACING) == side) {
				BlockEntity blockentity = level.getBlockEntity(pos);
				if (blockentity instanceof SpiritDiodeBlockEntity spiritDiode) {
					return spiritDiode.getOutputSignal();
				}
			}
		}
        return 0;
	}

    @Override
    public boolean getWeakChanges(BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        var direction = pContext.getHorizontalDirection();
        if (pContext.getPlayer() == null || !pContext.getPlayer().isCrouching()) {
            direction = direction.getOpposite();
        }
        return this.defaultBlockState().setValue(FACING, direction);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
