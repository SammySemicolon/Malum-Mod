package com.sammy.malum.common.item.augment;

import com.sammy.malum.registry.common.*;

public class PrismaticFocusLensItem extends AbstractAugmentItem{
    public PrismaticFocusLensItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.AQUEOUS_SPIRIT);
    }

    @Override
    public float getDamageChanceIncrease() {
        return -0.15f;
    }
}
