package com.sammy.malum.capabilities;

import net.minecraft.nbt.CompoundNBT;

import java.util.Optional;
import java.util.UUID;

public class MalumData implements IMalumData
{
    public UUID spiritOwner;
    
    public UUID cachedTarget;
    
    boolean husk;
    
    @Override
    public Optional<UUID> getSpiritOwner()
    {
        if (spiritOwner == null)
        {
            return Optional.empty();
        }
        return Optional.of(spiritOwner);
    }
    
    @Override
    public Optional<UUID> getCachedTarget()
    {
        if (cachedTarget == null)
        {
            return Optional.empty();
        }
        return Optional.of(cachedTarget);
    }
    
    @Override
    public boolean getHusk()
    {
        return husk;
    }
    
    @Override
    public void setSpiritOwner(UUID uuid)
    {
        spiritOwner =uuid;
    }
    
    @Override
    public void setCachedTarget(UUID uuid)
    {
        cachedTarget = uuid;
    }
    
    @Override
    public void setHusk(boolean husk)
    {
        this.husk = husk;
    }
    
    @Override
    public void copy(IMalumData data)
    {
        if (data.getSpiritOwner().isPresent())
        {
            spiritOwner = data.getSpiritOwner().get();
        }
        if (data.getCachedTarget().isPresent())
        {
            cachedTarget = data.getCachedTarget().get();
        }
        husk = data.getHusk();
    }
    
    @Override
    public CompoundNBT saveNBTData()
    {
        CompoundNBT nbt = new CompoundNBT();
        if (husk)
        {
            nbt.putBoolean("husk", true);
        }
        if (spiritOwner != null)
        {
            nbt.putUniqueId("spiritOwner", spiritOwner);
        }
        return nbt;
    }
    
    @Override
    public void loadNBTData(CompoundNBT compound)
    {
        if (compound.contains("husk"))
        {
            husk = compound.getBoolean("husk");
        }
        if (compound.contains("rogueOwner"))
        {
            spiritOwner = compound.getUniqueId("spiritOwner");
        }
    }
}
