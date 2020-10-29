package com.sammy.malum.capabilities;

import net.minecraft.nbt.CompoundNBT;

import java.util.Optional;
import java.util.UUID;

public class MalumData implements IMalumData
{
    public UUID spiritOwner;
    
    public UUID harvestTarget;
    
    public UUID gauntletTarget;
    
    public boolean dread;
    
    public boolean charm;
    
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
    public void setSpiritOwner(UUID uuid)
    {
        spiritOwner = uuid;
    }
    
    @Override
    public Optional<UUID> getHarvestTarget()
    {
        if (harvestTarget == null)
        {
            return Optional.empty();
        }
        return Optional.of(harvestTarget);
    }
    
    @Override
    public void setHarvestTarget(UUID uuid)
    {
        harvestTarget = uuid;
    }
    
    @Override
    public Optional<UUID> getGauntletTarget()
    {
        if (gauntletTarget == null)
        {
            return Optional.empty();
        }
        return Optional.of(gauntletTarget);
    }
    
    @Override
    public void setGauntletTarget(UUID uuid)
    {
        gauntletTarget = uuid;
    }
    
    @Override
    public boolean getDread()
    {
        return dread;
    }
    
    @Override
    public void setDread(boolean dread)
    {
        this.dread = dread;
    }
    
    @Override
    public boolean getCharm()
    {
        return charm;
    }
    
    @Override
    public void setCharm(boolean charm)
    {
        this.charm = charm;
    }
    
    @Override
    public void copy(IMalumData data)
    {
        if (data.getSpiritOwner().isPresent())
        {
            spiritOwner = data.getSpiritOwner().get();
        }
        if (data.getHarvestTarget().isPresent())
        {
            harvestTarget = data.getHarvestTarget().get();
        }
        charm = data.getCharm();
        dread = data.getDread();
    }
    
    @Override
    public CompoundNBT saveNBTData()
    {
        CompoundNBT nbt = new CompoundNBT();
        if (dread)
        {
            nbt.putBoolean("dread", true);
        }
        if (charm)
        {
            nbt.putBoolean("charm", true);
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
        if (compound.contains("dread"))
        {
            dread = compound.getBoolean("dread");
        }
        if (compound.contains("charm"))
        {
            charm = compound.getBoolean("charm");
        }
        if (compound.contains("spiritOwner"))
        {
            spiritOwner = compound.getUniqueId("spiritOwner");
        }
    }
}
