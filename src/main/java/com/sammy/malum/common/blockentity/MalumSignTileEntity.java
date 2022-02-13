package com.sammy.malum.common.blockentity;

import com.sammy.malum.core.setup.block.BlockEntityRegistry;
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
        return BlockEntityRegistry.SIGN_BLOCK_ENTITY.get();
    }
}
