package com.sammy.malum.core.setup.server;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.packets.SyncLivingCapabilityDataPacket;
import com.sammy.malum.common.packets.SyncMalumPlayerCapabilityDataPacket;
import com.sammy.malum.common.packets.particle.*;
import com.sammy.malum.common.packets.particle.altar.AltarConsumeParticlePacket;
import com.sammy.malum.common.packets.particle.altar.AltarCraftParticlePacket;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MalumMod.MALUM, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PacketRegistry {
    public static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(MalumMod.prefix("main"), () -> PacketRegistry.PROTOCOL_VERSION, PacketRegistry.PROTOCOL_VERSION::equals, PacketRegistry.PROTOCOL_VERSION::equals);

    @SuppressWarnings("UnusedAssignment")
    @SubscribeEvent
    public static void registerNetworkStuff(FMLCommonSetupEvent event) {
        int index = 0;
        AltarCraftParticlePacket.register(INSTANCE, index++);
        AltarConsumeParticlePacket.register(INSTANCE, index++);
        MagicParticlePacket.register(INSTANCE, index++);
        SoulPurgeParticlePacket.register(INSTANCE, index++);
        BlockParticlePacket.register(INSTANCE, index++);
        BlockMistParticlePacket.register(INSTANCE, index++);
        BlockSparkleParticlePacket.register(INSTANCE, index++);
        TotemParticlePacket.register(INSTANCE, index++);
        SyncMalumPlayerCapabilityDataPacket.register(INSTANCE, index++);
        SyncLivingCapabilityDataPacket.register(INSTANCE, index++);
    }
}