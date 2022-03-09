package com.sammy.malum.core.events;

import com.sammy.malum.common.item.tools.magic.*;
import com.sammy.malum.common.spiritaffinity.ArcaneAffinity;
import com.sammy.malum.common.spiritaffinity.EarthenAffinity;
import com.sammy.malum.compability.farmersdelight.FarmersDelightCompat;
import com.sammy.malum.core.handlers.ScreenParticleHandler;
import com.sammy.malum.core.systems.item.ModCombatItem;
import com.sammy.malum.core.systems.rendering.particle.screen.base.ScreenParticle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
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

    @SubscribeEvent
    public static void renderTick(TickEvent.RenderTickEvent event) {
        ScreenParticleHandler.renderParticles(event);
    }

    @SubscribeEvent
    public static void fixItemTooltip(ItemTooltipEvent event) { //TODO: make this not absolutely awful, probably with mixins
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        if (item instanceof ModCombatItem || item instanceof MagicAxeItem || item instanceof MagicSwordItem || item instanceof MagicPickaxeItem || item instanceof MagicShovelItem || item instanceof MagicHoeItem || (FarmersDelightCompat.LOADED && FarmersDelightCompat.LoadedOnly.isMagicKnife(item))) {
            List<Component> tooltip = event.getToolTip();
            ArrayList<Component> clone = new ArrayList<>(tooltip);
            for (int i = 0; i < clone.size(); i++) {
                Component component = clone.get(i);
                if (component instanceof TranslatableComponent textComponent) {
                    String rawText = textComponent.getString();
                    if (rawText.contains("+") || rawText.contains("-")) {
                        if (textComponent.decomposedParts.size() > 3) {
                            String amount = textComponent.decomposedParts.get(1).getString();
                            String text = textComponent.decomposedParts.get(3).getString();
                            component = new TextComponent(" " + amount + " " + text).withStyle(ChatFormatting.DARK_GREEN);
                            tooltip.set(i, component);
                        }
                    }
                }
            }
        }
    }
}