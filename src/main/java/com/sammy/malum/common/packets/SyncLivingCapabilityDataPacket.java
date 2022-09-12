package com.sammy.malum.common.packets;

import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import team.lodestar.lodestone.systems.network.LodestoneClientPacket;

import java.util.function.Supplier;

public class SyncLivingCapabilityDataPacket extends LodestoneClientPacket {
    private final int entityId;
    private final CompoundTag tag;

    public SyncLivingCapabilityDataPacket(int entityId, CompoundTag tag) {
        this.entityId = entityId;
        this.tag = tag;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeNbt(tag);
    }

    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Entity entity = Minecraft.getInstance().level.getEntity(entityId);
        if (entity instanceof LivingEntity livingEntity) {
            MalumLivingEntityDataCapability.getCapabilityOptional(livingEntity).ifPresent(c -> c.deserializeNBT(tag));
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SyncLivingCapabilityDataPacket.class, SyncLivingCapabilityDataPacket::encode, SyncLivingCapabilityDataPacket::decode, SyncLivingCapabilityDataPacket::handle);
    }

    public static SyncLivingCapabilityDataPacket decode(FriendlyByteBuf buf) {
        return new SyncLivingCapabilityDataPacket(buf.readInt(), buf.readNbt());
    }
}