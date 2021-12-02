package com.sammy.malum.common.tile;

import com.sammy.malum.core.registry.block.TileEntityRegistry;
import net.minecraft.tileentity.TileEntityType;

public class MalumSignTileEntity extends net.minecraft.tileentity.SignTileEntity
{
    @Override
    public TileEntityType<?> getType()
    {
        return TileEntityRegistry.SIGN_TILE_ENTITY.get();
    }
}
