package com.sammy.malum.common.block.misc.sign;

import com.sammy.malum.common.blockentity.MalumSignTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class MalumWallSignBlock extends WallSignBlock implements EntityBlock
{
    public MalumWallSignBlock(Properties properties, WoodType type)
    {
        super(properties, type);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MalumSignTileEntity(pos,state);
    }
}
