package com.sammy.malum.common.item.augment;

import com.sammy.malum.registry.common.SpiritTypeRegistry;

public class ShieldingApparatusItem extends AbstractAugmentItem {
    public ShieldingApparatusItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.ARCANE_SPIRIT);
    }

    @Override
    public float getShieldingChance() {
        return 0.08f;
    }

    @Override
    public float getInstabilityIncrease() {
        return -0.02f;
    }

    @Override
    public float getSpeedIncrease() {
        return -0.2f;
    }
}
