package com.sammy.malum.common.packets.particle.rite;

import com.sammy.malum.common.packets.particle.rite.generic.BlockSparkleParticlePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.awt.*;

public class InfernalExtinguishRiteEffectPacket extends BlockSparkleParticlePacket {

    public InfernalExtinguishRiteEffectPacket(Color col, BlockPos pos) {
        super(col, pos, false);
    }

    public InfernalExtinguishRiteEffectPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handle(IPayloadContext iPayloadContext) {
        Level level = Minecraft.getInstance().level;
        var rand = level.random;

        for (int i = 0; i < 8; ++i) {
            double d0 = pos.getX() + rand.nextFloat();
            double d1 = pos.getY() + rand.nextFloat();
            double d2 = pos.getZ() + rand.nextFloat();
            level.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
        super.handle(iPayloadContext);
    }
}