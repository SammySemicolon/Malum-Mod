package com.sammy.malum.common.effect;

import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.ortus.helpers.ColorHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class AqueousAura extends MobEffect {
    public AqueousAura() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.AQUEOUS_SPIRIT_COLOR));
        addAttributeModifier(ForgeMod.REACH_DISTANCE.get(), "738bd9e4-23d8-46b0-b8ba-45a2016eec74", 1f, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }
}