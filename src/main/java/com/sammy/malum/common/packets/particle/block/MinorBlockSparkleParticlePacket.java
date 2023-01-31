package com.sammy.malum.common.packets.particle.block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.SpinParticleData;

import java.awt.*;
import java.util.Random;
import java.util.function.Supplier;

public class MinorBlockSparkleParticlePacket extends BlockParticlePacket
{
    public MinorBlockSparkleParticlePacket(Color color, BlockPos pos) {
        super(color, pos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        Random rand = level.random;
        for (int i = 0; i < 3; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            int spinOffset = rand.nextInt(360);
            WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.02f, 0.095f, 0).setEasing(Easing.SINE_IN, Easing.CIRC_IN).build())
                    .setSpinData(SpinParticleData.create((0.125f+rand.nextFloat()*0.075f)*spinDirection).setSpinOffset(spinOffset).build())
                    .setScaleData(GenericParticleData.create(0.25f, 0.45f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(color, color).build())
                    .setLifetime(50+rand.nextInt(10))
                    .setRandomOffset(0.4f)
                    .enableNoClip()
                    .setRandomMotion(0.01f, 0.01f)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .repeatSurroundBlock(level, pos, 1);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, MinorBlockSparkleParticlePacket.class, MinorBlockSparkleParticlePacket::encode, MinorBlockSparkleParticlePacket::decode, MinorBlockSparkleParticlePacket::handle);
    }

    public static MinorBlockSparkleParticlePacket decode(FriendlyByteBuf buf) {
        return new MinorBlockSparkleParticlePacket(new Color(buf.readInt(), buf.readInt(), buf.readInt()), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
    }
}