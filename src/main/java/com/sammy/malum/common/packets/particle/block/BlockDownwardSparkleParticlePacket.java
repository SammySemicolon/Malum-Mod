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
import team.lodestar.lodestone.systems.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.SpinParticleData;

import java.awt.*;
import java.util.Random;
import java.util.function.Supplier;

public class BlockDownwardSparkleParticlePacket extends BlockParticlePacket
{
    public BlockDownwardSparkleParticlePacket(Color color, BlockPos pos) {
        super(color, pos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        Random rand = level.random;
        for (int i = 0; i <= 3; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            int spinOffset = rand.nextInt(360);
            WorldParticleBuilder.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0, 0.8f, 0).build())
                    .setLifetime(25)
                    .setSpinData(SpinParticleData.create(0, 0.8f * spinDirection, 0.1f * spinDirection).setCoefficient(2f).setSpinOffset(spinOffset).setEasing(Easing.CUBIC_IN, Easing.QUINTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.EXPO_IN_OUT).build())
                    .setColorData(ColorParticleData.create(ColorHelper.brighter(color, 2), color).build())
                    .enableNoClip()
                    .setRandomOffset(0.6f)
                    .setGravity(0.3f)
                    .disableNoClip()
                    .setRandomMotion(0.1f, 0.15f)
                    .repeat(level, pos.getX() + 0.5f, pos.getY() + 0.2f, pos.getZ() + 0.5f, 1);
        }


        for (int i = 0; i < 2; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            int spinOffset = rand.nextInt(360);
            WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.05f, 0.08f, 0).setCoefficient(0.8f + rand.nextFloat() * 0.4f).setEasing(Easing.SINE_IN, Easing.CIRC_IN).build())
                    .setSpinData(SpinParticleData.create((0.1f + rand.nextFloat() * 0.05f) * spinDirection).setSpinOffset(spinOffset).build()).setScaleData(GenericParticleData.create(0.35f, 0.5f, 0).setCoefficient(0.8f + rand.nextFloat() * 0.4f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(color, color).build())
                    .setLifetime(50 + rand.nextInt(10))
                    .setRandomOffset(0.4f)
                    .enableNoClip()
                    .addMotion(0, -0.02f, 0)
                    .setRandomMotion(0.01f, 0.01f)
                    .repeatSurroundBlock(level, pos, 2);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, BlockDownwardSparkleParticlePacket.class, BlockDownwardSparkleParticlePacket::encode, BlockDownwardSparkleParticlePacket::decode, BlockDownwardSparkleParticlePacket::handle);
    }

    public static BlockDownwardSparkleParticlePacket decode(FriendlyByteBuf buf) {
        return new BlockDownwardSparkleParticlePacket(new Color(buf.readInt(), buf.readInt(), buf.readInt()), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
    }
}