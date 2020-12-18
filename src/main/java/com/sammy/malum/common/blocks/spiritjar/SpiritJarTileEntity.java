package com.sammy.malum.common.blocks.spiritjar;

import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.spirits.block.SimpleSpiritHolderTileEntity;

public class SpiritJarTileEntity extends SimpleSpiritHolderTileEntity
{
    public SpiritJarTileEntity()
    {
        super(MalumTileEntities.SPIRIT_JAR_TILE_ENTITY.get());
    }
    
    @Override
    public int maxSpirits()
    {
        return 25;
    }
}
