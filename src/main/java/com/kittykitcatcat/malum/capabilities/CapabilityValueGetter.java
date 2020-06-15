package com.kittykitcatcat.malum.capabilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapabilityValueGetter
{
    @CapabilityInject(CapabilityData.class)
    public static Capability<CapabilityData> CAPABILITY;

    public static int getExtraSpirits(LivingEntity player)
    {
        return player.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::getExtraSpirits).orElse(1);
    }
    public static void setExtraSpirits(Entity playerEntity, int extraSpirits)
    {
        playerEntity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note -> note.extraSpirits = extraSpirits);
    }

    public static float getExtraHuskDamage(LivingEntity player)
    {
        return player.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::getExtraHuskDamage).orElse(1f);
    }
    public static void setExtraHuskDamage(Entity playerEntity, float extraHuskDamage)
    {
        playerEntity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note -> note.extraHuskDamage = extraHuskDamage);
    }

    public static float getHarvestSpeedMultiplayer(LivingEntity player)
    {
        return player.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::getHarvestSpeedMultiplayer).orElse(1f);
    }
    public static void setHarvestSpeedMultiplayer(Entity playerEntity, float harvestSpeedMultiplayer)
    {
        playerEntity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note -> note.harvestSpeedMultiplayer = harvestSpeedMultiplayer);
    }

    public static boolean getHusk(LivingEntity player)
    {
        return player.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::getHusk).orElse(false);
    }
    public static void setHusk(Entity playerEntity, boolean isHusk)
    {
        playerEntity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note -> note.husk = isHusk);
    }
}