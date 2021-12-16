package com.sammy.malum.network;

import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;

public class PacketEffects
{
    public static void altarConsumeParticles(ItemStack stack, ArrayList<String> spirits, double posX, double posY, double posZ, double altarPosX, double altarPosY, double altarPosZ)
    {
        Level level = Minecraft.getInstance().level;
        ArrayList<MalumSpiritType> types = new ArrayList<>();
        for (String string : spirits)
        {
            types.add(SpiritHelper.getSpiritType(string));
        }
        float alpha = 0.1f / types.size();
        for (MalumSpiritType type : types)
        {
            Color color = type.color;
            RenderUtilities.create(ParticleRegistry.TWINKLE_PARTICLE)
                    .setAlpha(alpha*2, 0f)
                    .setLifetime(60)
                    .setScale(0.4f, 0)
                    .setColor(color, color.darker())
                    .randomOffset(0.25f, 0.1f)
                    .randomVelocity(0.004f, 0.004f)
                    .enableNoClip()
                    .repeat(level, posX, posY, posZ, 12);

            RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                    .setAlpha(alpha, 0f)
                    .setLifetime(30)
                    .setScale(0.2f, 0)
                    .setColor(color, color.darker())
                    .randomOffset(0.05f, 0.05f)
                    .randomVelocity(0.002f, 0.002f)
                    .enableNoClip()
                    .repeat(level, posX, posY, posZ, 8);

            Vec3 velocity = new Vec3(posX, posY, posZ).subtract(altarPosX, altarPosY, altarPosZ).normalize().scale(-0.05f);
            RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                    .setAlpha(alpha, 0f)
                    .setLifetime(40)
                    .setScale(0.3f, 0)
                    .randomOffset(0.15f)
                    .randomVelocity(0.005f, 0.005f)
                    .setColor(color, color.darker())
                    .addVelocity(velocity.x, velocity.y, velocity.z)
                    .enableNoClip()
                    .repeat(level, posX, posY, posZ, 36);
        }
    }
    public static void altarCraftParticles(ArrayList<String> spirits, double posX, double posY, double posZ)
    {
        Level level = Minecraft.getInstance().level;
        ArrayList<MalumSpiritType> types = new ArrayList<>();
        for (String string : spirits)
        {
            types.add(SpiritHelper.getSpiritType(string));
        }
        for (MalumSpiritType type : types)
        {
            Color color = type.color;
            RenderUtilities.create(ParticleRegistry.TWINKLE_PARTICLE)
                    .setAlpha(0.6f, 0f)
                    .setLifetime(80)
                    .setScale(0.15f, 0)
                    .setColor(color, color.darker())
                    .randomOffset(0.1f)
                    .addVelocity(0, 0.13f, 0)
                    .randomVelocity(0.03f, 0.08f)
                    .enableGravity()
                    .repeat(level, posX, posY, posZ, 24);

            RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0.1f, 0f)
                    .setLifetime(60)
                    .setScale(0.4f, 0)
                    .setColor(color, color.darker())
                    .randomOffset(0.25f, 0.1f)
                    .randomVelocity(0.004f, 0.004f)
                    .enableNoClip()
                    .repeat(level, posX, posY, posZ, 12);

            RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0.05f, 0f)
                    .setLifetime(30)
                    .setScale(0.2f, 0)
                    .setColor(color, color.darker())
                    .randomOffset(0.05f, 0.05f)
                    .randomVelocity(0.002f, 0.002f)
                    .enableNoClip()
                    .repeat(level, posX, posY, posZ, 8);
        }
    }
}
