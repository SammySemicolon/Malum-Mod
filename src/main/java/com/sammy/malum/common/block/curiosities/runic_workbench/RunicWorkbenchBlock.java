package com.sammy.malum.common.block.curiosities.runic_workbench;

import net.minecraft.core.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;
import team.lodestar.lodestone.systems.block.*;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;

public class RunicWorkbenchBlock<T extends RunicWorkbenchBlockEntity> extends LodestoneEntityBlock<T> {

    public static final VoxelShape SHAPE = makeShape();

    public RunicWorkbenchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
        super.createBlockStateDefinition(builder);
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.125, 0.875, 0.25, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.5, 0, 1, 0.875, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.25, 0.25, 0.75, 0.5, 0.75), BooleanOp.OR);

        return shape;
    }
}
