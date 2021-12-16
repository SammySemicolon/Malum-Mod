package com.sammy.malum.core.eventhandlers;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.tools.ModCombatItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ItemEvents {

    @Mod.EventBusSubscriber(modid= MalumMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.FORGE)
    public static class ClientOnly {
        @SubscribeEvent
        public static void fixItemTooltip(ItemTooltipEvent event) {
            ItemStack stack = event.getItemStack();
            Item item = stack.getItem();
            if (item instanceof ModCombatItem) {
                List<Component> tooltip = event.getToolTip();
                ArrayList<Component> clone = new ArrayList<>(tooltip);

                for (int i = 0; i < clone.size(); i++) {
                    Component component = clone.get(i);
                    if (component instanceof TranslatableComponent) {
                        TranslatableComponent textComponent = (TranslatableComponent) component;
                        String rawText = textComponent.getString();
                        if (rawText.contains("+") || rawText.contains("-")) {
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
