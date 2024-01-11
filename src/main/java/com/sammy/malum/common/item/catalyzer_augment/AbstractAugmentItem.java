package com.sammy.malum.common.item.catalyzer_augment;

import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.world.item.*;

import java.util.*;

public class AbstractAugmentItem extends Item {
    public final MalumSpiritType spiritType;

    public AbstractAugmentItem(Properties pProperties, MalumSpiritType spiritType) {
        super(pProperties);
        this.spiritType = spiritType;
    }

    public float getSpeedIncrease() {
        return 0f;
    }

    public float getDamageChanceIncrease() {
        return 0f;
    }

    public float getBonusYieldChanceIncrease() {
        return 0f;
    }

    public float getFuelUsageRateIncrease() {
        return 0f;
    }

    public float getInstantCompletionChance() {
        return 0f;
    }

    public float getCompleteDamageNegationChance() {
        return 0f;
    }

    public float getRestorationChance() {
        return 0f;
    }

    public static Optional<AbstractAugmentItem> getAugmentType(ItemStack stack) {
        return stack.getItem() instanceof AbstractAugmentItem augmentItem ? Optional.of(augmentItem) : Optional.empty();
    }
}
