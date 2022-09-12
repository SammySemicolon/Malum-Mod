package com.sammy.malum.common.capability;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.packets.SyncMalumPlayerCapabilityDataPacket;
import com.sammy.malum.core.setup.content.SpiritAffinityRegistry;
import com.sammy.malum.core.setup.server.PacketRegistry;
import com.sammy.malum.core.systems.spirit.MalumSpiritAffinity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.systems.capability.LodestoneCapability;
import team.lodestar.lodestone.systems.capability.LodestoneCapabilityProvider;

import java.util.UUID;

public class MalumPlayerDataCapability implements LodestoneCapability {

    public static Capability<MalumPlayerDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public UUID targetedSoulUUID;
    public int targetedSoulId;
    public int soulFetchCooldown;

    public MalumSpiritAffinity affinity;

    public float soulWard;
    public float soulWardProgress;

    public float heartOfStone;
    public float heartOfStoneProgress;

    public int soulsShattered;
    public boolean obtainedEncyclopedia;

    public MalumPlayerDataCapability() {
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(MalumPlayerDataCapability.class);
    }

    public static void attachPlayerCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            final MalumPlayerDataCapability capability = new MalumPlayerDataCapability();
            event.addCapability(MalumMod.malumPath("player_data"), new LodestoneCapabilityProvider<>(MalumPlayerDataCapability.CAPABILITY, () -> capability));
        }
    }

    public static void playerJoin(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player instanceof ServerPlayer serverPlayer) {
                syncSelf(serverPlayer);
            }
        }
    }

    public static void syncPlayerCapability(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof Player player) {
            if (player.level instanceof ServerLevel) {
                syncTracking(player);
            }
        }
    }

    public static void playerClone(PlayerEvent.Clone event) {
        MalumPlayerDataCapability originalCapability = MalumPlayerDataCapability.getCapabilityOptional(event.getOriginal()).orElse(new MalumPlayerDataCapability());
        MalumPlayerDataCapability capability = MalumPlayerDataCapability.getCapabilityOptional(event.getPlayer()).orElse(new MalumPlayerDataCapability());
        capability.deserializeNBT(originalCapability.serializeNBT());
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        if (targetedSoulUUID != null) {
            tag.putUUID("targetedSoulUUID", targetedSoulUUID);
        }
        tag.putInt("targetedSoulId", targetedSoulId);
        tag.putInt("soulFetchCooldown", soulFetchCooldown);

        if (affinity != null) {
            tag.putString("affinity", affinity.identifier);
        }
        tag.putFloat("soulWard", soulWard);
        tag.putFloat("soulWardProgress", soulWardProgress);

        tag.putFloat("heartOfStone", heartOfStone);
        tag.putFloat("heartOfStoneProgress", heartOfStoneProgress);

        tag.putInt("soulsShattered", soulsShattered);
        tag.putBoolean("obtainedEncyclopedia", obtainedEncyclopedia);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        affinity = SpiritAffinityRegistry.AFFINITIES.get(tag.getString("affinity"));

        if (tag.contains("targetedSoulUUID")) {
            targetedSoulUUID = tag.getUUID("targetedSoulUUID");
        }
        targetedSoulId = tag.getInt("targetedSoulId");
        soulFetchCooldown = tag.getInt("soulFetchCooldown");

        soulWard = tag.getFloat("soulWard");
        soulWardProgress = tag.getFloat("soulWardProgress");

        heartOfStone = tag.getFloat("heartOfStone");
        heartOfStoneProgress = tag.getInt("heartOfStoneProgress");

        soulsShattered = tag.getInt("soulsShattered");
        obtainedEncyclopedia = tag.getBoolean("obtainedEncyclopedia");
    }

    public static void syncSelf(ServerPlayer player) {
        sync(player, PacketDistributor.PLAYER.with(() -> player));
    }

    public static void syncTrackingAndSelf(Player player) {
        sync(player, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player));
    }

    public static void syncTracking(Player player) {
        sync(player, PacketDistributor.TRACKING_ENTITY.with(() -> player));
    }

    public static void sync(Player player, PacketDistributor.PacketTarget target) {
        getCapabilityOptional(player).ifPresent(c -> PacketRegistry.MALUM_CHANNEL.send(target, new SyncMalumPlayerCapabilityDataPacket(player.getUUID(), c.serializeNBT())));
    }

    public static LazyOptional<MalumPlayerDataCapability> getCapabilityOptional(Player player) {
        return player.getCapability(CAPABILITY);
    }

    public static MalumPlayerDataCapability getCapability(Player player) {
        return player.getCapability(CAPABILITY).orElse(new MalumPlayerDataCapability());
    }
}