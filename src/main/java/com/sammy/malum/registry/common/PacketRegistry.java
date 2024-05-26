package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.packets.*;
import com.sammy.malum.common.packets.particle.curiosities.blight.BlightMistParticlePacket;
import com.sammy.malum.common.packets.particle.curiosities.blight.BlightTransformItemParticlePacket;
import com.sammy.malum.common.packets.particle.curiosities.rite.*;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.BlockSparkleParticlePacket;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.MajorEntityEffectParticlePacket;
import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.api.EnvType;

@SuppressWarnings("unused")
public class PacketRegistry {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel MALUM_CHANNEL = new SimpleChannel(MalumMod.malumPath("main"));


    @SuppressWarnings("UnusedAssignment")
    public static void registerNetworkStuff() {
        MALUM_CHANNEL.initServerListener();
        EnvExecutor.runWhenOn(EnvType.CLIENT, () -> MALUM_CHANNEL::initClientListener);
        int index = 0;

        //functionality
        MALUM_CHANNEL.registerS2CPacket(VoidRejectionPacket.class, index++);
        MALUM_CHANNEL.registerS2CPacket(SyncStaffCooldownChangesPacket.class, index++);
        MALUM_CHANNEL.registerS2CPacket(ParticleEffectPacket.class, index++);
        MALUM_CHANNEL.registerS2CPacket(MajorEntityEffectParticlePacket.class, index++);
        MALUM_CHANNEL.registerS2CPacket(SacredMistRiteEffectPacket.class, index++);
        MALUM_CHANNEL.registerS2CPacket(BlockSparkleParticlePacket.class, index++);
        MALUM_CHANNEL.registerS2CPacket(InfernalAccelerationRiteEffectPacket.class, index++);
        MALUM_CHANNEL.registerS2CPacket(InfernalExtinguishRiteEffectPacket.class, index++);
        MALUM_CHANNEL.registerS2CPacket(AerialBlockFallRiteEffectPacket.class, index++);
        MALUM_CHANNEL.registerS2CPacket(SpiritRiteActivationEffectPacket.class, index++);

        //SyncMalumPlayerCapabilityDataPacket.register(MALUM_CHANNEL, index++); //TODO components sync themselves?
        //SyncLivingCapabilityDataPacket.register(MALUM_CHANNEL, index++);

        MALUM_CHANNEL.registerS2CPacket(BlightMistParticlePacket.class, index++);
        MALUM_CHANNEL.registerS2CPacket(BlightTransformItemParticlePacket.class, index++);
    }
}