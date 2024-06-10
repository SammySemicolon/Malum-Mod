package com.sammy.malum.common.item.augment;

import com.sammy.malum.registry.common.*;

public class IntricateAssemblyItem extends AbstractAugmentItem{
    public IntricateAssemblyItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.EARTHEN_SPIRIT);
    }

    @Override
    public float getFortuneChance() {
        return 0.12f;
    }

    @Override
    public float getFuelUsageRateIncrease() {
        return 0.1f;
    }

    @Override
    public float getSpeedIncrease() {
        return -0.25f;
    }
}
