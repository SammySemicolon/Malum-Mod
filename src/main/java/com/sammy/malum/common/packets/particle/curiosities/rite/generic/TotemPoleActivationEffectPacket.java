package com.sammy.malum.common.packets.particle.curiosities.rite.generic;

import com.sammy.malum.common.packets.particle.base.color.ColorBasedBlockParticleEffectPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import java.awt.*;
import java.util.function.Supplier;

public class TotemPoleActivationEffectPacket extends ColorBasedBlockParticleEffectPacket {

    public TotemPoleActivationEffectPacket(Color color, BlockPos pos) {
        super(color, pos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.12f, 0.16f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setSpinData(SpinParticleData.create(0.2f).build())
                .setScaleData(GenericParticleData.create(0.15f, 0.2f, 0.1f).setCoefficient(0.7f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setColorData(ColorParticleData.create(color, color).build())
                .setLifetime(25)
                .enableNoClip()
                .setRandomOffset(0.1f, 0.1f)
                .setRandomMotion(0.001f, 0.001f)
                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                .repeatSurroundBlock(level, pos, 6, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

        WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.04f, 0.08f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setSpinData(SpinParticleData.create(0.1f).build())
                .setScaleData(GenericParticleData.create(0.35f, 0.4f, 0.1f).setCoefficient(0.7f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setColorData(ColorParticleData.create(color, color).build())
                .setLifetime(35)
                .setRandomOffset(0.2f)
                .enableNoClip()
                .setRandomMotion(0.001f, 0.001f)
                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                .repeatSurroundBlock(level, pos, 8, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, TotemPoleActivationEffectPacket.class, TotemPoleActivationEffectPacket::encode, TotemPoleActivationEffectPacket::decode, TotemPoleActivationEffectPacket::handle);
    }

    public static TotemPoleActivationEffectPacket decode(FriendlyByteBuf buf) {
        return decode(TotemPoleActivationEffectPacket::new, buf);
    }
}