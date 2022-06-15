package com.sammy.malum.common.packets.particle.block;

import com.sammy.ortus.helpers.ColorHelper;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.Random;
import java.util.function.Supplier;

public class BlockDownwardSparkleParticlePacket extends BlockParticlePacket
{
    public BlockDownwardSparkleParticlePacket(Color color, BlockPos pos) {
        super(color, pos);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        Random rand = level.random;
        for (int i = 0; i <= 3; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            int spinOffset = rand.nextInt(360);
            ParticleBuilders.create(OrtusParticleRegistry.TWINKLE_PARTICLE)
                    .setAlpha(0, 0.8f, 0)
                    .setLifetime(25)
                    .setSpinOffset(spinOffset)
                    .setSpinCoefficient(2f)
                    .setSpin(0, 0.8f*spinDirection, 0.1f*spinDirection)
                    .setSpinEasing(Easing.CUBIC_IN, Easing.QUINTIC_OUT)
                    .setScale(0.05f, 0.1f, 0)
                    .setScaleCoefficient(0.8f)
                    .setScaleEasing(Easing.QUINTIC_OUT, Easing.EXPO_IN_OUT)
                    .setColor(ColorHelper.brighter(color, 2), color)
                    .enableNoClip()
                    .randomOffset(0.6f)
                    .setGravity(0.3f)
                    .disableNoClip()
                    .randomMotion(0.1f, 0.15f)
                    .repeat(level, pos.getX()+0.5f, pos.getY()+0.2f, pos.getZ()+0.5f, 1);
        }


        for (int i = 0; i < 2; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            int spinOffset = rand.nextInt(360);
            ParticleBuilders.create(OrtusParticleRegistry.SMOKE_PARTICLE)
                    .setAlpha(0.05f, 0.08f, 0)
                    .setAlphaCoefficient(0.8f+rand.nextFloat()*0.4f)
                    .setAlphaEasing(Easing.SINE_IN, Easing.CIRC_IN)
                    .setLifetime(50+rand.nextInt(10))
                    .setSpinOffset(spinOffset)
                    .setSpin((0.1f+rand.nextFloat()*0.05f)*spinDirection)
                    .setScale(0.35f, 0.5f, 0)
                    .setScaleCoefficient(0.8f+rand.nextFloat()*0.4f)
                    .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                    .setColor(color, color)
                    .randomOffset(0.4f)
                    .enableNoClip()
                    .addMotion(0, -0.02f, 0)
                    .randomMotion(0.01f, 0.01f)
                    .repeatEdges(level, pos, 2);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, BlockDownwardSparkleParticlePacket.class, BlockDownwardSparkleParticlePacket::encode, BlockDownwardSparkleParticlePacket::decode, BlockDownwardSparkleParticlePacket::handle);
    }

    public static BlockDownwardSparkleParticlePacket decode(FriendlyByteBuf buf) {
        return new BlockDownwardSparkleParticlePacket(new Color(buf.readInt(), buf.readInt(), buf.readInt()), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
    }
}