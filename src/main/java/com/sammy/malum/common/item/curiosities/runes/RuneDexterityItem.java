package com.sammy.malum.common.item.curiosities.runes;

import com.sammy.malum.registry.common.*;

import java.util.*;

public class RuneDexterityItem extends MalumRuneCurioItem {

    public RuneDexterityItem(Properties builder) {
        super(builder, SpiritTypeRegistry.AERIAL_SPIRIT);
    }

    @Override
    public Optional<String> getEffectDescriptor() {
        return Optional.of("dexterity");
    }
}