package com.sammy.malum.network;

import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.systems.particle.ParticleManager;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
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
            types.add(SpiritHelper.getSpiritType(string));
        }
        float alpha = 0.1f / types.size();
        for (MalumSpiritType type : types)
        {
            Color color = type.color;
            ParticleManager.create(ParticleRegistry.TWINKLE_PARTICLE)
                    .setAlpha(alpha*2, 0f)
                    .setLifetime(60)
                    .setScale(0.4f, 0)
                    .setColor(color, color.darker())
                    .randomOffset(0.25f, 0.1f)
                    .randomVelocity(0.004f, 0.004f)
                    .enableNoClip()
                    .repeat(world, posX, posY, posZ, 12);

            ParticleManager.create(ParticleRegistry.WISP_PARTICLE)
                    .setAlpha(alpha, 0f)
                    .setLifetime(30)
                    .setScale(0.2f, 0)
                    .setColor(color, color.darker())
                    .randomOffset(0.05f, 0.05f)
                    .randomVelocity(0.002f, 0.002f)
                    .enableNoClip()
                    .repeat(world, posX, posY, posZ, 8);

            Vector3d velocity = new Vector3d(posX, posY, posZ).subtract(altarPosX, altarPosY, altarPosZ).normalize().scale(-0.05f);
            ParticleManager.create(ParticleRegistry.WISP_PARTICLE)
                    .setAlpha(alpha, 0f)
                    .setLifetime(40)
                    .setScale(0.3f, 0)
                    .randomOffset(0.15f)
                    .randomVelocity(0.005f, 0.005f)
                    .setColor(color, color.darker())
                    .addVelocity(velocity.x, velocity.y, velocity.z)
                    .enableNoClip()
                    .repeat(world, posX, posY, posZ, 36);
        }
    }
    public static void altarCraftParticles(ArrayList<String> spirits, double posX, double posY, double posZ)
    {
        World world = Minecraft.getInstance().world;
        ArrayList<MalumSpiritType> types = new ArrayList<>();
        for (String string : spirits)
        {
            types.add(SpiritHelper.getSpiritType(string));
        }
        for (MalumSpiritType type : types)
        {
            Color color = type.color;
            ParticleManager.create(ParticleRegistry.TWINKLE_PARTICLE)
                    .setAlpha(0.6f, 0f)
                    .setLifetime(80)
                    .setScale(0.15f, 0)
                    .setColor(color, color.darker())
                    .randomOffset(0.1f)
                    .addVelocity(0, 0.13f, 0)
                    .randomVelocity(0.03f, 0.08f)
                    .enableGravity()
                    .repeat(world, posX, posY, posZ, 24);

            ParticleManager.create(ParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0.1f, 0f)
                    .setLifetime(60)
                    .setScale(0.4f, 0)
                    .setColor(color, color.darker())
                    .randomOffset(0.25f, 0.1f)
                    .randomVelocity(0.004f, 0.004f)
                    .enableNoClip()
                    .repeat(world, posX, posY, posZ, 12);

            ParticleManager.create(ParticleRegistry.WISP_PARTICLE)
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
    public static void totemBlockParticles(String spirit, BlockPos pos, boolean success)
    {
        World world = Minecraft.getInstance().world;
        MalumSpiritType type = SpiritHelper.getSpiritType(spirit);
        Color color = type.color;
        if (!success)
        {
            color = color.darker();
        }

        ParticleManager.create(ParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.05f, 0f)
                .setLifetime(20)
                .setSpin(0.2f)
                .setScale(0.2f, 0)
                .setColor(color, color)
                .enableNoClip()
                .randomOffset(0.1f, 0.1f)
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(world, pos, 40);

        ParticleManager.create(ParticleRegistry.SMOKE_PARTICLE)
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
