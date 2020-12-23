package com.sammy.malum.common.blocks.spiritjar;

import com.sammy.malum.core.systems.spirits.block.SimpleSpiritHolderTileEntity;

public abstract class SpiritJarTileEntity extends SimpleSpiritHolderTileEntity
{
    public SpiritJarTileEntity()
    {
        super(null);
//        super(MalumTileEntities.SPIRIT_JAR_TILE_ENTITY.get());
    }
    
    @Override
    public int maxSpirits()
    {
        return 25;
    }
}
