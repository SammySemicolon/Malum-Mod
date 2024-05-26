package com.sammy.malum.common.packets.particle.curiosities.rite;

import com.sammy.malum.common.packets.particle.curiosities.rite.generic.BlockSparkleParticlePacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.awt.*;
import java.util.function.Supplier;

public class InfernalExtinguishRiteEffectPacket extends BlockSparkleParticlePacket {
    public InfernalExtinguishRiteEffectPacket(Color color, BlockPos pos) {
        super(color, pos);
    }

    public InfernalExtinguishRiteEffectPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void executeClient(Minecraft client, ClientPacketListener listener, PacketSender responseSender, SimpleChannel channel) {
        Level level = Minecraft.getInstance().level;
        var rand = level.random;

        for (int i = 0; i < 8; ++i) {
            double d0 = pos.getX() + rand.nextFloat();
            double d1 = pos.getY() + rand.nextFloat();
            double d2 = pos.getZ() + rand.nextFloat();
            level.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
        super.executeClient(client, listener, responseSender, channel);
    }
}