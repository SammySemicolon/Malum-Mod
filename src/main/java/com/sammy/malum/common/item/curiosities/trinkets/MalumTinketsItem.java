package com.sammy.malum.common.item.curiosities.trinkets;

import dev.emi.trinkets.api.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.helpers.TrinketsHelper;

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

        addExtraTooltipLines(tooltipComponents::add);

        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    public static Component positiveEffect(String name, Object... args) {
        return Component.translatable("malum.gui.trinket.positive", Component.translatable("malum.gui.trinket.effect." + name, args)).withStyle(ChatFormatting.BLUE);
    }

    public static Component negativeEffect(String name, Object... args) {
        return Component.translatable("malum.gui.trinket.negative", Component.translatable("malum.gui.trinket.effect." + name, args)).withStyle(ChatFormatting.RED);
    }
}
