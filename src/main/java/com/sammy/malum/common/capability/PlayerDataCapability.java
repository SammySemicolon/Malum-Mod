package com.sammy.malum.common.capability;

import com.sammy.malum.common.packets.SyncPlayerCapabilityDataPacket;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.setup.PacketRegistry;
import com.sammy.malum.core.setup.content.SpiritAffinityRegistry;
import com.sammy.malum.core.systems.capability.SimpleCapability;
import com.sammy.malum.core.systems.capability.SimpleCapabilityProvider;
import com.sammy.malum.core.systems.spirit.MalumSpiritAffinity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.UUID;

public class PlayerDataCapability implements SimpleCapability {

    public static Capability<PlayerDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public UUID targetedSoulUUID;
    public int targetedSoulId;
    public int soulFetchCooldown;

    public boolean firstTimeJoin;

    public MalumSpiritAffinity affinity;

    public float soulWard;
    public float soulWardProgress;

    public float heartOfStone;
    public float heartOfStoneProgress;

    public PlayerDataCapability() {
    }

    public static void attachPlayerCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            final PlayerDataCapability capability = new PlayerDataCapability();
            event.addCapability(DataHelper.prefix("player_data"), new SimpleCapabilityProvider<>(PlayerDataCapability.CAPABILITY, () -> capability));
        }
    }

    public static void playerJoin(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Player player) {
            PlayerDataCapability.getCapability(player).ifPresent(capability -> capability.firstTimeJoin = true);
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
    public static void playerClone(PlayerEvent.Clone event)
    {
        PlayerDataCapability originalCapability = PlayerDataCapability.getCapability(event.getOriginal()).orElse(new PlayerDataCapability());
        PlayerDataCapability capability = PlayerDataCapability.getCapability(event.getPlayer()).orElse(new PlayerDataCapability());
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

        tag.putBoolean("firstTimeJoin", firstTimeJoin);

        if (affinity != null) {
            tag.putString("affinity", affinity.identifier);
        }
        tag.putFloat("soulWard", soulWard);
        tag.putFloat("soulWardProgress", soulWardProgress);

        tag.putFloat("heartOfStone", heartOfStone);
        tag.putFloat("heartOfStoneProgress", heartOfStoneProgress);

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

        firstTimeJoin = tag.getBoolean("firstTimeJoin");

        soulWard = tag.getFloat("soulWard");
        soulWardProgress = tag.getFloat("soulWardProgress");

        heartOfStone = tag.getFloat("heartOfStone");
        heartOfStoneProgress = tag.getInt("heartOfStoneProgress");
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
    public static void sync(Player player, PacketDistributor.PacketTarget target)
    {
        getCapability(player).ifPresent(c -> PacketRegistry.INSTANCE.send(target, new SyncPlayerCapabilityDataPacket(player.getUUID(), c.serializeNBT())));
    }

    public static LazyOptional<PlayerDataCapability> getCapability(Player player) {
        return player.getCapability(CAPABILITY);
    }
}
