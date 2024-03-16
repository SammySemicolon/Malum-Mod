package com.sammy.malum.common.effect;

import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.helpers.*;

public class ReactiveShieldingEffect extends MobEffect {
    public ReactiveShieldingEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.EARTHEN_SPIRIT.getPrimaryColor()));
        addAttributeModifier(Attributes.ARMOR, "30e8d461-941b-4a0d-b105-60a73f28cf18", 0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ARMOR_TOUGHNESS, "a02a7c30-78a5-4d37-98bf-f30406de010b", 0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL);

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return false;
    }
}