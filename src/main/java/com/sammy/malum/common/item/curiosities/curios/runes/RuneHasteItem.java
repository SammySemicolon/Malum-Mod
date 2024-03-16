package com.sammy.malum.common.item.curiosities.curios.runes;

import com.sammy.malum.registry.common.*;

import java.util.function.*;

public class RuneHasteItem extends MalumRuneCurioItem {

    public RuneHasteItem(Properties builder) {
        super(builder, SpiritTypeRegistry.INFERNAL_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<AttributeLikeTooltipEntry> consumer) {
        consumer.accept(positiveEffect("malum.gui.rune.effect.haste"));
    }
}