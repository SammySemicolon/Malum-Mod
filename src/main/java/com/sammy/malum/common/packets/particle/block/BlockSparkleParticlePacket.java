package com.sammy.malum.common.packets.particle.block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.SimpleParticleOptions;

import java.awt.*;
import java.util.Random;
import java.util.function.Supplier;

public class BlockSparkleParticlePacket extends BlockParticlePacket
{
    public BlockSparkleParticlePacket(Color color, BlockPos pos) {
        super(color, pos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        Random rand = level.random;
        for (int i = 0; i < 5; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            int spinOffset = rand.nextInt(360);
            ParticleBuilders.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                    .setAlpha(0, 0.8f, 0)
                    .setLifetime(20)
                    .setSpinOffset(spinOffset)
                    .setSpinCoefficient(1.25f)
                    .setSpin(0.7f*spinDirection, 0)
                    .setSpinEasing(Easing.CUBIC_IN)
                    .setScale(0.05f, 0.1f, 0)
                    .setScaleCoefficient(0.8f)
                    .setScaleEasing(Easing.QUINTIC_OUT, Easing.EXPO_IN_OUT)
                    .setColor(ColorHelper.brighter(color, 2), color)
                    .enableNoClip()
                    .randomOffset(0.6f)
                    .setGravity(1.1f)
                    .addMotion(0, 0.28f+rand.nextFloat()*0.15f, 0)
                    .disableNoClip()
                    .randomMotion(0.1f, 0.15f)
                    .overwriteRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.ENDING_CURVE_INVISIBLE)
                    .repeat(level, pos.getX()+0.5f, pos.getY()+0.2f, pos.getZ()+0.5f, 1);
        }
        for (int i = 0; i < 2; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            int spinOffset = rand.nextInt(360);
            ParticleBuilders.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setAlpha(0.05f, 0.08f, 0)
                    .setAlphaEasing(Easing.SINE_IN, Easing.CIRC_IN)
                    .setLifetime(50+rand.nextInt(10))
                    .setSpinOffset(spinOffset)
                    .setSpin((0.125f+rand.nextFloat()*0.075f)*spinDirection)
                    .setScale(0.35f, 0.5f, 0)
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
        instance.registerMessage(index, BlockSparkleParticlePacket.class, BlockSparkleParticlePacket::encode, BlockSparkleParticlePacket::decode, BlockSparkleParticlePacket::handle);
    }

    public static BlockSparkleParticlePacket decode(FriendlyByteBuf buf) {
        return new BlockSparkleParticlePacket(new Color(buf.readInt(), buf.readInt(), buf.readInt()), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
    }
}