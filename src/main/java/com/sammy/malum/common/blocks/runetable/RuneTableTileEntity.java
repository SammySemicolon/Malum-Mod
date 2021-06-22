package com.sammy.malum.common.blocks.runetable;

import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.multiblock.MultiblockTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class RuneTableTileEntity extends MultiblockTileEntity
{
    public RuneTableTileEntity()
    {
        super(MalumTileEntities.RUNE_TABLE_TILE_ENTITY.get());
    }
}
