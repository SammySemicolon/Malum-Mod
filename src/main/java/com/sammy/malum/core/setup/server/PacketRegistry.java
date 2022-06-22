package com.sammy.malum.core.setup.server;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.packets.SyncLivingCapabilityDataPacket;
import com.sammy.malum.common.packets.SyncMalumPlayerCapabilityDataPacket;
import com.sammy.malum.common.packets.particle.block.functional.AltarConsumeParticlePacket;
import com.sammy.malum.common.packets.particle.block.functional.AltarCraftParticlePacket;
import com.sammy.malum.common.packets.particle.block.*;
import com.sammy.malum.common.packets.particle.entity.MajorEntityEffectParticlePacket;
import com.sammy.malum.common.packets.particle.entity.MinorEntityEffectParticlePacket;
import com.sammy.malum.common.packets.particle.entity.SuccessfulSoulHarvestParticlePacket;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MalumMod.MALUM, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PacketRegistry {
    public static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel MALUM_CHANNEL = NetworkRegistry.newSimpleChannel(MalumMod.prefix("main"), () -> PacketRegistry.PROTOCOL_VERSION, PacketRegistry.PROTOCOL_VERSION::equals, PacketRegistry.PROTOCOL_VERSION::equals);

    @SuppressWarnings("UnusedAssignment")
    @SubscribeEvent
    public static void registerNetworkStuff(FMLCommonSetupEvent event) {
        int index = 0;
        AltarCraftParticlePacket.register(MALUM_CHANNEL, index++);
        AltarConsumeParticlePacket.register(MALUM_CHANNEL, index++);

        MinorEntityEffectParticlePacket.register(MALUM_CHANNEL, index++);
        MajorEntityEffectParticlePacket.register(MALUM_CHANNEL, index++);
        SuccessfulSoulHarvestParticlePacket.register(MALUM_CHANNEL, index++);

        BlockParticlePacket.register(MALUM_CHANNEL, index++);
        BlockMistParticlePacket.register(MALUM_CHANNEL, index++);
        BlockSparkleParticlePacket.register(MALUM_CHANNEL, index++);
        FireBlockExtinguishSparkleParticlePacket.register(MALUM_CHANNEL, index++);
        BlockDownwardSparkleParticlePacket.register(MALUM_CHANNEL, index++);
        TotemBaseActivationParticlePacket.register(MALUM_CHANNEL, index++);
        SyncMalumPlayerCapabilityDataPacket.register(MALUM_CHANNEL, index++);
        SyncLivingCapabilityDataPacket.register(MALUM_CHANNEL, index++);
    }
}