package com.kittykitcatcat.malum.network;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.network.packets.*;
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
    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(MalumMod.MODID, "main"),
        () -> NetworkManager.PROTOCOL_VERSION,
        NetworkManager.PROTOCOL_VERSION::equals,
        NetworkManager.PROTOCOL_VERSION::equals
    );

    @SuppressWarnings("UnusedAssignment")
    @SubscribeEvent
    public static void registerNetworkStuff(FMLCommonSetupEvent event)
    {
        int index = 0;
        INSTANCE.registerMessage(index++, BloodCutPacket.class, BloodCutPacket::encode, BloodCutPacket::decode, BloodCutPacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, TotalFlightTimePacket.class, TotalFlightTimePacket::encode, TotalFlightTimePacket::decode, TotalFlightTimePacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, FlightTimePacket.class, FlightTimePacket::encode, FlightTimePacket::decode, FlightTimePacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, CanFlyPacket.class, CanFlyPacket::encode, CanFlyPacket::decode, CanFlyPacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, FurnaceSoundStartPacket.class, FurnaceSoundStartPacket::encode, FurnaceSoundStartPacket::decode, FurnaceSoundStartPacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, FurnaceSoundStopPacket.class, FurnaceSoundStopPacket::encode, FurnaceSoundStopPacket::decode, FurnaceSoundStopPacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, BonkPacket.class, BonkPacket::encode, BonkPacket::decode, BonkPacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, SpiritWhisperPacket.class, SpiritWhisperPacket::encode, SpiritWhisperPacket::decode, SpiritWhisperPacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, SpiritInfusionSoundStartPacket.class, SpiritInfusionSoundStartPacket::encode, SpiritInfusionSoundStartPacket::decode, SpiritInfusionSoundStartPacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, SpiritInfusionStopLoopSoundPacket.class, SpiritInfusionStopLoopSoundPacket::encode, SpiritInfusionStopLoopSoundPacket::decode, SpiritInfusionStopLoopSoundPacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, SpiritInfusionFinishSoundPacket.class, SpiritInfusionFinishSoundPacket::encode, SpiritInfusionFinishSoundPacket::decode, SpiritInfusionFinishSoundPacket::whenThisPacketIsReceived);
    }
}