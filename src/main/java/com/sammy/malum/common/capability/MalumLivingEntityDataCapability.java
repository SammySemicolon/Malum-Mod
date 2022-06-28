package com.sammy.malum.common.capability;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.packets.SyncLivingCapabilityDataPacket;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import com.sammy.ortus.systems.capability.OrtusCapability;
import com.sammy.ortus.systems.capability.OrtusCapabilityProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.UUID;

import static com.sammy.malum.core.setup.server.PacketRegistry.MALUM_CHANNEL;

public class MalumLivingEntityDataCapability implements OrtusCapability {

    public static Capability<MalumLivingEntityDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });


    public MalumEntitySpiritData spiritData;

    public float soulHarvestProgress;
    public float exposedSoul;
    public boolean soulless;
    public boolean spawnerSpawned;
    public UUID ownerUUID;

    public float getPreviewProgress() {
        return soulless ? 10 : Math.min(10, soulHarvestProgress);
    }

    public float getHarvestProgress() {
        return Math.max(0, soulHarvestProgress - 10);
    }

    public MalumLivingEntityDataCapability() {
    }

    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity) {
            final MalumLivingEntityDataCapability capability = new MalumLivingEntityDataCapability();
            event.addCapability(MalumMod.prefix("living_data"), new OrtusCapabilityProvider<>(MalumLivingEntityDataCapability.CAPABILITY, () -> capability));
        }
    }

    public static void syncEntityCapability(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof LivingEntity livingEntity) {
            if (livingEntity.level instanceof ServerLevel) {
                MalumLivingEntityDataCapability.sync(livingEntity);
            }
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        tag.putFloat("soulHarvestProgress", soulHarvestProgress);
        tag.putFloat("exposedSoul", exposedSoul);
        tag.putBoolean("soulless", soulless);
        tag.putBoolean("spawnerSpawned", spawnerSpawned);
        if (ownerUUID != null) {
            tag.putUUID("ownerUUID", ownerUUID);
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        soulHarvestProgress = tag.getFloat("soulHarvestProgress");
        exposedSoul = tag.getFloat("exposedSoul");
        soulless = tag.getBoolean("soulless");
        spawnerSpawned = tag.getBoolean("spawnerSpawned");
        if (tag.contains("ownerUUID")) {
            ownerUUID = tag.getUUID("ownerUUID");
        }
    }

    public static void sync(LivingEntity entity) {
        getCapabilityOptional(entity).ifPresent(c -> MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new SyncLivingCapabilityDataPacket(entity.getId(), c.serializeNBT())));
    }

    public static LazyOptional<MalumLivingEntityDataCapability> getCapabilityOptional(LivingEntity entity) {
        return entity.getCapability(CAPABILITY);
    }
    public static MalumLivingEntityDataCapability getCapability(LivingEntity entity) {
        return entity.getCapability(CAPABILITY).orElse(new MalumLivingEntityDataCapability());
    }

}