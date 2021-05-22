package com.sammy.malum.common.blocks.totem.pole;

import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;

public class TotemPoleTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public TotemPoleTileEntity()
    {
        super(MalumTileEntities.TOTEM_BASE_TILE_ENTITY.get());
    }

    @Override
    public void tick()
    {

    }
}
