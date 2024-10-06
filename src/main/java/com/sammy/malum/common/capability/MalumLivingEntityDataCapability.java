package com.sammy.malum.common.capability;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.packets.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.systems.capability.LodestoneCapability;
import team.lodestar.lodestone.systems.capability.LodestoneCapabilityProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;

public class MalumLivingEntityDataCapability implements LodestoneCapability {

    public static Capability<MalumLivingEntityDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public SoulDataHandler soulData = new SoulDataHandler();
    public MalignantConversionHandler malignantConversionHandler = new MalignantConversionHandler();
    public TouchOfDarknessHandler touchOfDarknessHandler = new TouchOfDarknessHandler();

    public int watcherNecklaceCooldown;
    public int hiddenBladeCounterCooldown;

    public List<ItemStack> soulsToApplyToDrops;
    public UUID killerUUID;

    public MalumLivingEntityDataCapability() {
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(MalumLivingEntityDataCapability.class);
    }

    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity) {
            final MalumLivingEntityDataCapability capability = new MalumLivingEntityDataCapability();
            event.addCapability(MalumMod.malumPath("living_data"), new LodestoneCapabilityProvider<>(MalumLivingEntityDataCapability.CAPABILITY, () -> capability));
        }
    }

    public static void syncEntityCapability(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof LivingEntity livingEntity) {
            if (livingEntity.level() instanceof ServerLevel) {
                MalumLivingEntityDataCapability.syncTracking(livingEntity);
            }
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        tag.put("soulData", soulData.serializeNBT());
        tag.put("darknessAfflictionData", touchOfDarknessHandler.serializeNBT());

        if (watcherNecklaceCooldown > 0) {
            tag.putInt("watcherNecklaceCooldown", watcherNecklaceCooldown);
        }
        if (hiddenBladeCounterCooldown > 0) {
            tag.putInt("hiddenBladeCounterCooldown", hiddenBladeCounterCooldown);
        }
        if (soulsToApplyToDrops != null) {
            ListTag souls = new ListTag();
            for (ItemStack soul : soulsToApplyToDrops) {
                souls.add(soul.serializeNBT());
            }
            tag.put("soulsToApplyToDrops", souls);
        }
        if (killerUUID != null) {
            tag.putUUID("killerUUID", killerUUID);
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("soulData")) {
            soulData.deserializeNBT(tag.getCompound("soulData"));
        }
        if (tag.contains("darknessAfflictionData")) {
            touchOfDarknessHandler.deserializeNBT(tag.getCompound("darknessAfflictionData"));
        }
        watcherNecklaceCooldown = tag.contains("watcherNecklaceCooldown") ? tag.getInt("watcherNecklaceCooldown") : 0;
        hiddenBladeCounterCooldown = tag.contains("hiddenBladeCounterCooldown") ? tag.getInt("hiddenBladeCounterCooldown") : 0;

        if (tag.contains("soulsToApplyToDrops", Tag.TAG_LIST)) {
            soulsToApplyToDrops = new ArrayList<>();
            ListTag souls = tag.getList("soulsToApplyToDrops", Tag.TAG_COMPOUND);
            for (int i = 0; i < souls.size(); i++) {
                soulsToApplyToDrops.add(ItemStack.of(souls.getCompound(i)));
            }
        } else {
            soulsToApplyToDrops = null;
        }

        if (tag.hasUUID("killerUUID")) {
            killerUUID = tag.getUUID("killerUUID");
        } else {
            killerUUID = null;
        }
    }

    public static void syncSelf(ServerPlayer player) {
        sync(player, PacketDistributor.PLAYER.with(() -> player));
    }

    public static void syncTrackingAndSelf(ServerPlayer player) {
        sync(player, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player));
    }

    public static void syncTracking(LivingEntity entity) {
        sync(entity, PacketDistributor.TRACKING_ENTITY.with(() -> entity));
    }

    public static void sync(LivingEntity entity, PacketDistributor.PacketTarget target) {
        getCapabilityOptional(entity).ifPresent(c -> PacketRegistry.MALUM_CHANNEL.send(target, new SyncLivingCapabilityDataPacket(entity.getId(), c.serializeNBT())));
    }

    public static LazyOptional<MalumLivingEntityDataCapability> getCapabilityOptional(LivingEntity entity) {
        return entity.getCapability(CAPABILITY);
    }

    public static MalumLivingEntityDataCapability getCapability(LivingEntity entity) {
        return entity.getCapability(CAPABILITY).orElse(new MalumLivingEntityDataCapability());
    }
}