package com.sammy.malum.core.systems.heat;

import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;

public class SimpleHeatSystem
{
    public final SimpleTileEntity owner;
    public float heat;
    public boolean needsUpdate;
    public SimpleHeatSystem(SimpleTileEntity owner)
    {
        this.owner = owner;
    }
    
    public void readData(CompoundNBT compound)
    {
        heat = compound.getFloat("heat");
        needsUpdate = compound.getBoolean("needsUpdate");
    }
    
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putFloat("heat", heat);
        compound.putBoolean("needsUpdate", needsUpdate);
        return compound;
    }
    public void updateHeat()
    {
        if (needsUpdate)
        {
            if (owner.getWorld().getBlockState(owner.getPos().down()).getBlock().equals(Blocks.MAGMA_BLOCK))
            {
                heat = 0.5f;
            }
            else
            {
                heat = 0f;
            }
            needsUpdate = false;
        }
    }
}