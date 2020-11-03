package com.sammy.malum.blocks.utility;

import net.minecraft.nbt.CompoundNBT;


public interface IConfigurableTileEntity
{
    int getOption();
    void setOption(int option);
    public default CompoundNBT writeOption(CompoundNBT compound)
    {
        if (getOption() != 0)
        {
            compound.putInt("option", getOption());
        }
        return compound;
    }
    
    public default void readOption(CompoundNBT compound)
    {
        if (compound.contains("option"))
        {
            setOption(compound.getInt("option"));
        }
    }
}