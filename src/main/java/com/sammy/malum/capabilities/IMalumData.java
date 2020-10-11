package com.sammy.malum.capabilities;

import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public interface IMalumData
{
    Optional<UUID> getRogueOwner();
    Optional<UUID> getCachedTarget();
    boolean getHusk();
    void setRogueOwner(UUID uuid);
    void setCachedTarget(UUID uuid);
    void setHusk(boolean husk);
    void copy(IMalumData data);
    void saveNBTData(CompoundNBT compound);
    void loadNBTData(CompoundNBT compound);
}
