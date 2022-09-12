package com.sammy.malum.common.packets.particle.block.functional;

import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AltarCraftParticlePacket extends FunctionalBlockParticlePacket {
    public AltarCraftParticlePacket(List<String> spirits, Vec3 vec3) {
        super(spirits, vec3);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        List<MalumSpiritType> types = new ArrayList<>();
        for (String string : spirits) {
            types.add(SpiritHelper.getSpiritType(string));
        }
        for (MalumSpiritType type : types) {
            Color color = type.getColor();
            Color endColor = type.getEndColor();
            ParticleBuilders.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                    .setAlpha(0.6f, 0f)
                    .setLifetime(80)
                    .setScale(0.15f, 0)
                    .setColor(color, endColor)
                    .randomOffset(0.1f)
                    .addMotion(0, 0.26f, 0)
                    .randomMotion(0.03f, 0.04f)
                    .setGravity(1)
                    .repeat(level, posX, posY, posZ, 32);

            ParticleBuilders.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0.2f, 0f)
                    .setLifetime(60)
                    .setScale(0.4f, 0)
                    .setColor(color, endColor)
                    .randomOffset(0.25f, 0.1f)
                    .randomMotion(0.004f, 0.004f)
                    .enableNoClip()
                    .repeat(level, posX, posY, posZ, 12);

            ParticleBuilders.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                    .setAlpha(0.05f, 0f)
                    .setLifetime(30)
                    .setScale(0.2f, 0)
                    .setColor(color, endColor)
                    .randomOffset(0.05f, 0.05f)
                    .randomMotion(0.02f, 0.02f)
                    .enableNoClip()
                    .repeat(level, posX, posY, posZ, 8);
        }
    }


    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, AltarCraftParticlePacket.class, AltarCraftParticlePacket::encode, AltarCraftParticlePacket::decode, AltarCraftParticlePacket::handle);
    }

    public static AltarCraftParticlePacket decode(FriendlyByteBuf buf) {
        return decode(AltarCraftParticlePacket::new, buf);
    }
}
