package com.sammy.malum.common.packets.particle.curiosities.blight;

import com.sammy.malum.common.packets.particle.base.spirit.SpiritBasedParticleEffectPacket;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BlightTransformItemParticlePacket extends SpiritBasedParticleEffectPacket {

    public BlightTransformItemParticlePacket(List<String> spirits, Vec3 pos) {
        this(spirits, pos.x, pos.y, pos.z);
    }

    public BlightTransformItemParticlePacket(List<String> spirits, double posX, double posY, double posZ) {
        super(spirits, posX, posY, posZ);
    }

    public BlightTransformItemParticlePacket(FriendlyByteBuf buf) {
        super(buf);
    }

    @Environment(EnvType.CLIENT)
    @Override
    protected void execute(Minecraft client, ClientPacketListener listener, PacketSender responseSender, SimpleChannel channel, MalumSpiritType spiritType) {
        Level level = Minecraft.getInstance().level;
        var rand = level.random;
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
                    .setGravityStrength(1.1f)
                    .addMotion(0, 0.28f + rand.nextFloat() * 0.15f, 0)
                    .disableNoClip()
                    .setRandomMotion(0.1f, 0.15f)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .repeat(level, posX, posY, posZ, 2);
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
                    .repeat(level, posX, posY, posZ, 5);
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void executeClient(Minecraft client, ClientPacketListener listener, PacketSender responseSender, SimpleChannel channel) {
        super.executeClient(client, listener, responseSender, channel);
        Level level = Minecraft.getInstance().level;
        var rand = level.random;
        for (int i = 0; i < 3; i++) {
            float multiplier = Mth.nextFloat(rand, 0.4f, 1f);
            float timeMultiplier = Mth.nextFloat(rand, 0.9f, 1.4f);
            Color color = new Color((int) (31 * multiplier), (int) (19 * multiplier), (int) (31 * multiplier));
            boolean spinDirection = rand.nextBoolean();
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.4f, 1f, 0).build())
                    .setLifetime((int) (45 * timeMultiplier))
                    .setSpinData(SpinParticleData.create(0.2f * (spinDirection ? 1 : -1)).build())
                    .setScaleData(GenericParticleData.create(0.15f, 0.2f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(color, color).build())
                    .enableNoClip()
                    .setRandomOffset(0.1f, 0.1f)
                    .setRandomMotion(0.01f, 0.02f)
                    .addMotion(0, 0.01f, 0)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .repeat(level, posX, posY, posZ, 2);

            WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.35f, 0.55f, 0).build())
                    .setLifetime((int) (50 * timeMultiplier))
                    .setSpinData(SpinParticleData.create(0.1f * (spinDirection ? 1 : -1)).build())
                    .setScaleData(GenericParticleData.create(0.35f, 0.4f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(color, color).build())
                    .setRandomOffset(0.2f, 0)
                    .enableNoClip()
                    .setRandomMotion(0.015f, 0.015f)
                    .addMotion(0, 0.01f, 0)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .repeat(level, posX, posY, posZ, 3);

            color = new Color((int) (80 * multiplier), (int) (40 * multiplier), (int) (80 * multiplier));
            WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.1f, 0.15f, 0).build())
                    .setLifetime((int) (50 * timeMultiplier))
                    .setSpinData(SpinParticleData.create(0.1f * (spinDirection ? 1 : -1)).build())
                    .setScaleData(GenericParticleData.create(0.35f, 0.4f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(color, color).build())
                    .setRandomOffset(0.2f, 0)
                    .enableNoClip()
                    .setRandomMotion(0.02f, 0.01f)
                    .addMotion(0, 0.01f, 0)
                    .repeat(level, posX, posY, posZ, 2);
        }
    }
}
