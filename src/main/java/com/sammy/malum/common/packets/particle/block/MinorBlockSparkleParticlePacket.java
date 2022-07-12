package com.sammy.malum.common.packets.particle.block;

import com.sammy.ortus.helpers.ColorHelper;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import com.sammy.ortus.systems.rendering.particle.SimpleParticleOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

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
            ParticleBuilders.create(OrtusParticleRegistry.SMOKE_PARTICLE)
                    .setAlpha(0.02f, 0.095f, 0)
                    .setAlphaEasing(Easing.SINE_IN, Easing.CIRC_IN)
                    .setLifetime(50+rand.nextInt(10))
                    .setSpinOffset(spinOffset)
                    .setSpin((0.125f+rand.nextFloat()*0.075f)*spinDirection)
                    .setScale(0.25f, 0.45f, 0)
                    .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                    .setColor(color, color)
                    .randomOffset(0.4f)
                    .enableNoClip()
                    .randomMotion(0.01f, 0.01f)
                    .overwriteRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.ENDING_CURVE_INVISIBLE)
                    .repeatEdges(level, pos, 1);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, MinorBlockSparkleParticlePacket.class, MinorBlockSparkleParticlePacket::encode, MinorBlockSparkleParticlePacket::decode, MinorBlockSparkleParticlePacket::handle);
    }

    public static MinorBlockSparkleParticlePacket decode(FriendlyByteBuf buf) {
        return new MinorBlockSparkleParticlePacket(new Color(buf.readInt(), buf.readInt(), buf.readInt()), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
    }
}