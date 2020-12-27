package com.sammy.malum.common.blocks.spiritpipe;

import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.spirits.block.SimpleSpiritTransferTileEntity;

public class SpiritPipeTileEntity extends SimpleSpiritTransferTileEntity
{
    public SpiritPipeTileEntity()
    {
        super(MalumTileEntities.SPIRIT_PIPE_TILE_ENTITY.get());
    }
}
