package com.sammy.malum.blocks.machines.spiritjar;

import com.sammy.malum.blocks.utility.spiritstorage.SpiritStoringTileEntity;
import com.sammy.malum.init.ModTileEntities;
import net.minecraft.tileentity.ITickableTileEntity;

public class SpiritJarTileEntity extends SpiritStoringTileEntity implements ITickableTileEntity
{
    public SpiritJarTileEntity()
    {
        super(ModTileEntities.spirit_jar_tile_entity);
    }
    
    @Override
    public void tick()
    {
    
    }
}