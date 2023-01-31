package com.sammy.malum.common.packets.particle.block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.SpinParticleData;

import java.awt.*;
import java.util.function.Supplier;

public class BlockMistParticlePacket extends BlockParticlePacket {
    public BlockMistParticlePacket(Color color, BlockPos pos) {
        super(color, pos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.08f, 0.32f, 0).build())
                .setSpinData(SpinParticleData.create(0.2f).build())
                .setScaleData(GenericParticleData.create(0.15f, 0.2f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setColorData(ColorParticleData.create(color, color).build())
                .setLifetime(15)
                .enableNoClip()
                .setRandomOffset(0.1f, 0f)
                .setRandomMotion(0.001f, 0.002f)
                .repeatSurroundBlock(level, pos, 6, Direction.UP, Direction.DOWN);

        WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.04f, 0.16f, 0).build())
                .setSpinData(SpinParticleData.create(0.1f).build())
                .setScaleData(GenericParticleData.create(0.35f, 0.4f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setColorData(ColorParticleData.create(color, color).build())
                .setLifetime(20)
                .setRandomOffset(0.2f, 0)
                .enableNoClip()
                .setRandomMotion(0.001f, 0.002f)
                .repeatSurroundBlock(level, pos, 8, Direction.UP, Direction.DOWN);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, BlockMistParticlePacket.class, BlockMistParticlePacket::encode, BlockMistParticlePacket::decode, BlockMistParticlePacket::handle);
    }

    public static BlockMistParticlePacket decode(FriendlyByteBuf buf) {
        return new BlockMistParticlePacket(new Color(buf.readInt(), buf.readInt(), buf.readInt()), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
    }
}