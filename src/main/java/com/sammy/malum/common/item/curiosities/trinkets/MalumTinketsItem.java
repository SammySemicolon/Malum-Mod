package com.sammy.malum.common.item.curiosities.trinkets;

import dev.emi.trinkets.api.SlotGroup;
import dev.emi.trinkets.api.SlotType;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public class MalumTinketsItem extends AbstractMalumTrinketsItem {

    public MalumTinketsItem(Properties properties, MalumTrinketType type) {
        super(properties, type);
    }

    public void addExtraTooltipLines(Consumer<Component> consumer) {

    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {

        tooltipComponents.add(Component.empty());

        if (level != null) {
            Map<String, SlotGroup> trinket = TrinketsApi.getPlayerSlots(level);
            var t = trinket.entrySet().stream().findFirst();

            if (t.isPresent()) {
                Optional<Map.Entry<String, SlotType>> s = t.get().getValue().getSlots().entrySet().stream().findFirst();
                s.ifPresent(stringSlotTypeEntry -> tooltipComponents.add(Component.translatable("curios.modifiers." + stringSlotTypeEntry.getValue().getName()).withStyle(ChatFormatting.GOLD)));
            }
        }

        addExtraTooltipLines(tooltipComponents::add);

        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    public static Component positiveEffect(String name, Object... args) {
        return Component.translatable("malum.gui.curio.positive", Component.translatable("malum.gui.curio.effect." + name, args)).withStyle(ChatFormatting.BLUE);
    }

    public static Component negativeEffect(String name, Object... args) {
        return Component.translatable("malum.gui.curio.negative", Component.translatable("malum.gui.curio.effect." + name, args)).withStyle(ChatFormatting.RED);
    }
}
