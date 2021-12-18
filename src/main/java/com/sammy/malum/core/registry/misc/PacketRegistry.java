package com.sammy.malum.core.registry.misc;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.packets.particle.*;
import com.sammy.malum.common.packets.particle.altar.SpiritAltarConsumeParticlePacket;
import com.sammy.malum.common.packets.particle.altar.SpiritAltarCraftParticlePacket;
import com.sammy.malum.core.helper.DataHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MalumMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PacketRegistry
{
    public static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(DataHelper.prefix("main"), () -> PacketRegistry.PROTOCOL_VERSION, PacketRegistry.PROTOCOL_VERSION::equals, PacketRegistry.PROTOCOL_VERSION::equals);
    
    @SuppressWarnings("UnusedAssignment")
    @SubscribeEvent
    public static void registerNetworkStuff(FMLCommonSetupEvent event)
    {
        int index = 0;
        INSTANCE.registerMessage(index++, SpiritAltarCraftParticlePacket.class, SpiritAltarCraftParticlePacket::encode, SpiritAltarCraftParticlePacket::decode, SpiritAltarCraftParticlePacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, SpiritAltarConsumeParticlePacket.class, SpiritAltarConsumeParticlePacket::encode, SpiritAltarConsumeParticlePacket::decode, SpiritAltarConsumeParticlePacket::whenThisPacketIsReceived);

        MagicParticlePacket.register(INSTANCE, index++);
        BlockParticlePacket.register(INSTANCE, index++);
        BlockMistParticlePacket.register(INSTANCE, index++);
        BlockSparkleParticlePacket.register(INSTANCE, index++);
        TotemParticlePacket.register(INSTANCE, index++);

    }
}