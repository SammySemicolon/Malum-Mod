package com.kittykitcatcat.malum.capabilities;

import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.List;

public class CapabilityData
{
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

    public float harvestSpeedMultiplayer;
    public float getHarvestSpeedMultiplayer()
    {
        return harvestSpeedMultiplayer;
    }

    public int extraSpirits;
    public int getExtraSpirits()
    {
        return extraSpirits;
    }

    public float extraHuskDamage;

    public float getExtraHuskDamage()
    {
        return extraHuskDamage;
    }

    boolean husk;

    public boolean getHusk()
    {
        return husk;
    }
}
