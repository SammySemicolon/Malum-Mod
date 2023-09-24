package com.sammy.malum.common.packets.particle;

import com.sammy.malum.common.packets.particle.base.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.network.*;
import net.minecraftforge.network.simple.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;

import java.awt.*;
import java.util.function.*;

public class SuccessfulSoulHarvestParticlePacket extends PositionBasedParticleEffectPacket {
    private final Color color;
    private final Color endColor;

    public SuccessfulSoulHarvestParticlePacket(Color color, Color endColor, double posX, double posY, double posZ) {
        super(posX, posY, posZ);
        this.color = color;
        this.endColor = endColor;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(color.getRed());
        buf.writeInt(color.getGreen());
        buf.writeInt(color.getBlue());
        buf.writeInt(endColor.getRed());
        buf.writeInt(endColor.getGreen());
        buf.writeInt(endColor.getBlue());
        super.encode(buf);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(1.0f, 0).build())
                .setScaleData(GenericParticleData.create(0.4f, 0).build())
                .setColorData(ColorParticleData.create(color, endColor).build())
                .setSpinData(SpinParticleData.create(0.4f).build())
                .setLifetime(20)
                .setRandomOffset(0.5, 0).setRandomMotion(0, 0.125f)
                .addMotion(0, 0.28f, 0)
                .setGravityStrength(1)
                .repeat(level, posX, posY, posZ, 40);

        WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                .setScaleData(GenericParticleData.create(0.2f, 0).build())
                .setColorData(ColorParticleData.create(color, endColor).build())
                .setSpinData(SpinParticleData.create(0.3f).build())
                .setLifetime(40)
                .setRandomOffset(0.5, 0.5).setRandomMotion(0.125f, 0.05)
                .addMotion(0, 0.15f, 0)
                .setGravityStrength(1)
                .repeat(level, posX, posY, posZ, 30);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SuccessfulSoulHarvestParticlePacket.class, SuccessfulSoulHarvestParticlePacket::encode, SuccessfulSoulHarvestParticlePacket::decode, SuccessfulSoulHarvestParticlePacket::handle);
    }

    public static SuccessfulSoulHarvestParticlePacket decode(FriendlyByteBuf buf) {
        return new SuccessfulSoulHarvestParticlePacket(new Color(buf.readInt(), buf.readInt(), buf.readInt()), new Color(buf.readInt(), buf.readInt(), buf.readInt()), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }
}