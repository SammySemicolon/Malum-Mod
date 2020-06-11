package com.kittykitcatcat.malum.capabilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapabilityValueGetter
{
    @CapabilityInject(CapabilityData.class)
    public static Capability<CapabilityData> CAPABILITY;
    public static boolean getHusk(LivingEntity player)
    {
        return player.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::isHusk).orElse(false);
    }
    public static void setHusk(Entity playerEntity, boolean isHusk)
    {
        playerEntity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note ->
                note.setHusk(isHusk));
    }
}
