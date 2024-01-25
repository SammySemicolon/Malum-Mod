package com.sammy.malum.common.item.augment.core;

import com.sammy.malum.registry.common.*;

public class StellarMechanismItem extends AbstractCoreAugmentItem {
    public StellarMechanismItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.ELDRITCH_SPIRIT);
    }

    @Override
    public float getTuningStrengthIncrease() {
        return 1f;
    }
}
