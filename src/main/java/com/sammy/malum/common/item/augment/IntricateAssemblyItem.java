package com.sammy.malum.common.item.augment;

import com.sammy.malum.registry.common.SpiritTypeRegistry;

public class IntricateAssemblyItem extends AbstractAugmentItem {
    public IntricateAssemblyItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.EARTHEN_SPIRIT);
    }

    @Override
    public float getFortuneChance() {
        return 0.10f;
    }

    @Override
    public float getFuelUsageRateIncrease() {
        return 0.15f;
    }

    @Override
    public float getSpeedIncrease() {
        return -0.5f;
    }
}
