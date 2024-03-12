package com.sammy.malum.common.item.curiosities.curios;

import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.*;
import top.theillusivec4.curios.api.type.capability.*;

import java.util.*;
import java.util.function.*;

public class MalumCurioItem extends AbstractMalumCurioItem implements ICurioItem {

    private final List<AttributeLikeTooltipEntry> extraTooltipLines = new ArrayList<>();

    public MalumCurioItem(Properties properties, MalumTrinketType type) {
        super(properties, type);
        addExtraTooltipLines(extraTooltipLines::add);
    }

    public void addExtraTooltipLines(Consumer<AttributeLikeTooltipEntry> consumer) {

    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
        final List<Component> attributesTooltip = super.getAttributesTooltip(tooltips, stack);

        if (!extraTooltipLines.isEmpty()) {
            if (attributesTooltip.isEmpty()) {
                attributesTooltip.add(Component.empty());
                final Map<String, ISlotType> itemStackSlots = CuriosApi.getItemStackSlots(stack);

                itemStackSlots.keySet().stream().findFirst().ifPresent(s -> {
                    attributesTooltip.add(Component.translatable("curios.modifiers." + s)
                            .withStyle(ChatFormatting.GOLD));
                });
            }
            for (AttributeLikeTooltipEntry attributeLike : extraTooltipLines) {
                attributesTooltip.add(Component.translatable(attributeLike.key).withStyle(attributeLike.formatting));
            }
        }
        return attributesTooltip;
    }

    public static AttributeLikeTooltipEntry positiveEffect(String key) {
        return new AttributeLikeTooltipEntry(key, ChatFormatting.BLUE);
    }
    public static AttributeLikeTooltipEntry negativeEffect(String key) {
        return new AttributeLikeTooltipEntry(key, ChatFormatting.RED);
    }
    public record AttributeLikeTooltipEntry(String key, ChatFormatting formatting) {
    }
}