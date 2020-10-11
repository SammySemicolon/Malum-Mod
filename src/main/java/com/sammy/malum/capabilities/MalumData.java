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
        return Optional.of(rogueOwner);
    }
    
    @Override
    public Optional<UUID> getCachedTarget()
    {
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
    public void saveNBTData(CompoundNBT compound)
    {
        compound.putBoolean("husk", husk);
        compound.putUniqueId("rogueOwner", rogueOwner);
    }
    
    @Override
    public void loadNBTData(CompoundNBT compound)
    {
        husk = compound.getBoolean("husk");
        rogueOwner = compound.getUniqueId("rogueOwner");
    }
}
