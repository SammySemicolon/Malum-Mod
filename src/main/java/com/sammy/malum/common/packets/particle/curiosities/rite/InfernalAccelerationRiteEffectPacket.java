package com.sammy.malum.common.packets.particle.curiosities.rite;

import com.sammy.malum.common.packets.particle.base.spirit.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.client.*;
import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.network.*;
import net.minecraftforge.network.simple.*;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.data.*;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.*;

public class InfernalAccelerationRiteEffectPacket extends SpiritBasedBlockParticleEffectPacket {

    public InfernalAccelerationRiteEffectPacket(List<String> spirits, BlockPos pos) {
        super(spirits, pos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context, MalumSpiritType spiritType) {
        Level level = Minecraft.getInstance().level;
        RandomSource rand = level.random;
        Color color = spiritType.getPrimaryColor();
        Color endColor = spiritType.getSecondaryColor();
        for (int i = 0; i < 3; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            int spinOffset = rand.nextInt(360);
            WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.02f, 0.095f, 0).setEasing(Easing.SINE_IN, Easing.CIRC_IN).build())
                    .setSpinData(SpinParticleData.create((0.125f + rand.nextFloat() * 0.075f) * spinDirection).setSpinOffset(spinOffset).build())
                    .setScaleData(GenericParticleData.create(0.25f, 0.45f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(color, endColor).build())
                    .setLifetime(50 + rand.nextInt(10))
                    .setRandomOffset(0.4f)
                    .enableNoClip()
                    .setRandomMotion(0.01f, 0.01f)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .repeatSurroundBlock(level, pos, 1);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, InfernalAccelerationRiteEffectPacket.class, InfernalAccelerationRiteEffectPacket::encode, InfernalAccelerationRiteEffectPacket::decode, InfernalAccelerationRiteEffectPacket::handle);
    }

    public static InfernalAccelerationRiteEffectPacket decode(FriendlyByteBuf buf) {
        return decode(InfernalAccelerationRiteEffectPacket::new, buf);
    }
}