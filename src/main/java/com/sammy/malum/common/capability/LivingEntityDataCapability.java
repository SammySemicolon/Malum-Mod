package com.sammy.malum.common.capability;

import com.sammy.malum.common.packets.SyncLivingCapabilityDataPacket;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.systems.capability.SimpleCapability;
import com.sammy.malum.core.systems.capability.SimpleCapabilityProvider;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.UUID;

import static com.sammy.malum.core.registry.PacketRegistry.INSTANCE;

public class LivingEntityDataCapability implements SimpleCapability {

    public static Capability<LivingEntityDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });


    public MalumEntitySpiritData spiritData;

    public float soulHarvestProgress;
    public float exposedSoul;
    public boolean soulless;
    public UUID ownerUUID;

    public float getPreviewProgress()
    {
        return soulless ? 10 : Math.min(10, soulHarvestProgress);
    }
    public float getHarvestProgress()
    {
        return Math.max(0, soulHarvestProgress-10);
    }

    public LivingEntityDataCapability() {
    }

    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity) {
            final LivingEntityDataCapability capability = new LivingEntityDataCapability();
            event.addCapability(DataHelper.prefix("living_data"), new SimpleCapabilityProvider<>(LivingEntityDataCapability.CAPABILITY, () -> capability));
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        tag.putFloat("soulHarvestProgress", soulHarvestProgress);
        tag.putFloat("exposedSoul", exposedSoul);
        tag.putBoolean("soulless", soulless);
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
        if (tag.contains("ownerUUID")) {
            ownerUUID = tag.getUUID("ownerUUID");
        }
    }

    public static void sync(LivingEntity entity)
    {
        getCapability(entity).ifPresent(c -> INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new SyncLivingCapabilityDataPacket(entity.getId(), c.serializeNBT())));
    }
    public static LazyOptional<LivingEntityDataCapability> getCapability(LivingEntity entity) {
        return entity.getCapability(CAPABILITY);
    }
    public static UUID getOwner(LivingEntity entity) {
        return entity.getCapability(CAPABILITY).orElse(new LivingEntityDataCapability()).ownerUUID;
    }
    public static boolean isSoulless(LivingEntity entity) {
        return entity.getCapability(CAPABILITY).orElse(new LivingEntityDataCapability()).soulless;
    }
    public static boolean hasSpiritData(LivingEntity entity) {
        return entity.getCapability(CAPABILITY).orElse(new LivingEntityDataCapability()).spiritData != null;
    }
}
