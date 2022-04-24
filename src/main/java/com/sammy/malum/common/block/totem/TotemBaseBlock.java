package com.sammy.malum.common.block.totem;

import com.sammy.malum.common.blockentity.totem.TotemBaseTileEntity;
import com.sammy.ortus.systems.block.OrtusBlock;

public class TotemBaseBlock<T extends TotemBaseTileEntity> extends OrtusBlock<T>
{
    public final boolean corrupted;
    public TotemBaseBlock(Properties properties, boolean corrupted)
    {
        super(properties);
        this.corrupted = corrupted;
    }
}
