package com.sammy.malum.common.item.curiosities.runes;

import com.sammy.malum.registry.common.*;

import java.util.*;

public class RuneAlimentCleansingItem extends MalumRuneCurioItem {

    public RuneAlimentCleansingItem(Properties builder) {
        super(builder, SpiritTypeRegistry.AQUEOUS_SPIRIT);
    }

    @Override
    public Optional<String> getEffectDescriptor() {
        return Optional.of("aliment_cleansing");
    }
}