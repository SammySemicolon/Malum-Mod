package com.sammy.malum.common.packets.particle.curiosities.rite;

import com.sammy.malum.common.packets.particle.base.spirit.SpiritBasedBlockParticleEffectPacket;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
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
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

public class SacredMistRiteEffectPacket extends SpiritBasedBlockParticleEffectPacket {
    public SacredMistRiteEffectPacket(List<String> spirits, BlockPos pos) {
        super(spirits, pos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context, MalumSpiritType spiritType) {
        Level level = Minecraft.getInstance().level;
        Color color = spiritType.getPrimaryColor();
        Color endColor = spiritType.getSecondaryColor();
        WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.08f, 0.32f, 0).build())
                .setSpinData(SpinParticleData.create(0.2f).build())
                .setScaleData(GenericParticleData.create(0.15f, 0.2f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setColorData(ColorParticleData.create(color, endColor).build())
                .setLifetime(15)
                .enableNoClip()
                .setRandomOffset(0.1f, 0f)
                .setRandomMotion(0.001f, 0.002f)
                .repeatSurroundBlock(level, pos, 6, Direction.UP, Direction.DOWN);

        WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.04f, 0.16f, 0).build())
                .setSpinData(SpinParticleData.create(0.1f).build())
                .setScaleData(GenericParticleData.create(0.35f, 0.4f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setColorData(ColorParticleData.create(color, endColor).build())
                .setLifetime(20)
                .setRandomOffset(0.2f, 0)
                .enableNoClip()
                .setRandomMotion(0.001f, 0.002f)
                .repeatSurroundBlock(level, pos, 8, Direction.UP, Direction.DOWN);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SacredMistRiteEffectPacket.class, SacredMistRiteEffectPacket::encode, SacredMistRiteEffectPacket::decode, SacredMistRiteEffectPacket::handle);
    }

    public static SacredMistRiteEffectPacket decode(FriendlyByteBuf buf) {
        return decode(SacredMistRiteEffectPacket::new, buf);
    }

}