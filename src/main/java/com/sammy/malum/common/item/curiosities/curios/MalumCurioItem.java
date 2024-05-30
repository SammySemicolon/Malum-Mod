package com.sammy.malum.common.item.curiosities.curios;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public class MalumCurioItem extends AbstractMalumCurioItem implements Trinket {

    public MalumCurioItem(Properties properties, MalumTrinketType type) {
        super(properties, type);
    }

    public void addExtraTooltipLines(Consumer<Component> consumer) {

    }

    @Override
    public void addAttributeModifier(Multimap<Attribute, AttributeModifier> map, Attribute attribute, Function<UUID, AttributeModifier> attributeModifier) {
        super.addAttributeModifier(map, attribute, attributeModifier);
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
