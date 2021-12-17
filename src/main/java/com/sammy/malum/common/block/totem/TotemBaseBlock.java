package com.sammy.malum.common.block.totem;

import com.sammy.malum.common.tile.TotemBaseTileEntity;
import com.sammy.malum.core.registry.block.TileEntityRegistry;
import com.sammy.malum.core.systems.block.SimpleBlock;

public class TotemBaseBlock extends SimpleBlock<TotemBaseTileEntity>
{
    public final boolean corrupted;
    public TotemBaseBlock(Properties properties, boolean corrupted)
    {
        super(properties);
        this.corrupted = corrupted;
        setTile(TileEntityRegistry.TOTEM_BASE_TILE_ENTITY);
    }
}
