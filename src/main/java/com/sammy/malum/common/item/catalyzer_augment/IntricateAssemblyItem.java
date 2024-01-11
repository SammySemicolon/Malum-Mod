package com.sammy.malum.common.item.catalyzer_augment;

import com.sammy.malum.registry.common.*;

public class IntricateAssemblyItem extends AbstractAugmentItem{
    public IntricateAssemblyItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.EARTHEN_SPIRIT);
    }

    @Override
    public float getBonusYieldChanceIncrease() {
        return 0.08f;
    }

    @Override
    public float getFuelUsageRateIncrease() {
        return 0.16f;
    }

    @Override
    public float getSpeedIncrease() {
        return -0.5f;
    }
}
