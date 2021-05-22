package com.sammy.malum.common.blocks.totem;

import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TotemBaseTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public TotemBaseTileEntity()
    {
        super(MalumTileEntities.TOTEM_BASE_TILE_ENTITY.get());
    }

    @Override
    public void tick()
    {

    }
}
