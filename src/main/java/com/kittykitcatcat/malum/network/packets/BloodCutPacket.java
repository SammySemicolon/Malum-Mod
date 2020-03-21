package com.kittykitcatcat.malum.network.packets;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.particles.bloodparticle.BloodParticleData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class BloodCutPacket
{

    private double x, y, z;
    private int strength;
    public BloodCutPacket(double x, double y, double z, int strength)
    {
        this.strength = strength;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeInt(strength);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
            DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
                World world = Minecraft.getInstance().world;
                Vec3d pos = new Vec3d(x,y,z);
                if (strength == 4)
                {
                    for (int i = 0; i <= 20; i++)
                    {
                        Vec3d velocity = MalumHelper.randVelocity(world, -0.2f, 0.2f);
                        Vec3d particlePos = MalumHelper.randPos(pos, world, -0.4f, 0.4f);
                        world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, world.getBlockState(new BlockPos(x,y,z))), particlePos.getX(),particlePos.getY(),particlePos.getZ(), velocity.getX(),velocity.getY(),velocity.getZ());
                    }
                    for (int i = 0; i <= 20; i++)
                    {
                        Vec3d velocity = MalumHelper.randVelocity(world, -0.3f, 0.3f);
                        Vec3d particlePos = MalumHelper.randPos(pos, world, -0.5f, 0.5f);
                        world.addParticle(new BloodParticleData(), particlePos.getX(),particlePos.getY(),particlePos.getZ(), velocity.getX(),velocity.getY(),velocity.getZ());
                    }
                    world.playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_CORAL_BLOCK_FALL, SoundCategory.BLOCKS, 1, 1, true);
                    world.playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_CORAL_BLOCK_BREAK, SoundCategory.BLOCKS, 1, 1, true);
                    world.playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_CORAL_BLOCK_BREAK, SoundCategory.BLOCKS, 1.5f, 0.5f, true);
                    world.playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_CORAL_BLOCK_STEP, SoundCategory.BLOCKS, 1, 1, true);
                }
                else
                {
                    world.playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_CORAL_BLOCK_PLACE, SoundCategory.BLOCKS, 1, 1, true);
                }
            }));
        context.get().setPacketHandled(true);
    }

    public static BloodCutPacket decode(PacketBuffer buf)
    {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        int i = buf.readInt();
        return new BloodCutPacket(x, y, z, i);
    }
}