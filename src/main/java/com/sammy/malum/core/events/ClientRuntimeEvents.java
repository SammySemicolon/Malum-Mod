package com.sammy.malum.core.events;

import com.sammy.malum.common.item.tools.magic.*;
import com.sammy.malum.common.spiritaffinity.ArcaneAffinity;
import com.sammy.malum.common.spiritaffinity.EarthenAffinity;
import com.sammy.malum.compability.farmersdelight.FarmersDelightCompat;
import com.sammy.malum.core.handlers.RenderHandler;
import com.sammy.malum.core.handlers.ScreenParticleHandler;
import com.sammy.malum.core.systems.item.ModCombatItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientRuntimeEvents {

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END)) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.level != null) {
                if (minecraft.isPaused()) {
                    return;
                }
                ScreenParticleHandler.clientTick(event);
            }
        }
    }

    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent.Post event) {
        EarthenAffinity.ClientOnly.renderHeartOfStone(event);
        ArcaneAffinity.ClientOnly.renderSoulWard(event);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void renderLast(RenderLevelLastEvent event) {
        RenderHandler.renderLast(event);
    }

    @SubscribeEvent
    public static void renderTick(TickEvent.RenderTickEvent event) {
        ScreenParticleHandler.renderParticles(event);
    }
}