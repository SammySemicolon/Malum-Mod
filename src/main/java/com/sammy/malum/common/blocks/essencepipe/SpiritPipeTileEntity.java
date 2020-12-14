package com.sammy.malum.common.blocks.essencepipe;

import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.spirits.block.ISpiritTransferTileEntity;
import com.sammy.malum.core.systems.spirits.block.SimpleSpiritTransferTileEntity;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.nbt.CompoundNBT;

public class SpiritPipeTileEntity extends SimpleSpiritTransferTileEntity
{
    public SpiritPipeTileEntity()
    {
        super(MalumTileEntities.ESSENCE_PIPE_TILE_ENTITY.get());
    }
}
