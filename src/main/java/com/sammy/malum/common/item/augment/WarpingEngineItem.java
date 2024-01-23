package com.sammy.malum.common.item.augment;

import com.sammy.malum.registry.common.*;

public class WarpingEngineItem extends AbstractAugmentItem{
    public WarpingEngineItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.ELDRITCH_SPIRIT);
    }

    @Override
    public float getInstantCompletionChance() {
        return 0.06f;
    }

    @Override
    public float getDamageChanceIncrease() {
        return 0.02f;
    }

    @Override
    public float getFuelUsageRateIncrease() {
        return 0.1f;
    }
}
