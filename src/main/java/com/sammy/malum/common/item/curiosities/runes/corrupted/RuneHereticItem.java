package com.sammy.malum.common.item.curiosities.runes.corrupted;

import com.sammy.malum.common.item.curiosities.runes.*;
import com.sammy.malum.registry.common.*;

import java.util.*;

public class RuneHereticItem extends MalumRuneCurioItem {

    public RuneHereticItem(Properties builder) {
        super(builder, SpiritTypeRegistry.ELDRITCH_SPIRIT);
    }

    @Override
    public Optional<String> getEffectDescriptor() {
        return Optional.of("heretic");
    }
}