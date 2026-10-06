package com.sammy.malum.client.events;

import com.sammy.malum.client.renderer.texture.loaded.*;
import com.sammy.malum.client.scarf.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.core.handlers.KeywordTooltipHandler;
import com.sammy.malum.core.handlers.client.*;
import com.sammy.malum.core.systems.item.HeldItemTracker;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.entity.player.*;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientRuntimeEventHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void renderStages(RenderLevelStageEvent event) {
        if (event.getStage().equals(RenderLevelStageEvent.Stage.AFTER_LEVEL)) {
            ScarfRenderHandler.renderScarfData(event);
        }
    }

    @SubscribeEvent
    public static void registerListeners(RegisterClientReloadListenersEvent event) {
        TextureLoaderReloadListener.register(event);
    }

    @SubscribeEvent
    public static void clientTickEvent(ClientTickEvent.Pre event) {
        HeldItemTracker.tickTrackers();
        ScarfRenderHandler.tickScarfData(event);
        HiddenBladeRenderHandler.tick(event);
        MalignantAegisRenderHandler.tick(event);
        SoulWardRenderHandler.tick(event);
        StaffAbilityRenderHandler.tick(event);
        WaveformConfigurationHandler.tick(event);
    }

    @SubscribeEvent
    public static void renderItemTooltip(ItemTooltipEvent event) {
        GeasItem.addGeasTooltip(event);
        AugmentItem.addAugmentAttributeTooltip(event);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void renderItemTooltipLate(ItemTooltipEvent event) {
        KeywordTooltipHandler.addKeywords(event);
    }

    @SubscribeEvent
    public static void onBlockActivated(PlayerInteractEvent.RightClickBlock event) {
        WaveformConfigurationHandler.onBlockActivated(event);
    }
}
