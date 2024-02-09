package com.sammy.malum.common.item.curiosities.runes.corrupted;

import com.sammy.malum.common.item.curiosities.runes.*;
import com.sammy.malum.registry.common.*;

import java.util.*;

public class RuneSacrificialEmpowermentItem extends MalumRuneCurioItem {

    public RuneSacrificialEmpowermentItem(Properties builder) {
        super(builder, SpiritTypeRegistry.WICKED_SPIRIT);
    }

    @Override
    public Optional<String> getEffectDescriptor() {
        return Optional.of("sacrificial_empowerment");
    }
}