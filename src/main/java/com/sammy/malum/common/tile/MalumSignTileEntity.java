package com.sammy.malum.common.tile;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.block.MalumTileEntities;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.tileentity.TileEntityType;

public class MalumSignTileEntity extends net.minecraft.tileentity.SignTileEntity
{
    @Override
    public TileEntityType<?> getType()
    {
        return MalumTileEntities.SIGN_TILE_ENTITY.get();
    }
}
