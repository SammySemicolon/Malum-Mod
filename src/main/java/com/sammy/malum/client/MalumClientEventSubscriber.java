package com.sammy.malum.client;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.spirit.SpiritJarItem;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = MalumMod.MALUM, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class MalumClientEventSubscriber {

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(SpiritJarItem.CUSTOM_RENDERER, ItemRegistry.SPIRIT_JAR.get());
    }

}
