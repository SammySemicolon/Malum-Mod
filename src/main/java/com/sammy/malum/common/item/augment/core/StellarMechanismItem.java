package com.sammy.malum.common.item.augment.core;

import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.item.*;

public class StellarMechanismItem extends CoreAugmentItem {
    public StellarMechanismItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.ELDRITCH_SPIRIT);
    }

    @Override
    public float getTuningStrengthIncrease() {
        return 1f;
    }
}
