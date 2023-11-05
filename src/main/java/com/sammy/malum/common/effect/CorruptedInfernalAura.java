package com.sammy.malum.common.effect;

import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.helpers.ColorHelper;

public class CorruptedInfernalAura extends MobEffect {
    public CorruptedInfernalAura() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.INFERNAL_SPIRIT.getPrimaryColor()));
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth()) {
            entityLivingBaseIn.heal(amplifier + 1);
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 10 == 0;
    }
}