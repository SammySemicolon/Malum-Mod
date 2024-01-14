package com.sammy.malum.common.item.catalyzer_augment;

import com.sammy.malum.registry.common.*;

public class ImpurityStabilizer extends AbstractAugmentItem{
    public ImpurityStabilizer(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.WICKED_SPIRIT);
    }

    @Override
    public float getWeakestAttributeMultiplier() {
        return 0.25f;
    }

    @Override
    public float getFuelUsageRateIncrease() {
        return -0.04f;
    }

    @Override
    public float getDamageChanceIncrease() {
        return -0.02f;
    }
}
