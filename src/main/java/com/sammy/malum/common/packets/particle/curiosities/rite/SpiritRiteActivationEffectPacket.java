package com.sammy.malum.common.packets.particle.curiosities.rite;

import com.sammy.malum.common.packets.particle.base.spirit.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.client.*;
import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.network.*;
import net.minecraftforge.network.simple.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.data.*;

import java.awt.*;
import java.util.List;
import java.util.function.*;

public class SpiritRiteActivationEffectPacket extends SpiritBasedBlockParticleEffectPacket {

    private int height = 0;
    public SpiritRiteActivationEffectPacket(List<String> spirits, BlockPos pos) {
        super(spirits, pos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context, MalumSpiritType spiritType) {
        Level level = Minecraft.getInstance().level;
        Color color = spiritType.getPrimaryColor();
        WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.1f, 0.22f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN_OUT).build())
                .setSpinData(SpinParticleData.create(0.2f).build())
                .setScaleData(GenericParticleData.create(0.15f, 0.2f, 0.1f).setCoefficient(0.7f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN_OUT).build())
                .setColorData(ColorParticleData.create(color, color).build())
                .setLifetime(25)
                .enableNoClip()
                .setRandomOffset(0.1f, 0.1f)
                .setRandomMotion(0.001f, 0.001f)
                .repeatSurroundBlock(level, pos.above(height), 3, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

        WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.06f, 0.14f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN_OUT).build())
                .setSpinData(SpinParticleData.create(0.1f).build())
                .setScaleData(GenericParticleData.create(0.35f, 0.4f, 0.1f).setCoefficient(0.7f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN_OUT).build())
                .setColorData(ColorParticleData.create(color, color).build())
                .setLifetime(30)
                .setRandomOffset(0.2f)
                .enableNoClip()
                .setRandomMotion(0.001f, 0.001f)
                .repeatSurroundBlock(level, pos.above(height), 5, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
        height++;
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SpiritRiteActivationEffectPacket.class, SpiritRiteActivationEffectPacket::encode, SpiritRiteActivationEffectPacket::decode, SpiritRiteActivationEffectPacket::handle);
    }

    public static SpiritRiteActivationEffectPacket decode(FriendlyByteBuf buf) {
        return decode(SpiritRiteActivationEffectPacket::new, buf);
    }
}
