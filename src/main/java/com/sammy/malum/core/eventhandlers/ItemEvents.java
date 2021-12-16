package com.sammy.malum.core.eventhandlers;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.tools.ModCombatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
                List<ITextComponent> tooltip = event.getToolTip();
                ArrayList<ITextComponent> clone = new ArrayList<>(tooltip);

                for (int i = 0; i < clone.size(); i++) {
                    ITextComponent component = clone.get(i);
                    if (component instanceof TranslationTextComponent) {
                        TranslationTextComponent textComponent = (TranslationTextComponent) component;
                        String rawText = textComponent.getString();
                        if (rawText.contains("+") || rawText.contains("-")) {
                            String amount = textComponent.decomposedParts.get(1).getString();
                            String text = textComponent.decomposedParts.get(3).getString();
                            component = new StringTextComponent(" " + amount + " " + text).withStyle(TextFormatting.DARK_GREEN);
                            tooltip.set(i, component);
                        }
                    }
                }
            }
        }
    }
}
