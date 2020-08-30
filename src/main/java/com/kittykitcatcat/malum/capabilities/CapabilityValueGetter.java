package com.kittykitcatcat.malum.capabilities;

import com.kittykitcatcat.malum.network.packets.HuskChangePacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.kittykitcatcat.malum.network.NetworkManager.INSTANCE;

public class CapabilityValueGetter
{
    @CapabilityInject(CapabilityData.class)
    public static Capability<CapabilityData> CAPABILITY;
    public static LazyOptional<LivingEntity> getCachedTarget(LivingEntity player)
    {
        return player.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::getCachedTarget);
    }
    public static void setCachedTarget(Entity playerEntity, LivingEntity cachedTarget)
    {
        playerEntity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note -> note.cachedTarget = cachedTarget);
    }
    
    public static boolean getRogue(LivingEntity livingEntity)
    {
        return livingEntity.getCapability(CapabilityValueGetter.CAPABILITY).map(CapabilityData::getHusk).orElse(false);
    }
    public static void setRogue(LivingEntity livingEntity, boolean rouge)
    {
        livingEntity.getCapability(CapabilityValueGetter.CAPABILITY).ifPresent(note -> note.rogue = rouge);
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