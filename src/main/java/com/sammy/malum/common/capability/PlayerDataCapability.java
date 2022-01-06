package com.sammy.malum.common.capability;

import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.registry.content.SpiritAffinityRegistry;
import com.sammy.malum.core.systems.capability.SimpleCapability;
import com.sammy.malum.core.systems.capability.SimpleCapabilityProvider;
import com.sammy.malum.core.systems.spirit.MalumSpiritAffinity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import java.util.UUID;

public class PlayerDataCapability implements SimpleCapability {

    public static Capability<PlayerDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public UUID targetedSoulUUID;
    public int targetedSoulId;
    public Vec3 targetedSoulPosition;
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
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        if (targetedSoulUUID != null) {
            tag.putUUID("targetedSoulUUID", targetedSoulUUID);
        }
        tag.putInt("targetedSoulId", targetedSoulId);
        if (targetedSoulPosition != null)
        {
            tag.putDouble("targetedSoulPositionX", targetedSoulPosition.x);
            tag.putDouble("targetedSoulPositionY", targetedSoulPosition.y);
            tag.putDouble("targetedSoulPositionZ", targetedSoulPosition.z);
        }
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
        if (tag.contains("targetedSoulPositionX"))
        {
            targetedSoulPosition = new Vec3(tag.getDouble("targetedSoulPositionX"),tag.getDouble("targetedSoulPositionY"),tag.getDouble("targetedSoulPositionZ"));
        }
        soulFetchCooldown = tag.getInt("soulFetchCooldown");

        firstTimeJoin = tag.getBoolean("firstTimeJoin");

        soulWard = tag.getFloat("soulWard");
        soulWardProgress = tag.getFloat("soulWardProgress");

        heartOfStone = tag.getFloat("heartOfStone");
        heartOfStoneProgress = tag.getInt("heartOfStoneProgress");
    }

    public static LazyOptional<PlayerDataCapability> getCapability(Player player) {
        return player.getCapability(CAPABILITY);
    }

    public static LivingEntity getTargetedSoul(ServerPlayer player) {
        Entity entity = player.getLevel().getEntity(player.getCapability(CAPABILITY).orElse(new PlayerDataCapability()).targetedSoulUUID);
        if (entity instanceof LivingEntity livingEntity)
        {
            return livingEntity;
        }
        return null;
    }
}
