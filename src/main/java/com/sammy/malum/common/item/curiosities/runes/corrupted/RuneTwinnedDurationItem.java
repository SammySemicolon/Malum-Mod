package com.sammy.malum.common.item.curiosities.runes.corrupted;

import com.sammy.malum.common.item.curiosities.runes.*;
import com.sammy.malum.registry.common.*;

import java.util.*;

public class RuneTwinnedDurationItem extends MalumRuneCurioItem {

    public RuneTwinnedDurationItem(Properties builder) {
        super(builder, SpiritTypeRegistry.AQUEOUS_SPIRIT);
    }

    @Override
    public Optional<String> getEffectDescriptor() {
        return Optional.of("twinned_duration");
    }
}