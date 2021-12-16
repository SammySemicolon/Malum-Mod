package com.sammy.malum.common.tile;

import com.sammy.malum.core.registry.block.TileEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MalumSignTileEntity extends SignBlockEntity
{
    public MalumSignTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType()
    {
        return TileEntityRegistry.SIGN_TILE_ENTITY.get();
    }
}
