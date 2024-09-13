package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.packets.*;
import com.sammy.malum.common.packets.particle.curiosities.blight.BlightMistParticlePacket;
import com.sammy.malum.common.packets.particle.curiosities.blight.BlightTransformItemParticlePacket;
import com.sammy.malum.common.packets.particle.curiosities.rite.*;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.BlockSparkleParticlePacket;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.MajorEntityEffectParticlePacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import team.lodestar.lodestone.registry.common.LodestoneNetworkPayloads;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = MalumMod.MALUM, bus = EventBusSubscriber.Bus.MOD)
public class PacketRegistry {

    public static LodestoneNetworkPayloads.PayloadNetworkChannel MALUM_CHANNEL =  new LodestoneNetworkPayloads.PayloadNetworkChannel(MalumMod.MALUM);

    @SuppressWarnings("UnusedAssignment")
    @SubscribeEvent
    public static void registerNetworkStuff(FMLCommonSetupEvent event) {
        int index = 0;

        //functionality
        VoidRejectionPacket.register(MALUM_CHANNEL, index++);
        SyncStaffCooldownChangesPacket.register(MALUM_CHANNEL, index++);

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