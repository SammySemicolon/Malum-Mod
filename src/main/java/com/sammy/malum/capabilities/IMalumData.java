package com.sammy.malum.capabilities;

import net.minecraft.nbt.CompoundNBT;

import java.util.Optional;
import java.util.UUID;

public interface IMalumData
{
    boolean getDread();
    
    void setDread(boolean dread);
    
    boolean getCharm();
    
    void setCharm(boolean charm);
    
    Optional<UUID> getSpiritOwner();
    
    void setSpiritOwner(UUID uuid);
    
    Optional<UUID> getHarvestTarget();
    
    void setHarvestTarget(UUID uuid);
    
    Optional<UUID> getGauntletTarget();
    
    void setGauntletTarget(UUID uuid);
    
    void copy(IMalumData data);
    
    CompoundNBT saveNBTData();
    
    void loadNBTData(CompoundNBT compound);
}
