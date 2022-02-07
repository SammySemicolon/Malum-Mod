package com.sammy.malum.common.block.totem;

import com.sammy.malum.common.blockentity.totem.TotemBaseTileEntity;
import com.sammy.malum.core.setup.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.block.SimpleBlock;

public class TotemBaseBlock extends SimpleBlock<TotemBaseTileEntity>
{
    public final boolean corrupted;
    public TotemBaseBlock(Properties properties, boolean corrupted)
    {
        super(properties);
        this.corrupted = corrupted;
        setTile(BlockEntityRegistry.TOTEM_BASE);
    }
}
