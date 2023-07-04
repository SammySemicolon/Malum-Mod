package com.sammy.malum.common.packets.particle.curiosities.altar;

import com.sammy.malum.common.packets.particle.base.spirit.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.network.*;
import net.minecraftforge.network.simple.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.data.*;

import java.awt.*;
import java.util.List;
import java.util.function.*;

public class AltarCraftParticlePacket extends SpiritBasedParticleEffectPacket {

    public AltarCraftParticlePacket(List<String> spirits, Vec3 pos) {
        this(spirits, pos.x, pos.y, pos.z);
    }

    public AltarCraftParticlePacket(List<String> spirits, double posX, double posY, double posZ) {
        super(spirits, posX, posY, posZ);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context, MalumSpiritType spiritType) {
        Level level = Minecraft.getInstance().level;
        Color color = spiritType.getPrimaryColor();
        Color endColor = spiritType.getSecondaryColor();
        WorldParticleBuilder.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.6f, 0f).build())
                .setScaleData(GenericParticleData.create(0.15f, 0).build())
                .setColorData(ColorParticleData.create(color, endColor).build())
                .setLifetime(80)
                .setRandomOffset(0.1f)
                .addMotion(0, 0.26f, 0)
                .setRandomMotion(0.03f, 0.04f)
                .setGravityStrength(1)
                .repeat(level, posX, posY, posZ, 32);

        WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.2f, 0f).build())
                .setScaleData(GenericParticleData.create(0.4f, 0).build())
                .setColorData(ColorParticleData.create(color, endColor).build())
                .setLifetime(60)
                .setRandomOffset(0.25f, 0.1f)
                .setRandomMotion(0.004f, 0.004f)
                .enableNoClip()
                .repeat(level, posX, posY, posZ, 12);

        WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.05f, 0f).build())
                .setScaleData(GenericParticleData.create(0.2f, 0).build())
                .setColorData(ColorParticleData.create(color, endColor).build())
                .setLifetime(30)
                .setRandomOffset(0.05f, 0.05f)
                .setRandomMotion(0.02f, 0.02f)
                .enableNoClip()
                .repeat(level, posX, posY, posZ, 8);
    }


    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, AltarCraftParticlePacket.class, AltarCraftParticlePacket::encode, AltarCraftParticlePacket::decode, AltarCraftParticlePacket::handle);
    }

    public static AltarCraftParticlePacket decode(FriendlyByteBuf buf) {
        return decode(AltarCraftParticlePacket::new, buf);
    }
}
