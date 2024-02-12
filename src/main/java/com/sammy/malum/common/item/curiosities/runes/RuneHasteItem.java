package com.sammy.malum.common.item.curiosities.runes;

import com.sammy.malum.registry.common.*;

import java.util.*;

public class RuneHasteItem extends MalumRuneCurioItem {

    public RuneHasteItem(Properties builder) {
        super(builder, SpiritTypeRegistry.INFERNAL_SPIRIT);
    }

    @Override
    public Optional<String> getEffectDescriptor() {
        return Optional.of("haste");
    }
}