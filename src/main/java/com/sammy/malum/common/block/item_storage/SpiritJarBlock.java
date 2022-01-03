package com.sammy.malum.common.block.item_storage;

import com.sammy.malum.common.blockentity.item_storage.SpiritJarTileEntity;
import com.sammy.malum.core.registry.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.block.WaterLoggedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class SpiritJarBlock extends WaterLoggedBlock<SpiritJarTileEntity>
{
    public static final VoxelShape SHAPE = Stream.of(Block.box(2.5, 0.5, 2.5, 13.5, 13.5, 13.5), Block.box(3.5, 14.5, 3.5, 12.5, 16.5, 12.5), Block.box(4.5, 13.5, 4.5, 11.5, 14.5, 11.5), Block.box(5.5, 0, 5.5, 10.5, 1, 10.5)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public SpiritJarBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
        setTile(BlockEntityRegistry.SPIRIT_JAR);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }
}
