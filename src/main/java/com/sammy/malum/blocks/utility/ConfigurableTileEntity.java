package com.sammy.malum.blocks.utility;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public class ConfigurableTileEntity extends BasicTileEntity
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
        if (option != 0)
        {
            compound.putInt("option", option);
        }
        return compound;
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        if (compound.contains("option"))
        {
            option = compound.getInt("option");
        }
    }
}