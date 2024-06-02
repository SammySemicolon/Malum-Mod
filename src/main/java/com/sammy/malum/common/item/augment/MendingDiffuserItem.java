package com.sammy.malum.common.item.augment;

import com.sammy.malum.registry.common.SpiritTypeRegistry;

public class MendingDiffuserItem extends AbstractAugmentItem {
    public MendingDiffuserItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.SACRED_SPIRIT);
    }

    @Override
    public float getRestorationChance() {
        return 0.08f;
    }
}
