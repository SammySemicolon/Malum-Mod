package com.sammy.malum.common.item.curiosities.curios;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

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

    /*TODO
    @Override
    public List<Component> getAttributesTooltip(List<net.minecraft.network.chat.Component> tooltips, ItemStack stack) {
        final List<Component> attributesTooltip = super.getAttributesTooltip(tooltips, stack);

        final List<Component> extraTooltipLines = new ArrayList<>();
        addExtraTooltipLines(extraTooltipLines::add);

        if (!extraTooltipLines.isEmpty()) {
            if (attributesTooltip.isEmpty()) {
                attributesTooltip.add(Component.empty());
                final Map<String, ISlotType> itemStackSlots = CuriosApi.getItemStackSlots(stack);

                itemStackSlots.keySet().stream().findFirst().ifPresent(s ->
                    attributesTooltip.add(Component.translatable("curios.modifiers." + s)
                        .withStyle(ChatFormatting.GOLD)));
            }

            attributesTooltip.addAll(extraTooltipLines);
        }
        return attributesTooltip;
    }

     */

    public static Component positiveEffect(String name, Object... args) {
        return Component.translatable("malum.gui.curio.positive", Component.translatable("malum.gui.curio.effect." + name, args)).withStyle(ChatFormatting.BLUE);
    }
    public static Component negativeEffect(String name, Object... args) {
        return Component.translatable("malum.gui.curio.negative", Component.translatable("malum.gui.curio.effect." + name, args)).withStyle(ChatFormatting.RED);
    }
}
