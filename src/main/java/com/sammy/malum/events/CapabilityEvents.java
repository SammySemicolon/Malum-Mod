package com.sammy.malum.events;

import com.sammy.malum.MalumMod;
import com.sammy.malum.capabilities.IMalumData;
import com.sammy.malum.capabilities.MalumData;
import com.sammy.malum.capabilities.MalumDataProvider;
import com.sammy.malum.capabilities.MalumDataStorage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber
public class CapabilityEvents
{
    @SubscribeEvent
    public static void onAttatchCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(MalumMod.MODID, "malum_data"), new MalumDataProvider());
        }
    }
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;
        LazyOptional<IMalumData> capability = event.getOriginal().getCapability(MalumDataProvider.CAPABILITY);
        capability.ifPresent(oldStore ->
                event.getOriginal().getCapability(MalumDataProvider.CAPABILITY).ifPresent(newStore ->
                        newStore.copy(oldStore)));
    }
}