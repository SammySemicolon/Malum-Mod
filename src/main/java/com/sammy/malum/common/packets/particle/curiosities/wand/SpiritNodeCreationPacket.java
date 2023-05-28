package com.sammy.malum.common.packets.particle.curiosities.wand;

import com.sammy.malum.common.packets.particle.base.spirit.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.client.*;
import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.network.*;
import net.minecraftforge.network.simple.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.data.*;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.*;

public class SpiritNodeCreationPacket extends SpiritBasedBlockParticleEffectPacket {
    public SpiritNodeCreationPacket(List<String> spirits, BlockPos pos) {
        super(spirits, pos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context, MalumSpiritType spiritType) {
        Level level = Minecraft.getInstance().level;
        Random rand = level.random;
        Color color = spiritType.getPrimaryColor();
        for (int i = 0; i < 3; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            int spinOffset = rand.nextInt(360);
            WorldParticleBuilder.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.4f, 0.8f, 0).build())
                    .setLifetime(20)
                    .setSpinData(SpinParticleData.create(0.7f * spinDirection, 0).setSpinOffset(spinOffset).setSpinOffset(1.25f).setEasing(Easing.CUBIC_IN).build())
                    .setScaleData(GenericParticleData.create(0.075f, 0.15f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.EXPO_IN_OUT).build())
                    .setColorData(ColorParticleData.create(ColorHelper.brighter(color, 2), color).build())
                    .enableNoClip()
                    .setRandomOffset(0.6f)
                    .setGravity(1.1f)
                    .addMotion(0, 0.28f + rand.nextFloat() * 0.15f, 0)
                    .disableNoClip()
                    .setRandomMotion(0.1f, 0.15f)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .repeatSurroundBlock(level, pos, 3);
        }
        int spinOffset = rand.nextInt(360);
        for (int i = 0; i < 3; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.12f, 0.06f, 0).setEasing(Easing.SINE_IN, Easing.CIRC_IN).build())
                    .setSpinData(SpinParticleData.create((0.125f + rand.nextFloat() * 0.075f) * spinDirection).setSpinOffset(spinOffset).build())
                    .setScaleData(GenericParticleData.create(0.85f, 0.5f, 0).setEasing(Easing.EXPO_OUT, Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(color.brighter(), color.darker()).build())
                    .setLifetime(30)
                    .setRandomOffset(0.4f)
                    .enableNoClip()
                    .setRandomMotion(0.01f, 0.01f)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .repeatSurroundBlock(level, pos, 5);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SpiritNodeCreationPacket.class, SpiritNodeCreationPacket::encode, SpiritNodeCreationPacket::decode, SpiritNodeCreationPacket::handle);
    }

    public static SpiritNodeCreationPacket decode(FriendlyByteBuf buf) {
        return decode(SpiritNodeCreationPacket::new, buf);
    }
}
