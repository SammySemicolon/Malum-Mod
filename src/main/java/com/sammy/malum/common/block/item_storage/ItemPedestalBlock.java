package com.sammy.malum.common.block.item_storage;

import com.sammy.malum.common.blockentity.item_storage.ItemPedestalTileEntity;
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

public class ItemPedestalBlock extends WaterLoggedBlock<ItemPedestalTileEntity>
{
    public static final VoxelShape SHAPE = Stream.of(
            Block.box(4, 0, 4, 12, 4, 12),
            Block.box(5, 4, 5, 11, 10, 11),
            Block.box(3, 10, 3, 13, 13, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public ItemPedestalBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
        setTile(BlockEntityRegistry.ITEM_PEDESTAL);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}