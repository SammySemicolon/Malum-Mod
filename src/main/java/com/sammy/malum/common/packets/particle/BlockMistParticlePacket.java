package com.sammy.malum.common.packets.particle;

import com.sammy.malum.core.setup.client.ParticleRegistry;
import com.sammy.malum.core.helper.RenderHelper;
import com.sammy.malum.core.systems.rendering.particle.ParticleBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class BlockMistParticlePacket
{
    private final Color color;
    private final int posX;
    private final int posY;
    private final int posZ;
    public BlockMistParticlePacket(Color color, int posX, int posY, int posZ)
    {
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static BlockMistParticlePacket decode(FriendlyByteBuf buf)
    {
        Color color = new Color(buf.readInt(), buf.readInt(), buf.readInt());
        int posX = buf.readInt();
        int posY = buf.readInt();
        int posZ = buf.readInt();
        return new BlockMistParticlePacket(color, posX, posY, posZ);
    }

    public void encode(FriendlyByteBuf buf)
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
        instance.registerMessage(index, BlockMistParticlePacket.class, BlockMistParticlePacket::encode, BlockMistParticlePacket::decode, BlockMistParticlePacket::execute);
    }

    public static class ClientOnly {
        public static void addParticles(BlockPos pos, Color color) {
            Level level = Minecraft.getInstance().level;
            ParticleBuilders.create(ParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0.4f, 0f)
                    .setLifetime(20)
                    .setSpin(0.2f)
                    .setScale(0.3f, 0)
                    .setColor(color, color)
                    .enableNoClip()
                    .randomOffset(0.1f, 0.1f)
                    .randomMotion(0.001f, 0.001f)
                    .evenlyRepeatEdges(level, pos, 4, Direction.UP, Direction.DOWN);

            ParticleBuilders.create(ParticleRegistry.SMOKE_PARTICLE)
                    .setAlpha(0.2f, 0f)
                    .setLifetime(40)
                    .setSpin(0.1f)
                    .setScale(0.4f, 0)
                    .setColor(color, color)
                    .randomOffset(0.2f)
                    .enableNoClip()
                    .randomMotion(0.001f, 0.001f)
                    .evenlyRepeatEdges(level, pos, 6, Direction.UP, Direction.DOWN);
        }
    }
}