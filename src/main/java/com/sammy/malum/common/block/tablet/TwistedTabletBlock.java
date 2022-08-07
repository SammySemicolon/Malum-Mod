package com.sammy.malum.common.block.tablet;

import com.sammy.malum.common.block.spirit_crucible.SpiritCrucibleComponentBlock;
import com.sammy.malum.common.blockentity.tablet.TwistedTabletBlockEntity;
import team.lodestar.lodestone.systems.block.WaterLoggedEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.EmptyHandler;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class TwistedTabletBlock<T extends TwistedTabletBlockEntity> extends WaterLoggedEntityBlock<T> {
    public static final VoxelShape UP = makeUpShape();
    public static final VoxelShape DOWN = makeDownShape();
    public static final VoxelShape SOUTH = makeSouthShape();
    public static final VoxelShape NORTH = makeNorthShape();
    public static final VoxelShape WEST = makeWestShape();
    public static final VoxelShape EAST = makeEastShape();

    public TwistedTabletBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case UP -> {
                return UP;
            }
            case DOWN -> {
                return DOWN;
            }
            case SOUTH -> {
                return SOUTH;
            }
            case NORTH -> {
                return NORTH;
            }
            case WEST -> {
                return WEST;
            }
            case EAST -> {
                return EAST;
            }
        }
        return super.getShape(state, level, pos, context);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof TwistedTabletBlockEntity tablet) {
            return ItemHandlerHelper.calcRedstoneFromInventory(tablet.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(new EmptyHandler()));
        }
        return 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getLevel().getBlockState(context.getClickedPos().relative(context.getClickedFace().getOpposite())).getBlock() instanceof SpiritCrucibleComponentBlock) {
            return super.getStateForPlacement(context).setValue(FACING, context.getClickedFace().getOpposite());
        }
        return super.getStateForPlacement(context).setValue(FACING, context.getClickedFace());
    }

    public static VoxelShape makeUpShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0.0625, 0.9375, 0.25, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.1875, 0.25, 0.75, 0.375, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.375, 0.125, 0.875, 0.5625, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.25, 0.75, 0, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.375, 0.25, 0.1875, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.375, 0.25, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.625, 0.375, 0.25, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0.625, 0.9375, 0.25, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0, 0.125, 0.625, 0.1875, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.375, 0.875, 0.1875, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0, 0.75, 0.625, 0.1875, 0.875), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeSouthShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.625, 0.625, 0, 0.9375, 0.9375, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.25, 0.1875, 0.75, 0.75, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.125, 0.375, 0.875, 0.875, 0.5625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.25, 0, 0.75, 0.75, 0), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.375, 0, 0.25, 0.625, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.625, 0, 0.375, 0.9375, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.0625, 0, 0.375, 0.375, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.0625, 0, 0.9375, 0.375, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.75, 0, 0.625, 0.875, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.375, 0, 0.875, 0.625, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.125, 0, 0.625, 0.25, 0.1875), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeWestShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.75, 0.625, 0.625, 1, 0.9375, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.25, 0.25, 0.8125, 0.75, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.125, 0.125, 0.625, 0.875, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(1, 0.25, 0.25, 1, 0.75, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.375, 0.125, 1, 0.625, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.625, 0.0625, 1, 0.9375, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.0625, 0.0625, 1, 0.375, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.0625, 0.625, 1, 0.375, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.75, 0.375, 1, 0.875, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.375, 0.75, 1, 0.625, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.125, 0.375, 1, 0.25, 0.625), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeNorthShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.625, 0.75, 0.375, 0.9375, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.25, 0.625, 0.75, 0.75, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.125, 0.4375, 0.875, 0.875, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.25, 1, 0.75, 0.75, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.375, 0.8125, 0.875, 0.625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.625, 0.75, 0.9375, 0.9375, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.0625, 0.75, 0.9375, 0.375, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.0625, 0.75, 0.375, 0.375, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.75, 0.8125, 0.625, 0.875, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.375, 0.8125, 0.25, 0.625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.125, 0.8125, 0.625, 0.25, 1), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeDownShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.625, 0.375, 1, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.625, 0.25, 0.75, 0.8125, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.4375, 0.125, 0.875, 0.625, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 1, 0.25, 0.75, 1, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.8125, 0.125, 0.625, 1, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.0625, 0.375, 1, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.75, 0.0625, 0.9375, 1, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.75, 0.625, 0.9375, 1, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.8125, 0.375, 0.25, 1, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.8125, 0.75, 0.625, 1, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.8125, 0.375, 0.875, 1, 0.625), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeEastShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0.0625, 0.25, 0.9375, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.25, 0.25, 0.375, 0.75, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.125, 0.125, 0.5625, 0.875, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.25, 0.25, 0, 0.75, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.375, 0.75, 0.1875, 0.625, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0.625, 0.25, 0.9375, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.0625, 0.625, 0.25, 0.375, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.0625, 0.0625, 0.25, 0.375, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.75, 0.375, 0.1875, 0.875, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.375, 0.125, 0.1875, 0.625, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.125, 0.375, 0.1875, 0.25, 0.625), BooleanOp.OR);

        return shape;
    }
}
