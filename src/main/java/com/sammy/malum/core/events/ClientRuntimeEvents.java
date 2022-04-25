package com.sammy.malum.core.events;

import com.sammy.malum.common.spiritaffinity.ArcaneAffinity;
import com.sammy.malum.common.spiritaffinity.EarthenAffinity;
import com.sammy.ortus.handlers.ScreenParticleHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientRuntimeEvents {

    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent.Post event) {
        EarthenAffinity.ClientOnly.renderHeartOfStone(event);
        ArcaneAffinity.ClientOnly.renderSoulWard(event);
    }
}