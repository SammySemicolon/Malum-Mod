package com.kittykitcatcat.malum.capabilities;

import com.kittykitcatcat.malum.network.packets.HuskChangePacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.kittykitcatcat.malum.network.NetworkManager.INSTANCE;

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

    public static float getSpiritIntegrityMultiplier(LivingEntity player)
    {
        return player.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::getSpiritIntegrityMultiplier).orElse(1f);
    }
    public static void setSpiritIntegrityMultiplier(Entity playerEntity, float spiritIntegrityMultiplier)
    {
        playerEntity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note -> note.spiritIntegrityMultiplier = spiritIntegrityMultiplier);
    }

    public static boolean getHusk(LivingEntity livingEntity)
    {
        return livingEntity.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::getHusk).orElse(false);
    }
    public static void setHusk(LivingEntity livingEntity, boolean isHusk)
    {
        livingEntity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note -> note.husk = isHusk);
        if (isHusk)
        {
            if (livingEntity instanceof MobEntity)
            {
                ((MobEntity) livingEntity).setAttackTarget(null);
            }
        }
        if (livingEntity.world instanceof ServerWorld)
        {
            INSTANCE.send(
                    PacketDistributor.TRACKING_CHUNK.with(() -> livingEntity.world.getChunkAt(livingEntity.getPosition())),
                    new HuskChangePacket(livingEntity.getEntityId(), isHusk));
        }
    }
}