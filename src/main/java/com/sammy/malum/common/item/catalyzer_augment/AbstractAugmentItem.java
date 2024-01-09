package com.sammy.malum.common.item.catalyzer_augment;

import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.world.item.*;

public class AbstractAugmentItem extends Item {
    public final MalumSpiritType spiritType;

    public AbstractAugmentItem(Properties pProperties, MalumSpiritType spiritType) {
        super(pProperties);
        this.spiritType = spiritType;
    }

    public boolean isForCrucible() {
        return false;
    }
}
