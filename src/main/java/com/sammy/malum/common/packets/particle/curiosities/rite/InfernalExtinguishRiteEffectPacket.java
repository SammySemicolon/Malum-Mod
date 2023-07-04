package com.sammy.malum.common.packets.particle.curiosities.rite;

import com.sammy.malum.common.packets.particle.curiosities.rite.generic.*;
import net.minecraft.client.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.network.*;
import net.minecraftforge.network.simple.*;

import java.awt.*;
import java.util.*;
import java.util.function.*;

public class InfernalExtinguishRiteEffectPacket extends BlockSparkleParticlePacket
{
    public InfernalExtinguishRiteEffectPacket(Color color, BlockPos pos) {
        super(color, pos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        Random rand = level.random;

        for(int i = 0; i < 8; ++i) {
            double d0 = pos.getX() + rand.nextFloat();
            double d1 = pos.getY() + rand.nextFloat();
            double d2 = pos.getZ() + rand.nextFloat();
            level.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
        super.execute(context);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, InfernalExtinguishRiteEffectPacket.class, InfernalExtinguishRiteEffectPacket::encode, InfernalExtinguishRiteEffectPacket::decode, InfernalExtinguishRiteEffectPacket::handle);
    }

    public static InfernalExtinguishRiteEffectPacket decode(FriendlyByteBuf buf) {
        return decode(InfernalExtinguishRiteEffectPacket::new, buf);
    }
}