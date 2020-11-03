package com.sammy.malum.blocks.utility;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public class ConfigurableTileEntity extends BasicTileEntity implements IConfigurableTileEntity
{
    public int option;
    
    public ConfigurableTileEntity(TileEntityType<? extends ConfigurableTileEntity> tileEntityType)
    {
        super(tileEntityType);
    }
    
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        writeOption(compound);
        return compound;
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        readOption(compound);
    }
    
    @Override
    public int getOption()
    {
        return option;
    }
    
    @Override
    public void setOption(int option)
    {
        this.option = option;
    }
}