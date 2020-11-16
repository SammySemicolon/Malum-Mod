package com.sammy.malum.common.blocks.blightingfurnace;

import com.sammy.malum.core.init.MalumTileEntities;
import com.sammy.malum.core.systems.multiblock.MultiblockTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class BlightingFurnaceTileEntity extends MultiblockTileEntity
{
    public BlightingFurnaceTileEntity()
    {
        super(MalumTileEntities.BLIGHTING_FURNACE_TILE_ENTITY.get());
    }
}
