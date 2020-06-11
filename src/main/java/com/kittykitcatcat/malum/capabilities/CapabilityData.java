package com.kittykitcatcat.malum.capabilities;

import net.minecraft.nbt.CompoundNBT;

public class CapabilityData
{
    boolean husk;

    public boolean isHusk()
    {
        return husk;
    }

    public void setHusk(boolean husk)
    {
        this.husk = husk;
    }

    public CapabilityData()
    {
    }
    public void copyFrom(CapabilityData source)
    {
    }


    public void saveNBTData(CompoundNBT compound)
    {
        compound.putBoolean("husk", husk);
    }

    public void loadNBTData(CompoundNBT compound)
    {
        husk = compound.getBoolean("husk");
    }
}
