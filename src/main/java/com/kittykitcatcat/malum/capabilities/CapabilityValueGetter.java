package com.kittykitcatcat.malum.capabilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

public class CapabilityValueGetter
{

    @CapabilityInject(CapabilityData.class)
    public static Capability<CapabilityData> CAPABILITY;
    public static boolean getIsHusk(LivingEntity player)
    {
        return player.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::isHusk).orElse(false);
    }
    public static float getDrainProgress(PlayerEntity player)
    {
        return player.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::getDrainProgress).orElse(0f);
    }

    public static void setDrainProgress(LivingEntity playerEntity, float drainProgress)
    {
        playerEntity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note ->
                note.setDrainProgress(drainProgress));
    }
    public static void setIsHusk(Entity playerEntity, boolean isHusk)
    {
        playerEntity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note ->
                note.setHusk(isHusk));
    }

    public static float getTeleportChargeTime(PlayerEntity player)
    {
        return player.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::getTeleortChargeTime).orElse(0f);
    }


    public static void setTeleportChargeTime(PlayerEntity playerEntity, float teleportChargeTime)
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
