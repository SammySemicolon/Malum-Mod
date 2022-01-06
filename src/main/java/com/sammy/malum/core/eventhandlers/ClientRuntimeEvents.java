package com.sammy.malum.core.eventhandlers;

import com.sammy.malum.common.item.spirit.SoulStaveItem;
import com.sammy.malum.common.spiritaffinity.ArcaneAffinity;
import com.sammy.malum.common.spiritaffinity.EarthenAffinity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientRuntimeEvents {
    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent.Post event) {
        ArcaneAffinity.ClientOnly.renderSoulWard(event);
        EarthenAffinity.ClientOnly.renderHeartOfStone(event);
    }
    @SubscribeEvent
    public static void renderLast(RenderLevelLastEvent event) {
        SoulStaveItem.ClientOnly.renderHarvestVFX(event);
    }
}
