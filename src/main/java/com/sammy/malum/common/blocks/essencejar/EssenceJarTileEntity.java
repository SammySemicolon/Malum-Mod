package com.sammy.malum.common.blocks.essencejar;

import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.essences.EssenceHolderTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class EssenceJarTileEntity extends EssenceHolderTileEntity
{
    public EssenceJarTileEntity()
    {
        super(MalumTileEntities.ESSENCE_JAR_TILE_ENTITY.get());
    }
}
