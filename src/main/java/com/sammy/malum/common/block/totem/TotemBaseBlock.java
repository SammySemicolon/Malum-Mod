package com.sammy.malum.common.block.totem;

import com.sammy.malum.common.blockentity.totem.TotemBaseTileEntity;
import com.sammy.ortus.systems.block.OrtusEntityBlock;

public class TotemBaseBlock<T extends TotemBaseTileEntity> extends OrtusEntityBlock<T>
{
    public final boolean corrupted;
    public TotemBaseBlock(Properties properties, boolean corrupted)
    {
        super(properties);
        this.corrupted = corrupted;
    }
}
