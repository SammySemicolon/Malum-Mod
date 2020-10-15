package com.sammy.malum.network;

import com.sammy.malum.MalumMod;
import com.sammy.malum.network.packets.FinishFunkEngineDisc;
import com.sammy.malum.network.packets.HuskChangePacket;
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
        INSTANCE.registerMessage(index++, HuskChangePacket.class, HuskChangePacket::encode, HuskChangePacket::decode, HuskChangePacket::whenThisPacketIsReceived);
        INSTANCE.registerMessage(index++, FinishFunkEngineDisc.class, FinishFunkEngineDisc::encode, FinishFunkEngineDisc::decode, FinishFunkEngineDisc::whenThisPacketIsReceived);
    }
}