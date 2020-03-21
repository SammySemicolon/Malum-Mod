package com.kittykitcatcat.malum.capabilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapabilityValueGetter
{

    @CapabilityInject(CapabilityData.class)
    public static Capability<CapabilityData> CAPABILITY;


    public static boolean getIsTeleporting(PlayerEntity player)
    {
        return player.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::getTeleporting).orElse(false);
    }

    public static double getTeleportChargeTime(PlayerEntity player)
    {
        return player.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::getTeleortChargeTime).orElse(0d);
    }

    public static void setIsTeleporting(PlayerEntity playerEntity, boolean isTeleporting)
    {
        playerEntity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note ->
            note.setIsTeleporting(isTeleporting));
    }


    public static void setTeleportChargeTime(PlayerEntity playerEntity, double teleportChargeTime)
    {
        playerEntity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note ->
            note.setTeleportChargeTime(teleportChargeTime));
    }

    public static double getAvaiableFlightTime(PlayerEntity player)
    {
        return player.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::getStartedFlying).orElse(0d);
    }

    public static boolean getCanFly(PlayerEntity player)
    {
        return player.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::getCanFly).orElse(false);
    }

    public static double getTotalFlightTime(PlayerEntity player)
    {
        return player.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::getTotalFlightTime).orElse(0d);
    }

    public static void setCanFly(PlayerEntity playerEntity, boolean canFly)
    {
        playerEntity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note ->
            note.setCanFly(canFly));
    }

    public static void setAvaiableFlightTime(PlayerEntity playerEntity, double avaiableFlightTime)
    {
        playerEntity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note ->
            note.setStartedFlying(avaiableFlightTime));
    }

    public static void setTotalFlightTime(PlayerEntity playerEntity, double totalFlightTime)
    {
        playerEntity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note ->
            note.setTotalFlightTime(totalFlightTime));
    }
}
