package com.sammy.malum.common.item.catalyzer_augment;

import com.sammy.malum.registry.common.*;

public class MendingDiffuserItem extends AbstractAugmentItem{
    public MendingDiffuserItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.SACRED_SPIRIT);
    }

    @Override
    public boolean isForCrucible() {
        return true;
    }
}
