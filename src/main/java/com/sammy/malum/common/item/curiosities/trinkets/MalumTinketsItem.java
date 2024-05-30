package com.sammy.malum.common.item.curiosities.trinkets;

import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MalumTinketsItem extends AbstractMalumTrinketsItem {

    public MalumTinketsItem(Properties properties, MalumTrinketType type) {
        super(properties, type);
    }

    public void addExtraTooltipLines(Consumer<Component> consumer) {

    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        final List<Component> extraTooltipLines = new ArrayList<>();
        addExtraTooltipLines(extraTooltipLines::add);
        if (!extraTooltipLines.isEmpty()) {
            if (tooltipComponents.isEmpty()) {
                tooltipComponents.add(Component.empty());
            }
            if (level != null) {
                var trinket = TrinketsApi.getPlayerSlots(level);
                trinket.keySet().stream().findFirst().ifPresent(s -> {
                    tooltipComponents.add(Component.translatable("curios.modifiers." + s).withStyle(ChatFormatting.GOLD));
                });
            }

        }

        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    public static Component positiveEffect(String name, Object... args) {
        return Component.translatable("malum.gui.curio.positive", Component.translatable("malum.gui.curio.effect." + name, args)).withStyle(ChatFormatting.BLUE);
    }

    public static Component negativeEffect(String name, Object... args) {
        return Component.translatable("malum.gui.curio.negative", Component.translatable("malum.gui.curio.effect." + name, args)).withStyle(ChatFormatting.RED);
    }
}
