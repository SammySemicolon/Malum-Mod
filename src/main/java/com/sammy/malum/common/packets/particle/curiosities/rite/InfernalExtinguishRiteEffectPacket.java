package com.sammy.malum.common.packets.particle.curiosities.rite;

import com.sammy.malum.common.packets.particle.curiosities.rite.generic.BlockSparkleParticlePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class InfernalExtinguishRiteEffectPacket extends BlockSparkleParticlePacket {
    public InfernalExtinguishRiteEffectPacket(Color color, BlockPos pos) {
        super(color, pos);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        var rand = level.random;

        for (int i = 0; i < 8; ++i) {
            double d0 = pos.getX() + rand.nextFloat();
            double d1 = pos.getY() + rand.nextFloat();
            double d2 = pos.getZ() + rand.nextFloat();
            level.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
        super.execute(context);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, InfernalExtinguishRiteEffectPacket.class, InfernalExtinguishRiteEffectPacket::encode, InfernalExtinguishRiteEffectPacket::decode, InfernalExtinguishRiteEffectPacket::handle);
    }

    public static InfernalExtinguishRiteEffectPacket decode(FriendlyByteBuf buf) {
        return decode(InfernalExtinguishRiteEffectPacket::new, buf);
    }
}