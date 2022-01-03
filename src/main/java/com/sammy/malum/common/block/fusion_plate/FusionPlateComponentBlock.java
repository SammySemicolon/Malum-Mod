package com.sammy.malum.common.block.fusion_plate;

import com.sammy.malum.core.systems.multiblock.ComponentBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class FusionPlateComponentBlock extends ComponentBlock {
    public static final VoxelShape SIDE_NORTH_SHAPE = makeNorthSideShape();
    public static final VoxelShape SIDE_SOUTH_SHAPE = makeSouthSideShape();
    public static final VoxelShape SIDE_EAST_SHAPE = makeEastSideShape();
    public static final VoxelShape SIDE_WEST_SHAPE = makeWestSideShape();
    public static final VoxelShape CORNER_NORTH_SHAPE = makeNorthCornerShape();
    public static final VoxelShape CORNER_SOUTH_SHAPE = makeSouthCornerShape();
    public static final VoxelShape CORNER_EAST_SHAPE = makeEastCornerShape();
    public static final VoxelShape CORNER_WEST_SHAPE = makeWestCornerShape();

    public static final BooleanProperty CORNER = BooleanProperty.create("corner");
    private final Supplier<Item> cloneStack;
    public FusionPlateComponentBlock(Properties properties, Supplier<Item> cloneStack) {
        super(properties);
        this.cloneStack = cloneStack;
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(CORNER, false));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.getValue(CORNER))
        {
            switch (pState.getValue(HORIZONTAL_FACING))
            {
                case NORTH -> {
                    return CORNER_NORTH_SHAPE;
                }
                case SOUTH -> {
                    return CORNER_SOUTH_SHAPE;
                }
                case EAST -> {
                    return CORNER_EAST_SHAPE;
                }
                case WEST -> {
                    return CORNER_WEST_SHAPE;
                }
            }
        }
        else
        {
            switch (pState.getValue(HORIZONTAL_FACING))
            {
                case NORTH -> {
                    return SIDE_NORTH_SHAPE;
                }
                case SOUTH -> {
                    return SIDE_SOUTH_SHAPE;
                }
                case EAST -> {
                    return SIDE_EAST_SHAPE;
                }
                case WEST -> {
                    return SIDE_WEST_SHAPE;
                }
            }
        }

        return super.getShape(pState, pLevel, pPos, pContext);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        return cloneStack.get().getDefaultInstance();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, CORNER);
    }

    public static VoxelShape makeNorthSideShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0.625, 1, 0.75, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.0625, 1, 0.125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.125, 0.375, 0.1875, 0.75, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.125, 0.375, 1, 0.75, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.125, 0.25, 0.8125, 0.625, 0.625), BooleanOp.OR);

        return shape;
    }
    public static VoxelShape makeSouthSideShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0, 1, 0.75, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.125, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.125, 0.375, 1, 0.75, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.125, 0.375, 0.1875, 0.75, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.125, 0.375, 0.8125, 0.625, 0.75), BooleanOp.OR);

        return shape;
    }
    public static VoxelShape makeEastSideShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0, 0.375, 0.75, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.9375, 0.125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.125, 0, 0.625, 0.75, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.125, 0.8125, 0.625, 0.75, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.125, 0.1875, 0.75, 0.625, 0.8125), BooleanOp.OR);

        return shape;
    }
    public static VoxelShape makeWestSideShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.625, 0.625, 0, 1, 0.75, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0, 1, 0.125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.125, 0.8125, 0.625, 0.75, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.125, 0, 0.625, 0.75, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.125, 0.1875, 0.625, 0.625, 0.8125), BooleanOp.OR);

        return shape;
    }
    public static VoxelShape makeNorthCornerShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0.625, 0.375, 0.75, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.125, 0.3125, 0.6875, 0.625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.1875, 0.8125, 0.125, 1), BooleanOp.OR);

        return shape;
    }
    public static VoxelShape makeSouthCornerShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.625, 0.625, 0, 1, 0.75, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.125, 0, 1, 0.625, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0, 1, 0.125, 0.8125), BooleanOp.OR);

        return shape;
    }
    public static VoxelShape makeEastCornerShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0, 0.375, 0.75, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.125, 0, 0.6875, 0.625, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.8125, 0.125, 0.8125), BooleanOp.OR);

        return shape;
    }
    public static VoxelShape makeWestCornerShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.625, 0.625, 0.625, 1, 0.75, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.125, 0.3125, 1, 0.625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.1875, 1, 0.125, 1), BooleanOp.OR);

        return shape;
    }
}