package com.sammy.malum.common.block.totem;

import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.ortus.systems.block.OrtusEntityBlock;

public class TotemBaseBlock<T extends TotemBaseBlockEntity> extends OrtusEntityBlock<T>
{
    public final boolean corrupted;
    public TotemBaseBlock(Properties properties, boolean corrupted)
    {
        super(properties);
        this.corrupted = corrupted;
    }
}
