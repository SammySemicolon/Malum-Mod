package com.sammy.malum.core.systems.spirits.block;

import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public class SimpleSpiritTransferTileEntity extends SimpleTileEntity implements ISpiritTransferTileEntity
{
    protected boolean needsUpdate = false;
    
    public SimpleSpiritTransferTileEntity(TileEntityType type)
    {
        super(type);
    }
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        if (needsUpdate)
        {
            compound.putBoolean("needsUpdate", true);
        }
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        needsUpdate = compound.getBoolean("needsUpdate");
        super.readData(compound);
    }
    
    @Override
    public void setNeedsUpdate(boolean needsUpdate)
    {
        this.needsUpdate = needsUpdate;
    }
    
    @Override
    public boolean getNeedsUpdate()
    {
        return needsUpdate;
    }
}