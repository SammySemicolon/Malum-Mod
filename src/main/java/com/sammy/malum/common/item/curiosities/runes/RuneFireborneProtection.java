package com.sammy.malum.common.item.curiosities.runes;

import com.sammy.malum.registry.common.*;

import java.util.*;

public class RuneFireborneProtection extends MalumRuneCurioItem {

    public RuneFireborneProtection(Properties builder) {
        super(builder, SpiritTypeRegistry.INFERNAL_SPIRIT);
    }

    @Override
    public Optional<String> getEffectDescriptor() {
        return Optional.of("fireborne_protection");
    }
}