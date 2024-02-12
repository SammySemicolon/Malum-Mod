package com.sammy.malum.common.item.curiosities.runes.corrupted;

import com.sammy.malum.common.item.curiosities.runes.*;
import com.sammy.malum.registry.common.*;

import java.util.*;

public class RuneIgneousSolaceItem extends MalumRuneCurioItem {

    public RuneIgneousSolaceItem(Properties builder) {
        super(builder, SpiritTypeRegistry.INFERNAL_SPIRIT);
    }

    @Override
    public Optional<String> getEffectDescriptor() {
        return Optional.of("igneous_solace");
    }
}