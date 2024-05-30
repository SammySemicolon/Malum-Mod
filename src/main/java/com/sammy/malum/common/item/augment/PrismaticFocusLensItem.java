package com.sammy.malum.common.item.augment;

import com.sammy.malum.registry.common.SpiritTypeRegistry;

public class PrismaticFocusLensItem extends AbstractAugmentItem {
    public PrismaticFocusLensItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.AQUEOUS_SPIRIT);
    }

    @Override
    public float getInstabilityIncrease() {
        return -0.15f;
    }
}
