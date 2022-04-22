package com.sammy.malum.common.packets;

import com.sammy.malum.common.capability.LivingEntityDataCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class SyncLivingCapabilityDataPacket {
    private final int entityId;
    private final CompoundTag tag;

    public SyncLivingCapabilityDataPacket(int entityId, CompoundTag tag) {
        this.entityId = entityId;
        this.tag = tag;
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SyncLivingCapabilityDataPacket.class, SyncLivingCapabilityDataPacket::encode, SyncLivingCapabilityDataPacket::decode, SyncLivingCapabilityDataPacket::execute);
    }

    public static SyncLivingCapabilityDataPacket decode(FriendlyByteBuf buf) {
        return new SyncLivingCapabilityDataPacket(buf.readInt(), buf.readNbt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeNbt(tag);
    }

    public void execute(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ClientOnly.syncData(entityId, tag);
            }
        });
        context.get().setPacketHandled(true);
    }

    public static class ClientOnly {
        public static void syncData(int entityId, CompoundTag tag) {
            Entity entity = Minecraft.getInstance().level.getEntity(entityId);
            if (entity instanceof LivingEntity livingEntity) {
                LivingEntityDataCapability.getCapability(livingEntity).ifPresent(c -> c.deserializeNBT(tag));
            }
        }
    }
}