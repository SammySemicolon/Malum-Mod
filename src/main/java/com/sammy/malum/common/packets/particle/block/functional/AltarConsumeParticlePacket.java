package com.sammy.malum.common.packets.particle.block.functional;

import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AltarConsumeParticlePacket extends FunctionalBlockItemAbsorbParticlePacket {

    public AltarConsumeParticlePacket(ItemStack stack, List<String> spirits, double posX, double posY, double posZ, double altarPosX, double altarPosY, double altarPosZ) {
        super(stack, spirits, posX, posY, posZ, altarPosX, altarPosY, altarPosZ);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        ArrayList<MalumSpiritType> types = new ArrayList<>();
        for (String string : spirits) {
            types.add(SpiritHelper.getSpiritType(string));
        }
        float alpha = 0.1f / types.size();
        for (MalumSpiritType type : types) {
            Color color = type.getColor();
            Color endColor = type.getEndColor();
            ParticleBuilders.create(OrtusParticleRegistry.TWINKLE_PARTICLE)
                    .setAlpha(alpha * 2, 0f)
                    .setLifetime(60)
                    .setScale(0.4f, 0)
                    .setColor(color, endColor)
                    .randomOffset(0.25f, 0.1f)
                    .randomMotion(0.004f, 0.004f)
                    .enableNoClip()
                    .repeat(level, posX, posY, posZ, 12);

            ParticleBuilders.create(OrtusParticleRegistry.WISP_PARTICLE)
                    .setAlpha(alpha, 0f)
                    .setLifetime(30)
                    .setScale(0.2f, 0)
                    .setColor(color, endColor)
                    .randomOffset(0.05f, 0.05f)
                    .randomMotion(0.002f, 0.002f)
                    .enableNoClip()
                    .repeat(level, posX, posY, posZ, 8);

            Vec3 velocity = new Vec3(posX, posY, posZ).subtract(altarPosX, altarPosY, altarPosZ).normalize().scale(-0.05f);
            ParticleBuilders.create(OrtusParticleRegistry.WISP_PARTICLE)
                    .setAlpha(alpha, 0f)
                    .setLifetime(40)
                    .setScale(0.3f, 0)
                    .randomOffset(0.15f)
                    .randomMotion(0.005f, 0.005f)
                    .setColor(color, color.darker())
                    .addMotion(velocity.x, velocity.y, velocity.z)
                    .enableNoClip()
                    .repeat(level, posX, posY, posZ, 36);
        }
    }


    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, AltarConsumeParticlePacket.class, AltarConsumeParticlePacket::encode, AltarConsumeParticlePacket::decode, AltarConsumeParticlePacket::handle);
    }

    public static AltarConsumeParticlePacket decode(FriendlyByteBuf buf) {
        return decode(AltarConsumeParticlePacket::new, buf);
    }
}