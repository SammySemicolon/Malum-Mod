package com.sammy.malum.capabilities;

import net.minecraft.nbt.CompoundNBT;

import java.util.Optional;
import java.util.UUID;

public class MalumData implements IMalumData
{
    public UUID rogueOwner;
    
    public UUID cachedTarget;
    
    boolean husk;
    
    @Override
    public Optional<UUID> getRogueOwner()
    {
        if (rogueOwner == null)
        {
            return Optional.empty();
        }
        return Optional.of(rogueOwner);
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
    public void setRogueOwner(UUID uuid)
    {
        rogueOwner =uuid;
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
        if (data.getRogueOwner().isPresent())
        {
            rogueOwner = data.getRogueOwner().get();
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
        if (rogueOwner != null)
        {
            nbt.putUniqueId("rogueOwner", rogueOwner);
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
            rogueOwner = compound.getUniqueId("rogueOwner");
        }
    }
}
