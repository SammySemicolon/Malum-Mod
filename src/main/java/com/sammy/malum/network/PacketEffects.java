package com.sammy.malum.network;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
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
    public static void spiritEngrave(String spirit, BlockPos pos)
    {
        World world = Minecraft.getInstance().world;
        MalumSpiritType type = SpiritHelper.figureOutType(spirit);
        Color color = MalumHelper.brighter(type.color, 3);
        ParticleManager.create(MalumParticles.SMOKE_PARTICLE)
                .setAlpha(0.1f, 0f)
                .setLifetime(15)
                .setSpin(0.2f)
                .setScale(0.5f, 0)
                .setColor(color, color)
                .enableNoClip()
                .randomOffset(0.3f, 0.3f)
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(world, pos, 4);

        totemBlockParticles(spirit, pos, true);
    }
    public static void totemBlockParticles(String spirit, BlockPos pos, boolean success)
    {
        World world = Minecraft.getInstance().world;
        MalumSpiritType type = SpiritHelper.figureOutType(spirit);
        Color color = type.color;
        if (!success)
        {
            color = color.darker();
        }

        ParticleManager.create(MalumParticles.WISP_PARTICLE)
                .setAlpha(0.05f, 0f)
                .setLifetime(20)
                .setSpin(0.2f)
                .setScale(0.2f, 0)
                .setColor(color, color)
                .enableNoClip()
                .randomOffset(0.1f, 0.1f)
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(world, pos, 40);

        ParticleManager.create(MalumParticles.SMOKE_PARTICLE)
                .setAlpha(0.025f, 0f)
                .setLifetime(40)
                .setSpin(0.1f)
                .setScale(0.4f, 0)
                .setColor(color, color)
                .randomOffset(0.2f)
                .enableNoClip()
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(world, pos, 60);
    }
    public static void totemParticles(ArrayList<String> spirits, BlockPos pos, boolean success)
    {
        for (int i = 0; i < spirits.size(); i++)
        {
            totemBlockParticles(spirits.get(i), pos.up(1+i), success);
        }
    }
}
