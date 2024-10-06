package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.packets.*;
import com.sammy.malum.common.packets.malignant_conversion.*;
import com.sammy.malum.common.packets.particle.curiosities.blight.BlightMistParticlePacket;
import com.sammy.malum.common.packets.particle.curiosities.blight.BlightTransformItemParticlePacket;
import com.sammy.malum.common.packets.particle.curiosities.rite.*;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.BlockSparkleParticlePacket;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.MajorEntityEffectParticlePacket;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MalumMod.MALUM, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PacketRegistry {
    public static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel MALUM_CHANNEL = NetworkRegistry.newSimpleChannel(MalumMod.malumPath("main"), () -> PacketRegistry.PROTOCOL_VERSION, PacketRegistry.PROTOCOL_VERSION::equals, PacketRegistry.PROTOCOL_VERSION::equals);

    @SuppressWarnings("UnusedAssignment")
    @SubscribeEvent
    public static void registerNetworkStuff(FMLCommonSetupEvent event) {
        int index = 0;

        //functionality
        VoidRejectionPacket.register(MALUM_CHANNEL, index++);
        SyncStaffCooldownChangesPacket.register(MALUM_CHANNEL, index++);
        SyncMalignantConversionPacket.register(MALUM_CHANNEL, index++);

        ParticleEffectPacket.register(MALUM_CHANNEL, index++);

        MajorEntityEffectParticlePacket.register(MALUM_CHANNEL, index++);

        SacredMistRiteEffectPacket.register(MALUM_CHANNEL, index++);
        BlockSparkleParticlePacket.register(MALUM_CHANNEL, index++);
        InfernalAccelerationRiteEffectPacket.register(MALUM_CHANNEL, index++);
        InfernalExtinguishRiteEffectPacket.register(MALUM_CHANNEL, index++);
        AerialBlockFallRiteEffectPacket.register(MALUM_CHANNEL, index++);
        SpiritRiteActivationEffectPacket.register(MALUM_CHANNEL, index++);

        SyncMalumPlayerCapabilityDataPacket.register(MALUM_CHANNEL, index++);
        SyncLivingCapabilityDataPacket.register(MALUM_CHANNEL, index++);

        BlightMistParticlePacket.register(MALUM_CHANNEL, index++);
        BlightTransformItemParticlePacket.register(MALUM_CHANNEL, index++);

    }
}