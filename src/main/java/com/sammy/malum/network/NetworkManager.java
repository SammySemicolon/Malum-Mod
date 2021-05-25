package com.sammy.malum.network;

import com.sammy.malum.MalumMod;
import com.sammy.malum.network.packets.*;
import com.sammy.malum.network.packets.altar.SpiritAltarConsumeParticlePacket;
import com.sammy.malum.network.packets.altar.SpiritAltarCraftParticlePacket;
import com.sammy.malum.network.packets.rites.CropGrowthPacket;
import com.sammy.malum.network.packets.totem.SpiritEngravePacket;
import com.sammy.malum.network.packets.totem.TotemParticlePacket;
import com.sammy.malum.network.packets.totem.TotemPoleParticlePacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MalumMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetworkManager
{
    public static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(MalumMod.MODID, "main"), () -> NetworkManager.PROTOCOL_VERSION, NetworkManager.PROTOCOL_VERSION::equals, NetworkManager.PROTOCOL_VERSION::equals);
    
    @SuppressWarnings("UnusedAssignment")
    @SubscribeEvent
    public static void registerNetworkStuff(FMLCommonSetupEvent event)
    {
        int index = 0;
        INSTANCE.registerMessage(index++, TyrvingParticlePacket.class, TyrvingParticlePacket::encode, TyrvingParticlePacket::decode, TyrvingParticlePacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, SpiritAltarCraftParticlePacket.class, SpiritAltarCraftParticlePacket::encode, SpiritAltarCraftParticlePacket::decode, SpiritAltarCraftParticlePacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, SpiritAltarConsumeParticlePacket.class, SpiritAltarConsumeParticlePacket::encode, SpiritAltarConsumeParticlePacket::decode, SpiritAltarConsumeParticlePacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, TotemParticlePacket.class, TotemParticlePacket::encode, TotemParticlePacket::decode, TotemParticlePacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, TotemPoleParticlePacket.class, TotemPoleParticlePacket::encode, TotemPoleParticlePacket::decode, TotemPoleParticlePacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, SpiritEngravePacket.class, SpiritEngravePacket::encode, SpiritEngravePacket::decode, SpiritEngravePacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, CropGrowthPacket.class, CropGrowthPacket::encode, CropGrowthPacket::decode, CropGrowthPacket::whenThisPacketIsReceived);

    }
}