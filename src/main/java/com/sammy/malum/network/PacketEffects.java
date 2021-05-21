package com.sammy.malum.network;

import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumSpiritTypes;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.recipes.SpiritIngredient;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.awt.*;
import java.util.ArrayList;

public class PacketEffects
{
    public static void altarConsumeParticles(ItemStack stack, ArrayList<String> spirits, double posX, double posY, double posZ, double altarPosX, double altarPosY, double altarPosZ)
    {
        World world = Minecraft.getInstance().world;
        ArrayList<MalumSpiritType> types = new ArrayList<>();
        for (String string : spirits)
        {
            types.add(SpiritHelper.figureOutType(string));
        }
        for (MalumSpiritType type : types)
        {
            Color color = type.color;
            ParticleManager.create(MalumParticles.WISP_PARTICLE)
                    .setAlpha(0.1f, 0f)
                    .setLifetime(60)
                    .setScale(0.4f, 0)
                    .setColor(color, color.darker())
                    .randomOffset(0.25f, 0.1f)
                    .randomVelocity(0.004f, 0.004f)
                    .enableNoClip()
                    .repeat(world, posX, posY, posZ, 12);

            ParticleManager.create(MalumParticles.WISP_PARTICLE)
                    .setAlpha(0.05f, 0f)
                    .setLifetime(30)
                    .setScale(0.2f, 0)
                    .setColor(color, color.darker())
                    .randomOffset(0.05f, 0.05f)
                    .randomVelocity(0.002f, 0.002f)
                    .enableNoClip()
                    .repeat(world, posX, posY, posZ, 8);
        }
    }
    public static void altarCraftParticles(ArrayList<String> spirits, double posX, double posY, double posZ)
    {
        World world = Minecraft.getInstance().world;
        ArrayList<MalumSpiritType> types = new ArrayList<>();
        for (String string : spirits)
        {
            types.add(SpiritHelper.figureOutType(string));
        }
        for (MalumSpiritType type : types)
        {
            Color color = type.color;
            ParticleManager.create(MalumParticles.SPARKLE_PARTICLE)
                    .setAlpha(0.6f, 0f)
                    .setLifetime(80)
                    .setScale(0.15f, 0)
                    .setColor(color, color.darker())
                    .randomOffset(0.1f)
                    .addVelocity(0, 0.13f, 0)
                    .randomVelocity(0.03f, 0.08f)
                    .enableGravity()
                    .repeat(world, posX, posY, posZ, 20);

            ParticleManager.create(MalumParticles.WISP_PARTICLE)
                    .setAlpha(0.1f, 0f)
                    .setLifetime(60)
                    .setScale(0.4f, 0)
                    .setColor(color, color.darker())
                    .randomOffset(0.25f, 0.1f)
                    .randomVelocity(0.004f, 0.004f)
                    .enableNoClip()
                    .repeat(world, posX, posY, posZ, 12);

            ParticleManager.create(MalumParticles.WISP_PARTICLE)
                    .setAlpha(0.05f, 0f)
                    .setLifetime(30)
                    .setScale(0.2f, 0)
                    .setColor(color, color.darker())
                    .randomOffset(0.05f, 0.05f)
                    .randomVelocity(0.002f, 0.002f)
                    .enableNoClip()
                    .repeat(world, posX, posY, posZ, 8);
        }
    }
    public static void tyrving(double posX, double posY, double posZ)
    {
        World world = Minecraft.getInstance().world;
        Color color1 = new Color(158, 7, 219);
        Color color2 = new Color(56, 20, 95);
        ParticleManager.create(MalumParticles.WISP_PARTICLE)
                .setAlpha(0.1f, 0f)
                .setLifetime(10)
                .setSpin(0.4f)
                .setScale(0.4f, 0)
                .setColor(color1, color1)
                .enableNoClip()
                .randomOffset(0.2f, 0.2f)
                .randomVelocity(0.01f, 0.01f)
                .repeat(world, posX, posY, posZ, 12);

        ParticleManager.create(MalumParticles.SMOKE_PARTICLE)
                .setAlpha(0.04f, 0f)
                .setLifetime(40)
                .setSpin(0.1f)
                .setScale(0.6f, 0)
                .setColor(color1, color2)
                .randomOffset(0.4f)
                .enableNoClip()
                .randomVelocity(0.025f, 0.025f)
                .repeat(world, posX, posY, posZ, 20);

    }
}
