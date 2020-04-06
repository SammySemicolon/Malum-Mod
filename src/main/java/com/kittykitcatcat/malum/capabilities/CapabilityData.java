package com.kittykitcatcat.malum.capabilities;

import net.minecraft.nbt.CompoundNBT;
import org.antlr.v4.runtime.misc.NotNull;

public class CapabilityData
{

    float drainProgress;

    boolean isHusk;

    public float getDrainProgress()
    {
        return drainProgress;
    }

    public void setDrainProgress(float drainProgress)
    {
        this.drainProgress = drainProgress;
    }

    public boolean isHusk()
    {
        return isHusk;
    }

    public void setHusk(boolean husk)
    {
        isHusk = husk;
    }

    //phantom necklace
    double startedFlying;

    @NotNull
    public double getStartedFlying()
    {
        return startedFlying;
    }

    public void setStartedFlying(double startedFlying)
    {
        this.startedFlying = startedFlying;
    }

    boolean canFly;

    @NotNull
    public boolean getCanFly()
    {
        return canFly;
    }

    public void setCanFly(boolean canFly)
    {
        this.canFly = canFly;
    }

    double totalFlightTime;

    //ender staff
    boolean isTeleporting;
    double teleportChargeTime;

    public CapabilityData()
    {
    }

    @NotNull
    public double getTeleortChargeTime()
    {
        return teleportChargeTime;
    }

    @NotNull
    public boolean getTeleporting()
    {
        return isTeleporting;
    }

    public void setIsTeleporting(boolean canTeleport)
    {
        this.isTeleporting = canTeleport;
    }

    public void setTeleportChargeTime(double teleportChargeTime)
    {
        this.teleportChargeTime = teleportChargeTime;
    }

    @NotNull
    public double getTotalFlightTime()
    {
        return totalFlightTime;
    }

    public void setTotalFlightTime(double totalFlightTime)
    {
        this.totalFlightTime = totalFlightTime;
    }

    public void copyFrom(CapabilityData source)
    {
        startedFlying = source.startedFlying;
        canFly = source.canFly;
        totalFlightTime = source.totalFlightTime;
        teleportChargeTime = source.teleportChargeTime;
        isTeleporting = source.isTeleporting;
    }


    public void saveNBTData(CompoundNBT compound)
    {
        compound.putBoolean("husk", isHusk);
    }

    public void loadNBTData(CompoundNBT compound)
    {
        isHusk = compound.getBoolean("husk");
    }
}
