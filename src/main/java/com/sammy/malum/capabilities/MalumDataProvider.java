package com.sammy.malum.capabilities;

import com.sammy.malum.network.packets.HuskChangePacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

import static com.sammy.malum.network.NetworkManager.INSTANCE;

public class MalumDataProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(IMalumData.class)
    public static final Capability<IMalumData> CAPABILITY = null;

    private LazyOptional<IMalumData> instance = LazyOptional.of(CAPABILITY::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CAPABILITY.getStorage().writeNBT(CAPABILITY, this.instance.orElseThrow(()->new IllegalArgumentException()), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CAPABILITY.getStorage().readNBT(CAPABILITY,this.instance.orElseThrow(()->new IllegalArgumentException()), null,nbt);
    }
    
    public static UUID getCachedTarget(PlayerEntity player)
    {
        if (player.getCapability(CAPABILITY).isPresent())
        {
            if (player.getCapability(CAPABILITY).map(IMalumData::getCachedTarget).isPresent())
            {
                return player.getCapability(CAPABILITY).map(IMalumData::getCachedTarget).get().orElse(null);
            }
        }
        return null;
    }
    public static void setCachedTarget(PlayerEntity entity, UUID cachedTarget)
    {
        if (entity.getCapability(CAPABILITY).isPresent())
        {
            entity.getCapability(CAPABILITY).ifPresent(note -> note.setCachedTarget(cachedTarget));
        }
    }
    
    public static UUID getSpiritOwner(LivingEntity livingEntity)
    {
        if (livingEntity.getCapability(CAPABILITY).isPresent())
        {
            if (livingEntity.getCapability(CAPABILITY).map(IMalumData::getSpiritOwner).isPresent())
            {
                return livingEntity.getCapability(CAPABILITY).map(IMalumData::getSpiritOwner).get().orElse(null);
            }
        }
        return null;
    }
    public static void setSpiritOwner(LivingEntity livingEntity, UUID spiritOwner)
    {
        if (livingEntity.getCapability(CAPABILITY).isPresent())
        {
            livingEntity.getCapability(CAPABILITY).ifPresent(note -> note.setSpiritOwner(spiritOwner));
        }
        if (livingEntity instanceof MobEntity)
        {
            MobEntity mobEntity = (MobEntity) livingEntity;
            NearestAttackableTargetGoal<MobEntity> targetGoal = new NearestAttackableTargetGoal<>(mobEntity, MobEntity.class, 0, false, false, EntityPredicates.IS_LIVING_ALIVE);
            mobEntity.goalSelector.addGoal(621, targetGoal);
        }
    }
    
    public static boolean getDread(LivingEntity livingEntity)
    {
        if (livingEntity.getCapability(CAPABILITY).isPresent())
        {
            return livingEntity.getCapability(CAPABILITY).map(IMalumData::getDread).get();
        }
        return false;
    }
    public static boolean getCharm(LivingEntity livingEntity)
    {
        if (livingEntity.getCapability(CAPABILITY).isPresent())
        {
            return livingEntity.getCapability(CAPABILITY).map(IMalumData::getCharm).get();
        }
        return false;
    }
    public static void setDread(LivingEntity livingEntity, boolean dread)
    {
        if (livingEntity.getCapability(CAPABILITY).isPresent())
        {
            livingEntity.getCapability(CAPABILITY).ifPresent(note -> note.setDread(dread));
        }
        if (dread)
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
                    new HuskChangePacket(livingEntity.getEntityId(), dread,false));
        }
    }
    public static void setCharm(LivingEntity livingEntity, boolean charm)
    {
        if (livingEntity.getCapability(CAPABILITY).isPresent())
        {
            livingEntity.getCapability(CAPABILITY).ifPresent(note -> note.setCharm(charm));
        }
        if (charm)
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
                    new HuskChangePacket(livingEntity.getEntityId(), false, charm));
        }
    }
}
