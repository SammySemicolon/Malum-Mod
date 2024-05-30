package com.sammy.malum.common.item.augment;

import com.sammy.malum.registry.common.SpiritTypeRegistry;

public class ImpurityStabilizer extends AbstractAugmentItem {
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
    public float getInstabilityIncrease() {
        return -0.02f;
    }
}
