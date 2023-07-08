package com.sammy.malum.common.effect;

import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class CorruptedAqueousAura extends MobEffect {
	public CorruptedAqueousAura() {
		super(MobEffectCategory.BENEFICIAL, SpiritTypeRegistry.AQUEOUS_SPIRIT.getPrimaryColor().getRGB());
	}

	@Override
	public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
	}
}