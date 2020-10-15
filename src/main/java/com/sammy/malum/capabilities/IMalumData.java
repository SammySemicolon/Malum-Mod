package com.sammy.malum.capabilities;

import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public interface IMalumData
{
    boolean getHusk();
    void setSpiritOwner(UUID uuid);
    Optional<UUID> getSpiritOwner();
    void setCachedTarget(UUID uuid);
    Optional<UUID> getCachedTarget();
    void setHusk(boolean husk);
    void copy(IMalumData data);
    CompoundNBT saveNBTData();
    void loadNBTData(CompoundNBT compound);
}
