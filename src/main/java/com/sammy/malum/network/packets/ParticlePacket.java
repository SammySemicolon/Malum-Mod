package com.sammy.malum.network.packets;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.ParticleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Supplier;

import static net.minecraft.particles.ParticleTypes.*;

public class ParticlePacket
{
    int id;
    double posX;
    double posY;
    double posZ;
    public ParticlePacket(int id, double posX, double posY, double posZ)
    {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }
    
    public static ParticlePacket decode(PacketBuffer buf)
    {
        int id = buf.readInt();
        double posX = buf.readDouble();
        double posY = buf.readDouble();
        double posZ = buf.readDouble();
        return new ParticlePacket(id,posX,posY,posZ);
    }
    
    public void encode(PacketBuffer buf)
    {
        buf.writeInt(id);
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }
    
    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> {
            World world = Minecraft.getInstance().world;
            BlockPos pos = new BlockPos(posX,posY,posZ);
            switch (id)
            {
                case 0:
                {
                    Color color2 = MalumConstants.darkest();
                    ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.8f, 0f).setLifetime(12).setSpin(0.8f).setScale(0.3f, 0).setColor(color2.getRed() / 255f, color2.getGreen() / 255f, color2.getBlue() / 255f, color2.getRed() / 255f, (color2.getGreen() * 0.5f) / 255f, (color2.getBlue() * 0.5f) / 255f).randomOffset(0.1f).randomVelocity(0.075f, 0.075f).enableNoClip().repeat(world, posX,posY,posZ, 40);
                    break;
                }
                case 1:
                {
                    world.addParticle(EXPLOSION, posX + world.rand.nextFloat(), posY + world.rand.nextFloat(), posZ + world.rand.nextFloat(), 0, 0, 0);
                    world.addParticle(EXPLOSION, posX + world.rand.nextFloat(), posY + 1 + world.rand.nextFloat(), posZ + world.rand.nextFloat(), 0, 0, 0);
    
                    for (int i = 0; i < 4; i++)
                    {
                        ArrayList<Vector3d> particlePositions = MalumHelper.blockOutlinePositions(world, pos);
                        particlePositions.forEach(p -> world.addParticle(SMOKE, p.x,p.y,p.z, 0, world.rand.nextFloat() * 0.1f, 0));
                    }
                    Color color1 = MalumConstants.faded();
                    Color color2 = MalumConstants.darkest();
                    ParticleManager.create(MalumParticles.SPIRIT_FLAME).setScale(0.75f+world.rand.nextFloat(),0).setColor(color1.getRed() / 255f, color1.getGreen() / 255f, color1.getBlue() / 255f, color2.getRed() / 255f, (color2.getGreen() * 0.5f) / 255f, (color2.getBlue() * 0.5f) / 255f).addVelocity(0,0.1,0).randomOffset(0.1f).randomVelocity(0.075f, 0.075f).enableNoClip().repeatEdges(world, new BlockPos(posX,posY,posZ), 24);
                    break;
                }
                case 2:
                {
                    Color color1 = MalumConstants.faded();
                    Color color2 = MalumConstants.dark();
                    ParticleManager.create(MalumParticles.WISP_PARTICLE).setLifetime(18).setSpin(0.4f).randomVelocity(0.25f, 0f).setScale(0.1f+world.rand.nextFloat()*0.1f,0).setColor(color1.getRed() / 255f, color1.getGreen() / 255f, color1.getBlue() / 255f, color2.getRed() / 255f, (color2.getGreen() * 0.5f) / 255f, (color2.getBlue() * 0.5f) / 255f).enableNoClip().repeatCircle(world, posX,posY,posZ, 0.15f,24);
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}