package com.sammy.malum.core.systems.heat;

import com.sammy.malum.core.recipes.SpiritKilnFuelData;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;

public class SimpleFuelSystem
{
    public final SimpleTileEntity owner;
    public final int maxFuel;
    public int fuel;
    public float speed;
    public SimpleFuelSystem(SimpleTileEntity owner, int maxFuel)
    {
        this.owner = owner;
        this.maxFuel = maxFuel;
    }
    
    public void readData(CompoundNBT compound)
    {
        fuel = compound.getInt("fuel");
        speed = compound.getFloat("speed");
    }
    
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putInt("fuel", fuel);
        compound.putFloat("speed", speed);
        return compound;
    }
    public boolean increase(SpiritKilnFuelData data)
    {
        if (fuel != maxFuel)
        {
            if (data.fuelSpeed != speed)
            {
                fuel = 0;
                speed = data.fuelSpeed;
            }
            fuel = Math.min(maxFuel, fuel + data.fuelDuration);
            return true;
        }
        return false;
    }
    public void wipe()
    {
        fuel = 0;
        speed = 0;
    }
}