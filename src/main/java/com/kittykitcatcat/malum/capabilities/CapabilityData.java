package com.kittykitcatcat.malum.capabilities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CapabilityData
{
    public void copyFrom(CapabilityData source)
    {
    }

    public void saveNBTData(CompoundNBT compound)
    {
        compound.putBoolean("husk", husk);
        compound.putBoolean("rogue", rogue);
        compound.putUniqueId("rogueOwner", rogueOwner);
    }
    public void loadNBTData(CompoundNBT compound)
    {
        husk = compound.getBoolean("husk");
        rogue = compound.getBoolean("rogue");
        rogueOwner = compound.getUniqueId("rogueOwner");
    }
    public UUID rogueOwner;
    
    public UUID getRogueOwner()
    {
        return rogueOwner;
    }
    
    public UUID cachedTarget;

    public UUID getCachedTarget()
    {
        return cachedTarget;
    }

    boolean husk;
    
    public boolean getHusk()
    {
        return husk;
    }
    
    public boolean rogue;
    
    public boolean getRogue()
    {
        return rogue;
    }
    
}
