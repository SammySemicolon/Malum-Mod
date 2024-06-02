package com.sammy.malum.common.item.augment.core;

import com.sammy.malum.common.item.augment.AbstractAugmentItem;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;

public abstract class AbstractCoreAugmentItem extends AbstractAugmentItem {
    public AbstractCoreAugmentItem(Properties pProperties, MalumSpiritType spiritType) {
        super(pProperties, spiritType);
    }

    @Override
    public String getAugmentTypeTranslator() {
        return "core_augment";
    }
}
