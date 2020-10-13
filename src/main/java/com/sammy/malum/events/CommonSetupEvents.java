package com.sammy.malum.events;

import com.sammy.malum.ClientHandler;
import com.sammy.malum.capabilities.IMalumData;
import com.sammy.malum.capabilities.MalumData;
import com.sammy.malum.capabilities.MalumDataStorage;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.sammy.malum.MalumMod.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetupEvents
{
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent e) {
        CapabilityManager.INSTANCE.register(IMalumData.class, new MalumDataStorage(), MalumData::new);
    }
}