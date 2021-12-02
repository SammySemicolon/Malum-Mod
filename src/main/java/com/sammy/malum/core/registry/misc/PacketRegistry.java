package com.sammy.malum.core.registry.misc;

import com.sammy.malum.MalumMod;
import com.sammy.malum.network.packets.particle.SmallBurstParticlePacket;
import com.sammy.malum.network.packets.particle.altar.SpiritAltarConsumeParticlePacket;
import com.sammy.malum.network.packets.particle.altar.SpiritAltarCraftParticlePacket;
import com.sammy.malum.network.packets.particle.BurstParticlePacket;
import com.sammy.malum.network.packets.particle.UpwardsFromBlockParticlesPacket;
import com.sammy.malum.network.packets.particle.totem.SpiritEngraveParticlePacket;
import com.sammy.malum.network.packets.particle.totem.TotemParticlePacket;
import com.sammy.malum.network.packets.particle.totem.TotemPoleParticlePacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MalumMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PacketRegistry
{
    public static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(MalumMod.MODID, "main"), () -> PacketRegistry.PROTOCOL_VERSION, PacketRegistry.PROTOCOL_VERSION::equals, PacketRegistry.PROTOCOL_VERSION::equals);
    
    @SuppressWarnings("UnusedAssignment")
    @SubscribeEvent
    public static void registerNetworkStuff(FMLCommonSetupEvent event)
    {
        int index = 0;
        INSTANCE.registerMessage(index++, SpiritAltarCraftParticlePacket.class, SpiritAltarCraftParticlePacket::encode, SpiritAltarCraftParticlePacket::decode, SpiritAltarCraftParticlePacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, SpiritAltarConsumeParticlePacket.class, SpiritAltarConsumeParticlePacket::encode, SpiritAltarConsumeParticlePacket::decode, SpiritAltarConsumeParticlePacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, TotemParticlePacket.class, TotemParticlePacket::encode, TotemParticlePacket::decode, TotemParticlePacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, TotemPoleParticlePacket.class, TotemPoleParticlePacket::encode, TotemPoleParticlePacket::decode, TotemPoleParticlePacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, SpiritEngraveParticlePacket.class, SpiritEngraveParticlePacket::encode, SpiritEngraveParticlePacket::decode, SpiritEngraveParticlePacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, UpwardsFromBlockParticlesPacket.class, UpwardsFromBlockParticlesPacket::encode, UpwardsFromBlockParticlesPacket::decode, UpwardsFromBlockParticlesPacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, BurstParticlePacket.class, BurstParticlePacket::encode, BurstParticlePacket::decode, BurstParticlePacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, SmallBurstParticlePacket.class, SmallBurstParticlePacket::encode, SmallBurstParticlePacket::decode, SmallBurstParticlePacket::whenThisPacketIsReceived);

    }
}