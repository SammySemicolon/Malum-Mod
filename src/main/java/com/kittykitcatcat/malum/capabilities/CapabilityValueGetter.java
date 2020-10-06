package com.kittykitcatcat.malum.capabilities;

import com.kittykitcatcat.malum.network.packets.HuskChangePacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Optional;
import java.util.UUID;

import static com.kittykitcatcat.malum.network.NetworkManager.INSTANCE;

public class CapabilityValueGetter
{
    @CapabilityInject(CapabilityData.class)
    public static Capability<CapabilityData> CAPABILITY;
    public static UUID getCachedTarget(LivingEntity player)
    {
        if (player.getCapability(CapabilityValueGetter.CAPABILITY).isPresent())
        {
            return player.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::getCachedTarget).get();
        }
        return null;
    }
    public static void setCachedTarget(Entity entity, UUID cachedTarget)
    {
        if (entity.getCapability(CapabilityValueGetter.CAPABILITY).isPresent())
        {
            entity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note -> note.cachedTarget = cachedTarget);
        }
    }
    
    public static boolean getRogue(LivingEntity livingEntity)
    {
        if (livingEntity.getCapability(CapabilityValueGetter.CAPABILITY).isPresent())
        {
            return livingEntity.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::getHusk).orElse(false);
        }
        return false;
    }
    public static UUID getRogueOwner(LivingEntity livingEntity)
    {
        if (livingEntity.getCapability(CapabilityValueGetter.CAPABILITY).isPresent())
        {
            return livingEntity.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::getRogueOwner).orElse(null);
        }
        return null;
    }
    public static void setRogue(PlayerEntity playerEntity, LivingEntity livingEntity, boolean rouge)
    {
        livingEntity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note -> note.rogue = rouge);
        livingEntity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note -> note.rogueOwner = playerEntity.getUniqueID());
    
        if (livingEntity instanceof MobEntity)
        {
            MobEntity mobEntity = (MobEntity) livingEntity;
            NearestAttackableTargetGoal<MobEntity> targetGoal = new NearestAttackableTargetGoal<>(mobEntity, MobEntity.class, 0, false, false, EntityPredicates.IS_LIVING_ALIVE);
            mobEntity.goalSelector.addGoal(621, targetGoal);
        }
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