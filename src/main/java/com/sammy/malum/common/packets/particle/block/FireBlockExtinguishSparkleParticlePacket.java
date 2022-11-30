package com.sammy.malum.common.packets.particle.block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.Random;
import java.util.function.Supplier;

public class FireBlockExtinguishSparkleParticlePacket extends BlockSparkleParticlePacket
{
    public FireBlockExtinguishSparkleParticlePacket(Color color, BlockPos pos) {
        super(color, pos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        RandomSource rand = level.random;

        for(int i = 0; i < 8; ++i) {
            double d0 = pos.getX() + rand.nextFloat();
            double d1 = pos.getY() + rand.nextFloat();
            double d2 = pos.getZ() + rand.nextFloat();
            level.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
        super.execute(context);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, FireBlockExtinguishSparkleParticlePacket.class, FireBlockExtinguishSparkleParticlePacket::encode, FireBlockExtinguishSparkleParticlePacket::decode, FireBlockExtinguishSparkleParticlePacket::handle);
    }

    public static FireBlockExtinguishSparkleParticlePacket decode(FriendlyByteBuf buf) {
        return new FireBlockExtinguishSparkleParticlePacket(new Color(buf.readInt(), buf.readInt(), buf.readInt()), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
    }
}