package com.sammy.malum.capabilities;

import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public interface IMalumData
{
    boolean getDread();
    void setDread(boolean dread);
    
    boolean getCharm();
    void setCharm(boolean charm);
    
    void setSpiritOwner(UUID uuid);
    Optional<UUID> getSpiritOwner();
    
    void setCachedTarget(UUID uuid);
    Optional<UUID> getCachedTarget();
    
    void copy(IMalumData data);
    CompoundNBT saveNBTData();
    void loadNBTData(CompoundNBT compound);
}
