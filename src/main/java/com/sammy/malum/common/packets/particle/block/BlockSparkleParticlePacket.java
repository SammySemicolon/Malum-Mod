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
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.SpinParticleData;

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
            WorldParticleBuilder.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0, 0.8f, 0).build())
                    .setSpinData(SpinParticleData.create(0.7f*spinDirection, 0).setCoefficient(1.25f).setSpinOffset(spinOffset).setEasing(Easing.CUBIC_IN).build())
                    .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.EXPO_IN_OUT).build())
                    .setColorData(ColorParticleData.create(ColorHelper.brighter(color, 2), color).build())
                    .setLifetime(20)
                    .enableNoClip()
                    .setRandomOffset(0.6f)
                    .setGravity(1.1f)
                    .addMotion(0, 0.28f+rand.nextFloat()*0.15f, 0)
                    .disableNoClip()
                    .setRandomMotion(0.1f, 0.15f)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .repeat(level, pos.getX()+0.5f, pos.getY()+0.2f, pos.getZ()+0.5f, 1);
        }
        for (int i = 0; i < 2; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            int spinOffset = rand.nextInt(360);
            WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.05f, 0.08f, 0).setEasing(Easing.SINE_IN, Easing.CIRC_IN).build())
                    .setSpinData(SpinParticleData.create((0.125f+rand.nextFloat()*0.075f)*spinDirection).setSpinOffset(spinOffset).build())
                    .setScaleData(GenericParticleData.create(0.35f, 0.5f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
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
        instance.registerMessage(index, BlockSparkleParticlePacket.class, BlockSparkleParticlePacket::encode, BlockSparkleParticlePacket::decode, BlockSparkleParticlePacket::handle);
    }

    public static BlockSparkleParticlePacket decode(FriendlyByteBuf buf) {
        return new BlockSparkleParticlePacket(new Color(buf.readInt(), buf.readInt(), buf.readInt()), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
    }
}