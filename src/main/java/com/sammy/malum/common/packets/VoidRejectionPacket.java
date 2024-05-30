package com.sammy.malum.common.packets;

import com.sammy.malum.common.components.MalumComponents;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.systems.network.LodestoneClientPacket;

public class VoidRejectionPacket extends LodestoneClientPacket {
    private final int entityId;

    public VoidRejectionPacket(int entityId) {
        this.entityId = entityId;
    }

    public VoidRejectionPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void executeClient(Minecraft client, ClientPacketListener listener, PacketSender responseSender, SimpleChannel channel) {
        Entity entity = Minecraft.getInstance().level.getEntity(entityId);
        if (entity instanceof LivingEntity livingEntity) {
            MalumComponents.MALUM_LIVING_ENTITY_COMPONENT.maybeGet(livingEntity).ifPresent(c -> c.touchOfDarknessHandler.reject(livingEntity));
        }
    }
}