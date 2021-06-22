package com.sammy.malum.common.blocks.runetable.bounding;

import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.multiblock.BoundingBlockTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class RuneTableBoundingBlockTileEntity extends BoundingBlockTileEntity
{
    public RuneTableBoundingBlockTileEntity()
    {
        super(MalumTileEntities.RUNE_TABLE_BOUNDING_BLOCK_TILE_ENTITY.get());
    }
}
