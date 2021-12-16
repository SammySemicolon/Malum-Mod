package com.sammy.malum.common.packets.particle;

import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.systems.particle.ParticleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.Level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class BlockSparkleParticlePacket
{
    private final Color color;
    private final int posX;
    private final int posY;
    private final int posZ;
    public BlockSparkleParticlePacket(Color color, int posX, int posY, int posZ)
    {
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static BlockSparkleParticlePacket decode(PacketBuffer buf)
    {
        Color color = new Color(buf.readInt(), buf.readInt(), buf.readInt());
        int posX = buf.readInt();
        int posY = buf.readInt();
        int posZ = buf.readInt();
        return new BlockSparkleParticlePacket(color, posX, posY, posZ);
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeInt(color.getRed());
        buf.writeInt(color.getGreen());
        buf.writeInt(color.getBlue());
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);
    }

    public void execute(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ClientOnly.addParticles(new BlockPos(posX, posY, posZ), color);
            }
        });
        context.get().setPacketHandled(true);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, BlockSparkleParticlePacket.class, BlockSparkleParticlePacket::encode, BlockSparkleParticlePacket::decode, BlockSparkleParticlePacket::execute);
    }

    public static class ClientOnly {
        public static void addParticles(BlockPos pos, Color color) {
            Level Level = Minecraft.getInstance().level;
            ParticleManager.create(ParticleRegistry.TWINKLE_PARTICLE)
                    .setAlpha(0.4f, 0f)
                    .setLifetime(20)
                    .setSpin(0.3f)
                    .setScale(0.2f, 0)
                    .setColor(color, color)
                    .enableNoClip()
                    .randomOffset(0.1f, 0.1f)
                    .randomVelocity(0.001f, 0.001f)
                    .evenlyRepeatEdges(Level, pos, 4, Direction.UP, Direction.DOWN);
            ParticleManager.create(ParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0.1f, 0f)
                    .setLifetime(40)
                    .setSpin(0.1f)
                    .setScale(0.6f, 0)
                    .setColor(color, color)
                    .randomOffset(0.2f)
                    .enableNoClip()
                    .randomVelocity(0.001f, 0.001f)
                    .evenlyRepeatEdges(Level, pos, 6, Direction.UP, Direction.DOWN);
        }
    }
}