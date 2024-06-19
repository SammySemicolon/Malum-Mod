package com.sammy.malum.common.item.augment;

import com.sammy.malum.registry.common.*;

public class WarpingEngineItem extends AbstractAugmentItem{
    public WarpingEngineItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.ELDRITCH_SPIRIT);
    }

    @Override
    public float getChainFocusingChance() {
        return 0.1f;
    }

    @Override
    public float getInstabilityIncrease() {
        return 0.02f;
    }

    @Override
    public float getFuelUsageRateIncrease() {
        return 0.12f;
    }
}
