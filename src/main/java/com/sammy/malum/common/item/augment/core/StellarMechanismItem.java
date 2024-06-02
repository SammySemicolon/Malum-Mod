package com.sammy.malum.common.item.augment.core;

import com.sammy.malum.registry.common.SpiritTypeRegistry;

public class StellarMechanismItem extends AbstractCoreAugmentItem {
    public StellarMechanismItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.ELDRITCH_SPIRIT);
    }

    @Override
    public float getTuningStrengthIncrease() {
        return 1f;
    }
}
